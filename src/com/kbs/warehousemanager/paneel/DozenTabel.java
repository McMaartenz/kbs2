package com.kbs.warehousemanager.paneel;


import com.kbs.warehousemanager.algoritmes.FirstFitAlgoritme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DozenTabel extends JPanel {

    //Definieer variabelen
    static int header_height = 75;
    static DefaultTableModel[] tabelModellen = new DefaultTableModel[4];
    static JTable[] tabellen = new JTable[4];
    static JScrollPane[] dozenTabellen = new JScrollPane[4];

    public static void resetDozen() {
        for(int i = 0; i < 4; i++) {
            tabelModellen[i].setRowCount(0);
        }
    }

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

    //Dingen toevoegen aan de DozenTabel met het first fit algoritme
    public static void voegToeFirstFit(){
        for(int i = 0; i < ItemList.items.size(); i++) {
            //HuidigeItem haalt het huidige item op, en itemNummer haalt alleen het nummer op van de huidigeItem string
            String huidigeItem = ItemList.items.get(i);
            String itemNummer = huidigeItem.replaceAll("[^0-9]", "");
            //Haalt het gewicht van het product uit de database
            int gewicht = DatabaseConnection.gewichten.get(Integer.parseInt(itemNummer) - 1);
            tabelModellen[FirstFitAlgoritme.bepaalDoos(gewicht) - 1].addRow(new String[]{huidigeItem});
        }
    }

}


