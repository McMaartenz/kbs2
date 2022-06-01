package com.kbs.warehousemanager.paneel;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseConnection {
    public static void main(String[] args) throws SQLException, ClassNotFoundException{
        Class.forName(" ");
        Connection conn = DriverManager.getConnection(" ");

        Statement st = conn.createStatement();
        String sqlStr = null; // TODO: 31-5-2022 QUERY
        ResultSet rs = st.executeQuery(sqlStr);
        while(rs.next()) {
            System.out.println(rs.getString(" "));
        }
    }
}
