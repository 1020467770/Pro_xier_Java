package cn.sqh.JDBCutils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class JDBCutils {

    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    static {

        try {

            Properties pro=new Properties();

            ClassLoader classLoader = JDBCutils.class.getClassLoader();
            InputStream ras = classLoader.getResourceAsStream("jdbc.properties");

            pro.load(ras);

            url= pro.getProperty("url");
            user= pro.getProperty("user");
            password= pro.getProperty("password");
            driver= pro.getProperty("driver");
            //System.out.println(driver);

            Class.forName(driver);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * @return 连接对象
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,password);
    }


    public static void close(Statement stmt,Connection conn){
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs, Statement stmt, Connection conn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
