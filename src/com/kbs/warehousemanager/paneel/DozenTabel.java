package com.kbs.warehousemanager.paneel;


import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class DozenTabel extends JPanel {

    static String[][] dozen = new String[4][];
    String[][] placeholderData ={{"-"}};


    public DozenTabel() {
        setLayout(new GridLayout(1, 4));
        JTable tabel;
        for(int i = 0; i < 4; i++){
            String[] column = {"Doos " + (i+1)};
            if(!ItemList.items.isEmpty()) {
                tabel = new JTable(dozen, column);
            } else {
                tabel = new JTable(placeholderData, column);
            }
            add(tabel);
            setVisible(true);
        }
    }
        public static void voegToe(int maxDoosItems){
            ItemList.getItems();
            System.out.println("Items: " + ItemList.items);
            for (int doosnr = 1; doosnr <= 4; doosnr++) {
                dozen[doosnr - 1] = new String[maxDoosItems];
                for (int i = (doosnr - 1) * maxDoosItems, j = 0; i < Math.min(maxDoosItems * doosnr, ItemList.items.size()); i++, j++) {
                    dozen[doosnr - 1][j] = ItemList.items.get(i);
                }
                System.out.println("doos " + doosnr + ": ");
                for (String item : dozen[doosnr - 1]) {
                    System.out.print(item + ", ");
                }
                System.out.println();
            }
        }
    }


