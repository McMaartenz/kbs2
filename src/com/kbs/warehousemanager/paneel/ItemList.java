package com.kbs.warehousemanager.paneel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ItemList extends JPanel {

    //Array met items definiëren en ItemList Array definiëren
    static ArrayList<String> items = new ArrayList<>();
    static DefaultListModel<String> itemList = new DefaultListModel<>();


    // Het aanmaken van de ItemList
    ItemList(){
        setLayout(new BorderLayout());
        JList<String> list = new JList<>(itemList);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setFont(new Font("Rockwell", Font.PLAIN, 20));
        list.setCellRenderer(getRenderer());
        add(list, BorderLayout.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    //Centreren van Items in de ItemList en een kader om het lijstelement plaatsen
    private ListCellRenderer<? super String> getRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super
                        .getListCellRendererComponent(list, value, index, isSelected,
                                cellHasFocus);
                listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0,
                        0, 1, 0, Color.BLACK));
                listCellRendererComponent.setHorizontalAlignment(JLabel.CENTER);
                return listCellRendererComponent;
            }
        };
    }

    //Leegmaken van de ItemList
    public static void clearList() {
        items.clear();
        itemList.clear();
    }

    //Toevoegen van items aan de ItemList
    public static void addItem(String name) {
        items.add(name);
        itemList.addElement(name);
    }
    public static void removeItem(String name) {
        items.remove(name);
        itemList.removeElement(name);
    }
}

