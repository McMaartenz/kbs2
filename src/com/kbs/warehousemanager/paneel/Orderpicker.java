package com.kbs.warehousemanager.paneel;

import javax.swing.*;
import java.awt.*;

public class Orderpicker extends JPanel {
        String[] orderOptions = {"voorbeeldOrder 1", "voorbeeldOrder 2", "voorbeeldOrder 3"};

        public Orderpicker() {
            setLayout(new GridLayout(5, 1));
            JComboBox<String> orderList = new JComboBox<>(orderOptions);
            add(orderList);
        }
}
