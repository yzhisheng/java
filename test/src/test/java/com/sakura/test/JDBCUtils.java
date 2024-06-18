package com.sakura.test;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.dialect.Props;
import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 操作数据库的工具类
 * @ClassName JDBCUtils
 * @Author Sakura
 * @DateTime 2024-06-09 00:56:19
 * @Version 1.0
 */
public class JDBCUtils {

    private static DruidDataSource dataSource = new DruidDataSource();


    private static final String dev = "prd.";

    static {
        try {
            // 读取 properties 文件
            Props properties = new Props("db.properties", CharsetUtil.UTF_8);


            String url = "jdbc:oracle:thin:@%s:%s/%s";
            String format = String.format(url, properties.getProperty(dev + "host"), properties.getProperty(dev + "port"), properties.getProperty(dev + "sid"));
            // 设置数据库连接信息
            dataSource.setUrl(format);
            dataSource.setUsername(properties.getProperty(dev + "userName"));
            dataSource.setPassword(properties.getProperty(dev + "password"));

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
    public static Connection getDruidConnection() throws Exception {
        return dataSource.getConnection();
    }


    /**
     *
     * @Description 关闭连接和Statement的操作
     * @author shkstart
     * @date 上午9:12:40
     * @param conn
     * @param ps
     */
    public static void closeResource(Connection conn, Statement ps){
        try {
            if(ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * @Description 关闭资源操作
     * @author shkstart
     * @date 上午10:21:15
     * @param conn
     * @param ps
     * @param rs
     */
    public static void closeResource(Connection conn, Statement ps, ResultSet rs){
        try {
            if(ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
