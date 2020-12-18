package cn.sqh.Domain;


import cn.sqh.JDBCutils.JDBCutils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class 演示jdbc工具类 {

    public static void main(String[] args) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {

            conn = JDBCutils.getConnection();

            String sql = "select * from account";

            stmt = conn.createStatement();

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //获取数据
                int id = rs.getInt(1);
                String name = rs.getString("name");
                double balance = rs.getDouble(3);

                System.out.println(id + "---" + name + "---" + balance);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
           JDBCutils.close(rs,stmt,conn);
        }
    }

}
