package com.sakura.test;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @Author Sakura
 * @Date 2024/6/5 10:02
 * @Version 1.0
 */
public class NoncarCoreTest {

    private static DruidDataSource dataSource = new DruidDataSource();

    static {
        try {
            // 读取 properties 文件
            Props properties = new Props("db.properties", CharsetUtil.UTF_8);


            String url = "jdbc:oracle:thin:@%s:%s/%s";
            String format = String.format(url, properties.getProperty("prd.host"), properties.getProperty("prd.port"), properties.getProperty("prd.sid"));
            // 设置数据库连接信息
            dataSource.setUrl(format);
            dataSource.setUsername(properties.getProperty("prd.userName"));
            dataSource.setPassword(properties.getProperty("prd.password"));

            // 配置Druid连接池
            dataSource.setInitialSize(Integer.parseInt(properties.getProperty("druid.initialSize")));
            dataSource.setMaxActive(Integer.parseInt(properties.getProperty("druid.maxActive")));
            dataSource.setMinIdle(Integer.parseInt(properties.getProperty("druid.minIdle")));
            dataSource.setMaxWait(Integer.parseInt(properties.getProperty("druid.maxWait")));
            dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(properties.getProperty("druid.timeBetweenEvictionRunsMillis")));
            dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(properties.getProperty("druid.minEvictableIdleTimeMillis")));
            dataSource.setValidationQuery(properties.getProperty("druid.validationQuery"));

        } catch (Exception e) {
            System.out.println("初始化数据库连接异常!,异常原因:" + e.getMessage());
            e.printStackTrace();
        }
    }


    //获取连接
    public Connection getConnection() throws Exception {
        return dataSource.getConnection();
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
        String str = "060361";
        if (StrUtil.isBlank(str)) {
            System.out.println("险别代码未定义!");
        }

        String collect = Arrays.stream(str.split(",")).map(m -> "'" + m.trim() + "'").collect(Collectors.joining(","));


        Map<String, String> queryMap = new HashMap<>();
        //1、产品定义
        queryMap.put("prdfac.prdfac_PRD_PROD", "C_PROD_NO in (select distinct C_PROD_NO\n" +
                "                                                from prdfac.prdfac_prd_prod_cvrg_rel where C_CVRG_NO in (%s))");
        //2、险别定义
        queryMap.put("prdfac.prdfac_PRD_CVRG", "C_CVRG_NO in (%s)");
        //3、产品关联险别
        queryMap.put("prdfac.prdfac_prd_prod_cvrg_rel", "C_CVRG_NO in (%s)");
        // 4、条款定义
        queryMap.put("prdfac.prdfac_prd_clause", "C_CLAUSE_NO in (select C_CLAUSE_CLAUSE_NO\n" +
                "                                                    from prdfac.prdfac_prd_clause_cvrg_rel where C_CLAUSE_CVRG_NO in (%s))");
        //5、险别关联条款
        queryMap.put("prdfac.prdfac_prd_clause_cvrg_rel", "C_CLAUSE_CVRG_NO in (%s)");
        //6、责任定义
        queryMap.put("prdfac.prdfac_prd_risk", "C_RISK_NO in (select C_RISK_NO\n" +
                "                                                from prdfac.prdfac_prd_cvrg_risk_rel where C_CVRG_NO in (%s))");
//        //7、责任关联险别
        queryMap.put("prdfac.prdfac_prd_cvrg_risk_rel", "C_CVRG_NO in (%s)");


        String sql = StrUtil.EMPTY;


        // 创建PreparedStatement
        Connection connection = null;
        try {

            int j= 1;
            for (String tableStr : queryMap.keySet()) {
                if (StrUtil.isNotBlank(tableStr)) {
                    tableName = tableStr;
                    sql = String.format("SELECT %s FROM %s ", queryColumn, tableName);
                    if (StrUtil.isNotBlank(queryMap.get(tableStr))) {
                        condition = String.format(queryMap.get(tableStr), collect);
                        sql = sql + " WHERE " + condition;
                    }


                    // 2. 获取表的列信息
                    connection = getConnection();
                    DatabaseMetaData dbMetaData = connection.getMetaData();

                    // 3. 查询表以获取数据
                    Statement stmt = connection.createStatement();
                    ResultSet resultSet = stmt.executeQuery(sql);
                    ResultSetMetaData rsMetaData = resultSet.getMetaData();
                    int columnCount = rsMetaData.getColumnCount();

                    //获取列名
                    StringBuilder columnNames = new StringBuilder();
                    for (int i = 1; i <= columnCount; i++) {
                        if (i > 1) columnNames.append(", ");
                        columnNames.append(rsMetaData.getColumnName(i));
                    }


                    if(!resultSet.next()){
                        System.out.println("--"+j+"、"+tableName+"未查询到数据!");
                    }else{
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
                        System.out.println("--"+j+"、" + tableName + "");
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
            if (null != connection) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != dataSource) {
                try {
                    dataSource.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

}
