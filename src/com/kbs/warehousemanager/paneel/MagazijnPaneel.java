package com.kbs.warehousemanager.paneel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MagazijnPaneel extends JPanel implements ActionListener {


    static Boolean[][] btnPressed = new Boolean[5][5];
    static JButton[][] buttonArray = new JButton[5][5];
    private final ItemList itemList;

    public static void resetPanel() {
        for (int i = 5; i > 0; i--) {
            for (int j = 1; j <= 5; j++) {
                buttonArray[i-1][j-1].setForeground(Color.BLACK);
                btnPressed[i-1][j-1] = false;
            }
        }
    }

    public MagazijnPaneel(ItemList itemList) {
        this.itemList = itemList;
        setBackground(Color.WHITE);

        //Layout Magazijn
        setLayout(new GridLayout(5, 5, 2, 2));
        //"for"-loop voor alle knoppen toevoegen
        for (int i = 5; i > 0; i--) {
            for (int j = 1; j <= 5; j++) {
                JButton button = new JButton("item " + j + "." + i);
                boolean BtnPressed = false;
                btnPressed[i-1][j-1] = BtnPressed;
                buttonArray[i-1][j-1] = button;
                button.setFont(new Font("Rockwell", Font.PLAIN, 15));
                add(button);
                button.addActionListener(this);
                button.setBorder(new RoundBtn(15));
                button.setForeground(Color.BLACK);
            }
        }
    }

    // Functionaliteit MagazijnKnoppen
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj instanceof JButton) {
            JButton button = ((JButton) e.getSource());
            String text = button.getText();
            for (int i = 5; i > 0; i--) {
                for (int j = 1; j <= 5; j++) {
                    if (text.equals("item " + j + "." + i)) {
                        if (!btnPressed[i-1][j-1]) {
                            itemList.addItem("item " + j + "." + i);
                            btnPressed[i-1][j-1] = true;
                        } else {
                            itemList.removeItem("item " + j + "." + i);
                            btnPressed[i-1][j-1] = false;
                        }
                    }
                }
            }
        }
    }
}
//poopybutthole
// TODO ^^ haal dit weg voor de demo jongens