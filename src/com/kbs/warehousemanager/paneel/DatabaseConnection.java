package com.kbs.warehousemanager.paneel;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

class DatabaseConnection{
    static ResultSet orderlines;

    static HashMap<Integer, ArrayList<Integer>> orderLineArray = new HashMap<>();

    public static void Connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/nerdytest","root","");
            Statement stmt1 = con.createStatement();
            orderlines = stmt1.executeQuery("SELECT * FROM orderlines ORDER BY orderID ASC");
            while(orderlines.next()) {
                int OrderID = orderlines.getInt(1);
                int ProductID = orderlines.getInt(3);
                if(!ControlePaneel.orderOptions.contains(String.valueOf(OrderID))){
                    ControlePaneel.orderOptions.add(String.valueOf(OrderID));
                }
                if(!orderLineArray.containsKey(OrderID)) {
                    orderLineArray.put(OrderID, new ArrayList<>());
                }
                orderLineArray.get(OrderID).add(ProductID);
            }
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }
}
