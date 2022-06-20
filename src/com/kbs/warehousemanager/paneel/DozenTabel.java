package com.kbs.warehousemanager.paneel;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DozenTabel extends JPanel {

    //Definieer variabelen
    static int header_height = 75;
    static String[][] dozen = new String[4][];
    static DefaultTableModel[] tabelModellen = new DefaultTableModel[4];
    static JTable[] tabellen = new JTable[4];
    static JScrollPane[] dozenTabellen = new JScrollPane[4];

    //Constructor DozenTabel
    public DozenTabel() {
        setLayout(new GridLayout(1, 4));
        for(int i = 0; i < 4; i++){
            tabelModellen[i] = new DefaultTableModel();
            tabelModellen[i].addColumn("Doos " + (i+1));
            tabellen[i] = new JTable(tabelModellen[i]);
            dozenTabellen[i] = new JScrollPane(tabellen[i]);
            add(dozenTabellen[i]);
            tabellen[i].setEnabled(false);
            tabellen[i].getTableHeader().setPreferredSize(new Dimension(dozenTabellen[i].getWidth(),header_height));
            setVisible(true);
        }
    }

    //Dingen toevoegen aan de DozenTabel
    public static void voegToe(int maxDoosItems){
        for (int doosnr = 1; doosnr <= 4; doosnr++) {
            dozen[doosnr - 1] = new String[maxDoosItems];
            for (int i = (doosnr - 1) * maxDoosItems, j = 0; i < Math.min(maxDoosItems * doosnr, ItemList.items.size()); i++, j++) {
                dozen[doosnr - 1][j] = ItemList.items.get(i);
            }
            if(dozen[doosnr - 1] == null) {
                assert dozen[doosnr - 1] != null;
                for (String item : dozen[doosnr - 1]) {
                    tabelModellen[doosnr - 1].addRow(new String[]{item});
                }
            } else {
                tabelModellen[doosnr - 1].setRowCount(0);
                for (String item : dozen[doosnr - 1]) {
                    tabelModellen[doosnr - 1].addRow(new String[]{item});
                }
            }
        }
    }
}


