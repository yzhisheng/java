package com.sakura.test;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import oracle.sql.BLOB;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @Author Sakura
 * @Date 2024/6/5 10:02
 * @Version 1.0
 */
public class NoncarCoreTest {

    public Map<String, InputStream> getBlobAsInputStream(String query, String preFilePath) {

        Map<String, InputStream> resultMap = new HashMap();

        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getDruidConnection();
            String sql = query;

            MapListHandler handler = new MapListHandler();
            List<Map<String, Object>> list = runner.query(conn, sql, handler);

            if (CollectionUtil.isNotEmpty(list)) {
                for (Map<String, Object> map : list) {

                    //下载图片
                    if (map.containsKey("C_IMAGE_NAME") && null != map.get("C_IMAGE_NAME")) {
                        String cImageName = map.get("C_IMAGE_NAME").toString();
                        if (null != cImageName && !cImageName.isEmpty()) {
                            BLOB bImage = (BLOB) map.get("B_IMAGE");
                            String filePath = preFilePath + map.get("C_IMAGE_NAME").toString();
                            FileUtil.writeFromStream(bImage.getBinaryStream(), filePath);
                        }
                    }

                    BLOB bTemplate = (BLOB) map.get("B_TEMPLATE");
                    resultMap.put((String) map.get("C_TEMPLATE_NAME"), bTemplate.getBinaryStream());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
        return resultMap;
    }

    @Test
    public void exportBlobData() {

        // 假设查询语句和需要读取的列名以及记录ID
        String query = "select * from web_prn_fmp where  C_PRN_FMP like '%043037%'   and C_PRN_TYPE ='P'";
        query = "select * from web_prn_fmp where  C_PRN_TYPE like '%G_pdf%' and C_PRN_FMP ='Guarantee_65'";

        String filePath = "D:\\ApplicationCache\\Nwt\\王国谢\\Ireport3.0\\compile\\";
        FileUtil.del(filePath);
        // 获取 Blob 输入流
        Map<String, InputStream> map = getBlobAsInputStream(query, filePath);

        if (CollectionUtil.isNotEmpty(map)) {
            // 定义保存路径和文件名
            for (String str : map.keySet()) {
                FileUtil.writeFromStream(map.get(str), filePath + str);
            }
            // 将 Blob 数据保存为文件
            System.out.println("Blob 数据已成功保存为文件：" + filePath);
        } else {
            System.out.println("无法从数据库中获取 Blob 数据.");
        }

    }

    /**
     * 导出生产产品信息
     */
    @Test
    public void exportProud() {

        //查询列表
        String queryColumn = "*";
        String tableName = "";
        String condition = "";

        //定义执行的险别代码
        String str = "160023";
        if (StrUtil.isBlank(str)) {
            System.out.println("险别代码未定义!");
        }

        String collect = Arrays.stream(str.split(",")).map(m -> "'" + m.trim() + "'").collect(Collectors.joining(","));


        Map<String, String> queryMap = new HashMap<>();
        //1、产品定义
        queryMap.put("prdfac.prdfac_PRD_PROD", "C_PROD_NO in (%s)");
        //2、险别定义
        queryMap.put("prdfac.prdfac_PRD_CVRG", "C_CVRG_NO in (select C_CVRG_NO from prdfac.prdfac_prd_prod_cvrg_rel where C_PROD_NO in (%s))");
        //3、产品关联险别
        queryMap.put("prdfac.prdfac_prd_prod_cvrg_rel", "C_PROD_NO in (%s)");
        // 4、条款定义
        queryMap.put("prdfac.prdfac_prd_clause", "C_CLAUSE_NO in (select C_CLAUSE_CLAUSE_NO from prdfac.prdfac_prd_clause_cvrg_rel where C_CLAUSE_CVRG_NO in (select C_CVRG_NO from prdfac.prdfac_PRD_CVRG where C_CVRG_NO in (select C_CVRG_NO from prdfac.prdfac_prd_prod_cvrg_rel where C_PROD_NO in (%s))))");
        //5、险别关联条款
        queryMap.put("prdfac.prdfac_prd_clause_cvrg_rel", "C_CLAUSE_CVRG_NO in (select C_CVRG_NO from prdfac.prdfac_PRD_CVRG where C_CVRG_NO in (select C_CVRG_NO from prdfac.prdfac_prd_prod_cvrg_rel where C_PROD_NO in (%s)))");
        //6、责任定义
        queryMap.put("prdfac.prdfac_prd_risk", "C_RISK_NO in (select C_RISK_NO from prdfac.prdfac_prd_cvrg_risk_rel where C_CVRG_NO in (select C_CVRG_NO from prdfac.prdfac_prd_prod_cvrg_rel where C_PROD_NO in (%s)))");
//        //7、责任关联险别
        queryMap.put("prdfac.prdfac_prd_cvrg_risk_rel", "C_CVRG_NO in (select C_CVRG_NO from prdfac.prdfac_prd_prod_cvrg_rel where C_PROD_NO in (%s))");

        String sql = StrUtil.EMPTY;


        // 创建PreparedStatement
        Connection connection = null;
        ResultSet resultSet = null;
        Statement stmt = null;
        try {

            int j = 1;
            for (String tableStr : queryMap.keySet()) {
                if (StrUtil.isNotBlank(tableStr)) {
                    tableName = tableStr;
                    sql = String.format("SELECT %s FROM %s ", queryColumn, tableName);
                    if (StrUtil.isNotBlank(queryMap.get(tableStr))) {
                        condition = String.format(queryMap.get(tableStr), collect);
                        sql = sql + " WHERE " + condition;
                    }


                    // 2. 获取表的列信息
                    connection = JDBCUtils.getDruidConnection();
                    DatabaseMetaData dbMetaData = connection.getMetaData();

                    // 3. 查询表以获取数据
                    stmt = connection.createStatement();
//                    System.out.println("--准备执行的查询sql   \n" + sql);

                    resultSet = stmt.executeQuery(sql);
                    ResultSetMetaData rsMetaData = resultSet.getMetaData();
                    int columnCount = rsMetaData.getColumnCount();

                    //获取列名
                    StringBuilder columnNames = new StringBuilder();
                    for (int i = 1; i <= columnCount; i++) {
                        if (i > 1) columnNames.append(", ");
                        columnNames.append(rsMetaData.getColumnName(i));
                    }


                    if (!resultSet.next()) {
                        System.out.println("--" + j + "、" + tableName + "未查询到数据!");
                    } else {
                        StringBuilder insertSql = new StringBuilder();
                        insertSql.append("INSERT INTO ").append(tableName);
                        if (StrUtil.isNotBlank(columnNames)) {
                            insertSql.append(" (").append(columnNames).append(")");
                        }

                        insertSql.append(" VALUES (");

                        for (int i = 1; i <= columnCount; i++) {
                            insertSql.append(i > 1 ? ", " : "");

                            // 根据列类型处理数据
                            int columnType = rsMetaData.getColumnType(i);
                            switch (columnType) {
                                case Types.DATE:
                                case Types.TIMESTAMP:
                                    Timestamp timestamp = resultSet.getTimestamp(i);
                                    if (timestamp != null) {
                                        insertSql.append("TO_DATE('").append(timestamp.toString().replaceAll(".0", "")).append("', 'YYYY-MM-DD HH24:MI:SS')");
                                    } else {
                                        insertSql.append("null");
                                    }
                                    break;
                                case Types.VARCHAR:
                                case Types.CHAR:
                                case Types.LONGVARCHAR:
                                    String value = resultSet.getString(i);
                                    insertSql.append(value != null ? "'" + value.replaceAll("'", "''") + "'" : "null");
                                    break;
                                default:
                                    insertSql.append(resultSet.getString(i));
                            }


                        }

                        insertSql.append(");");


                        // 输出或使用生成的INSERT语句
                        System.out.println("--" + j + "、" + tableName + "");
                        System.out.println(insertSql.toString());
                    }


                    connection.close();
                    j++;
                }
            }


        } catch (Exception e) {
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println(String.format("读取【%s】表失败,查询sql【%s】", tableName, sql));
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, stmt, resultSet);

        }
    }

    @Test
    public void test1() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6000; i++) {
            stringBuilder.append("草");
        }
        System.out.println(stringBuilder.toString());

        System.out.println(stringBuilder.length());
    }

}
