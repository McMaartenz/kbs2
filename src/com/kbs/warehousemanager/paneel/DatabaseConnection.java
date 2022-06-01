package com.kbs.warehousemanager.paneel;

import java.sql.*;

class DatabaseConnection{
    static ResultSet orderlines;
    static ResultSet orders;

    public static void Connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/testschema","root","");
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();
            orderlines = stmt1.executeQuery("select * from orderlines");
            orders = stmt2.executeQuery("select * from orders");
            while(orderlines.next())
                System.out.println(orderlines.getInt(1)+"  "+orderlines.getString(2)+"  "+orderlines.getString(3));
            while(orders.next())
                ControlePaneel.orderOptions.add(String.valueOf(orders.getInt(1)));
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }
}
