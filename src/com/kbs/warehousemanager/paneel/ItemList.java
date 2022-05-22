package com.kbs.warehousemanager.paneel;

import javax.swing.*;
import java.util.ArrayList;

public class ItemList extends JPanel {
    static ArrayList<String> items = new ArrayList<>();
    private JLabel itemLabel;

    public static ArrayList<String> getItems() {
        return items;
    }

    ItemList(){
        items.add("items:");
        itemLabel = new JLabel(items.toString());
        add(itemLabel);
    }

    public void refresh(){
        itemLabel.setText(items.toString());
        repaint();
    }

    public void addItem(String name) {
        items.add(name);
    }

    public void removeItem(String name) {
        items.remove(name);
    }

}

