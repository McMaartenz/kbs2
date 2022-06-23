package com.kbs.warehousemanager.paneel;


import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

class DatabaseConnection {

    //Resultaten definiëren
    static ResultSet orderlines;
    static ResultSet producten;

    //Gegevens opslaan in variabelen
    static HashMap<Integer, ArrayList<Integer>> orderLineArray = new HashMap<>();
    static ArrayList<Integer> gewichten = new ArrayList<>();
    static HashMap<Integer, Point> coordinates = new HashMap<>();
    static ArrayList<String> namen = new ArrayList<>();

    //Verbinding-functie
    public static void Connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/nerdytest","root","");
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();

            //Queries
            orderlines = stmt1.executeQuery("SELECT * FROM orderlines ORDER BY orderID ASC");
            producten = stmt2.executeQuery("SELECT * FROM product ORDER BY ProductID ASC");


            //Data ophalen uit de Queries en de opgehaalde data opslaan in variabelen
            while(producten.next()) {
                int ProductID = producten.getInt(1);
                int weight = producten.getInt(2);
                int x_coordinate = producten.getInt(3);
                int y_coordinate = producten.getInt(4);
                String naam = producten.getString(5);
                gewichten.add(weight);
                namen.add(naam);
                coordinates.put(ProductID, new Point(x_coordinate, y_coordinate));
            }
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
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
