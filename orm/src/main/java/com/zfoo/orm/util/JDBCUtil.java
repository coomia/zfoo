package com.zfoo.orm.util;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 11 03 14 37
 */
public class JDBCUtil {
    static Properties pros = null; // 可以帮助读取和处理资源文件中的信息
    static { // 加载JDBCUtil类的时候调用
        pros = new Properties();
        try {
            //类的相对路径，以''空开头，表示bin目录
            pros.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/example/jdbc/database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getMySqlConnection() {
        try {
            Class.forName(pros.getProperty("mysqlDriver"));
            return DriverManager.getConnection(pros.getProperty("mysqlURL"), pros.getProperty("mysqlUser"),
                    pros.getProperty("mysqlPwd"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection getOracleConn() {
        try {
            Class.forName(pros.getProperty("oracleDriver"));
            return DriverManager.getConnection(pros.getProperty("oracleURL"), pros.getProperty("oracleUser"),
                    pros.getProperty("oraclePwd"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void close(ResultSet rs, Statement ps, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement ps, Connection conn) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

