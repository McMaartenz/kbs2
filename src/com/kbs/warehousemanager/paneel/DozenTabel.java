package com.kbs.warehousemanager.paneel;


import com.kbs.warehousemanager.algoritmes.NextFitAlgoritme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DozenTabel extends JPanel {

    //Definieer variabelen
    static int header_height = 75;
    static int[][] dozen = new int[4][];
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

    //Dingen toevoegen aan de DozenTabel met het next fit algoritme
    public static void voegToeNextFit() {
        for(int i = 0; i < ItemList.items.size(); i++) {
            String huidigeItem = ItemList.items.get(i);
            tabelModellen[NextFitAlgoritme.bepaalDoos() - 1].addRow(new String[]{huidigeItem});
        }
    }

}


