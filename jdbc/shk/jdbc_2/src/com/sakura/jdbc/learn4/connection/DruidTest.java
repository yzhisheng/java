package com.sakura.jdbc.learn4.connection;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @Description
 * @ClassName DruidTest
 * @Author Sakura
 * @DateTime 2024-06-09 17:31:28
 * @Version 1.0
 */
public class DruidTest {

    @Test
    public void getConnection() throws Exception{
        Properties pros = new Properties();

        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");

        pros.load(is);

        DataSource source = DruidDataSourceFactory.createDataSource(pros);
        Connection conn = source.getConnection();
        System.out.println(conn);

    }
}
