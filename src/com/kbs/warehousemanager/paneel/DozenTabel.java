package com.kbs.warehousemanager.paneel;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DozenTabel extends JPanel {

    static ArrayList<String> doos1 = new ArrayList<>();
    static ArrayList<String> doos2 = new ArrayList<>();
    static ArrayList<String> doos3 = new ArrayList<>();
    static ArrayList<String> doos4 = new ArrayList<>();
    int maxDoosItems = 5;

    public DozenTabel() {
        setLayout(new GridLayout(1, 4));
        setVisible(true);
    }
        public static void voegToe(int maxDoosItems) {
            ItemList.getItems();
            if (ItemList.items.size() < maxDoosItems) {
                doos1 = ItemList.items;
                System.out.println(doos1);
                ItemList.items.clear();
            } else if (doos2.size() < maxDoosItems) {
                doos2 = ItemList.items;
                System.out.println(doos1);
                ItemList.items.clear();
            } else if (doos3.size() < maxDoosItems) {
                doos3 = ItemList.items;
                System.out.println(doos1);
                ItemList.items.clear();
            } else if (doos4.size() < maxDoosItems) {
                doos4 = ItemList.items;
                System.out.println(doos1);
                ItemList.items.clear();
            }
        }
    }

