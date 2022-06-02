package com.kbs.warehousemanager.paneel;

import javax.swing.*;
import java.awt.*;

public class MagazijnPaneel extends JPanel {

    //Buttons definiëren en boolean aanmaken voor deze buttons
    static Boolean[] btnPressed = new Boolean[25];
    static JButton[] buttonArray = new JButton[25];

    //De magazijnTabel leegmaken
    public static void resetPanel() {
        for (int i = 0; i < 25; i++) {
                buttonArray[i].setBackground(UIManager.getColor("Button.background"));
                btnPressed[i] = false;
        }
    }

    public MagazijnPaneel(ItemList itemList) {
        //itemList definiëren
        setBackground(Color.WHITE);
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
