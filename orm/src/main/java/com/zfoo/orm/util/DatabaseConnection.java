package com.zfoo.orm.util;

import java.io.Reader;
import java.sql.*;
import java.util.Random;

/**
 * 1.加载JDBC驱动程序 2.建立与数据库的连接 3.发送sql查询 4.得到返回结果
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 11 03 14 40
 */
public class DatabaseConnection {

    // 装载MySql驱动：Class.forName("com.mysql.jdbc.Driver");
    // 装载Oracle驱动：Class.forName("oracle.jdbc.OracleDriver");
    public void connection() {
        Connection conn = null;
        // Statement stmt = null;
        PreparedStatement ps = null;
        try {
            // 加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            // 建立连接(连接对象内部其实包含了Socket对象，是一个远程的连接。比较耗时！这是Connection对象管理的一个要点！)
            // 真正开发中，为了提高效率，都会使用连接池来管理连接对象！
            // DriverManager作用于用户和驱动程序之间，是一个中介
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbctest", "root", "123");

            // --------------------------------------------------------------------------------------
            // stmt = conn.createStatement();
            // String name = "赵六";
            // String sql = "insert into user (name,age) values('" + name +
            // "',now())";
            // stmt.execute(sql);

            // --------------------------------------------------------------------------------------
            // 测试SQL注入
            // String id = "5 or 1=1 ";//全部删除
            // String sql = "delete from t_user where id=" + id;
            // stmt.execute(sql);

            // --------------------------------------------------------------------------------------
            String sql = "insert into user (name,age) values (?,?)"; // ?占位符
            ps = conn.prepareStatement(sql);
            // ps.setString(1, "高淇3"); //参数索引是从1开始计算， 而不是0
            // ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));

            // 可以使用setObject方法处理参数
            ps.setObject(1, "高淇5");
            ps.setObject(2, new java.sql.Date(System.currentTimeMillis()));

            System.out.println("插入一行记录");
            // ps.execute();
            int count = ps.executeUpdate();
            System.out.println(count);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

    }

    // 测试ResultSet结果集的基本用法
    public void resultSet() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 加载驱动类
            conn = JDBCUtil.getMySqlConnection();

            String sql = "select id,name from user where id>?"; // ?占位符
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 2); // 把id大于2的记录都取出来
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "---" + rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            // 遵循：resultset-->statment-->connection这样的关闭顺序！一定要将三个trycatch块，分开写！
            JDBCUtil.close(rs, ps, conn);
        }
    }

    // 测试批处理的基本用法
    public void batchInsert() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // 加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbctest", "root", "123");

            conn.setAutoCommit(false); // 设为手动提交，关闭自动提交
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);// 设置隔离级别
            long start = System.currentTimeMillis();
            stmt = conn.createStatement();

            for (int i = 0; i < 20000; i++) {
                stmt.addBatch("insert into user (name,age) values ('gao" + i + "',now())");
            }
            stmt.executeBatch();
            conn.commit(); // 提交事务
            long end = System.currentTimeMillis();
            System.out.println("插入20000条数据，耗时(毫秒)：" + (end - start));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();// 回滚
        } finally {
            // 遵循：resultset-->statment-->connection这样的关闭顺序！一定要将三个trycatch块，分开写！
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (stmt != null) {
                    stmt.close();
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
    }

    // 测试事务的基本概念和用法
    public void transaction() {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            // 加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbctest", "root", "123");

            conn.setAutoCommit(false); // JDBC中默认是true，自动提交事务

            ps1 = conn.prepareStatement("insert into user (name,age) values (?,?)");
            ps1.setObject(1, "高淇");
            ps1.setObject(2, new java.sql.Date(System.currentTimeMillis()));
            ps1.execute();
            System.out.println("插入一个用户,高淇");

            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ps2 = conn.prepareStatement("insert into user (name,age) values (?,?)");
            ps2.setObject(1, "马士兵");
            ps2.setObject(2, new java.sql.Date(System.currentTimeMillis()));
            ps2.execute();
            System.out.println("插入一个用户,马士兵");

            conn.commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            try {
                conn.rollback(); // 回滚
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps1 != null) {
                    ps1.close();
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
    }

    // 测试时间处理(java.sql.Date,Time,Timestamp)
    public void date() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbctest", "root", "123");

            for (int i = 0; i < 1000; i++) {

                ps = conn.prepareStatement("insert into t_user (username,pwd,regTime,lastLoginTime) values (?,?,?,?)");
                ps.setObject(1, "高淇" + i);
                ps.setObject(2, "123456");

                int rand = 100000000 + new Random().nextInt(1000000000);
                // 精确到日期
                java.sql.Date date = new java.sql.Date(System.currentTimeMillis() - rand);
                // 精确到毫秒
                Timestamp stamp = new Timestamp(System.currentTimeMillis() - rand); // 如果需要插入指定日期，可以使用Calendar、DateFormat

                ps.setDate(3, date);
                ps.setTimestamp(4, stamp);
                ps.execute();
            }
            System.out.println("插入一个用户,高淇");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
    }

    // 查某段时间的数据的实现：测试时间处理(java.sql.Date,Time,Timestamp),取出指定时间段的数据
    // select * from t_user where regTime>? and regTime<?")
    // ----------------------------------------------------------------

    // 测试CLOB 文本大对象的使用.包含：将字符串、文件内容插入数据库中的CLOB字段、将CLOB字段值取出来的操作。
    // 测试BLOB 二进制大对象的使用，用法同clob
    public void clob() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Reader r = null;
        try {
            // 加载驱动类
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbctest", "root", "123");

            // ps = conn.prepareStatement("insert into t_user (username,myInfo)
            // values (?,?) ");
            // ps.setString(1, "高淇");
            // ps.setClob(2, new FileReader(new File("d:/a.txt")));
            // //将文本文件内容直接输入到数据库中
            // 将程序中的字符串输入到数据库的CLOB字段中
            // ps.setClob(2, new BufferedReader(new InputStreamReader(new
            // ByteArrayInputStream("aaaabbbbbb".getBytes()))));

            ps = conn.prepareStatement("select * from t_user where id=?");
            ps.setObject(1, 101024);

            rs = ps.executeQuery();
            while (rs.next()) {
                Clob c = rs.getClob("myInfo");
                r = c.getCharacterStream();
                int temp = 0;
                while ((temp = r.read()) != -1) {
                    System.out.print((char) temp);
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (r != null) {
                    r.close();
                }
            } catch (Exception e) {
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
    }

    public static void main(String[] args) {
        new DatabaseConnection().resultSet();
    }
}
