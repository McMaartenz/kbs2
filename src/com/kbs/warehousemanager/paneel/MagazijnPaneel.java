package com.kbs.warehousemanager.paneel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MagazijnPaneel extends JPanel {


    static Boolean[] btnPressed = new Boolean[25];
    static JButton[] buttonArray = new JButton[25];
    private final ItemList itemList;

    public static void resetPanel() {
        for (int i = 0; i < 25; i++) {
                buttonArray[i].setBackground(UIManager.getColor("Button.background"));
                btnPressed[i] = false;
        }
    }

    public MagazijnPaneel(ItemList itemList) {
        System.out.println(DatabaseConnection.orderLineArray);
        this.itemList = itemList;
        setBackground(Color.WHITE);

        //Layout Magazijn
        setLayout(new GridLayout(5, 5, 2, 2));
        //"for"-loop voor alle knoppen toevoegen
        for (int i = 0; i < 25; i++) {
                JButton button = new JButton("Product " + (i+1));
                boolean BtnPressed = false;
                btnPressed[i] = BtnPressed;
                buttonArray[i] = button;
                button.setFont(new Font("Rockwell", Font.PLAIN, 10));
                add(button);
                button.setBorder(new RoundBtn(15));
                button.setForeground(Color.BLACK);
                button.setEnabled(false);
        }
    }

}
//poopybutthole
// TODO ^^ haal dit weg voor de demo jongens