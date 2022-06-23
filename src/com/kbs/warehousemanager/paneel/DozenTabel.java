package com.kbs.warehousemanager.paneel;


import com.kbs.warehousemanager.algoritmes.FirstFitAlgoritme;
import com.kbs.warehousemanager.algoritmes.NextFitAlgoritme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import static com.kbs.warehousemanager.paneel.MagazijnPaneel.productIDArray;

public class DozenTabel extends JPanel {

    //Definieer variabelen
    static int header_height = 75;
    static DefaultTableModel[] tabelModellen = new DefaultTableModel[4];
    static JTable[] tabellen = new JTable[4];
    static JScrollPane[] dozenTabellen = new JScrollPane[4];
    static public ArrayList<Integer> dozenArray = new ArrayList<>();

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
    public static void voegToeFirstFit() {
        for (int i = 0; i < ItemList.items.size(); i++) {
            //HuidigeItem haalt het huidige item op
            String huidigeItem = ItemList.items.get(i);
            //Haalt het gewicht van het product uit de database
            int gewicht = DatabaseConnection.gewichten.get(productIDArray[i]);
            int j = FirstFitAlgoritme.bepaalDoos(gewicht);
            tabelModellen[j - 1].addRow(new String[]{huidigeItem});
            dozenArray.add(j - 1);
        }

    }
    public static void voegToeNextFit() {
      for(int i = 0; i < ItemList.items.size(); i++) {
          String huidigeItem = ItemList.items.get(i);
          int gewicht = DatabaseConnection.gewichten.get(productIDArray[i]);
          int j = NextFitAlgoritme.bepaalDoos(gewicht);
          tabelModellen[j - 1].addRow(new String[]{huidigeItem});
          dozenArray.add(j - 1);
        }
    }
}


