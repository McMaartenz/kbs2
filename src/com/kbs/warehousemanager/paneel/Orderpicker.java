package com.kbs.warehousemanager.paneel;

import javax.swing.*;

    public class Orderpicker extends JPanel {
        String[] orderOptions = {"voorbeeldOrder 1", "voorbeeldOrder 2", "voorbeeldOrder 3"};

        public Orderpicker() {
            JComboBox<String> orderList = new JComboBox<>(orderOptions);
            add(orderList);
        }
}
