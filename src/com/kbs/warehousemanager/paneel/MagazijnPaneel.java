package com.kbs.warehousemanager.paneel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MagazijnPaneel extends JPanel implements ActionListener {

    public static void resetPanel() {
        for (int i = 0; i<25; i++) {
            buttonArray[i].setForeground(Color.BLACK);
            btnPressed[i] = false;
        }
    }

    static Boolean[] btnPressed = new Boolean[25];
    static JButton[] buttonArray = new JButton[25];
        private ItemList itemList;

        public MagazijnPaneel(ItemList itemList){
            this.itemList = itemList;
            setBackground(Color.WHITE);

            //Layout Magazijn
            setLayout(new GridLayout(5, 5, 2, 2));
            //"for"-loop voor alle knoppen toevoegen
            for (int i = 0; i < 25; i++) {
                JButton button = new JButton("item " + (i + 1));
                boolean BtnPressed = false;
                btnPressed[i] = BtnPressed;
                buttonArray[i] = button;
                button.setFont(new Font("Rockwell", Font.PLAIN, 15));
                add(button);
                button.addActionListener(this);
                button.setBorder(new RoundBtn(15));
                button.setForeground(Color.BLACK);
            }
        }

    // Functionaliteit MagazijnKnoppen
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj instanceof JButton ) {
            JButton button = ((JButton) e.getSource());
            String text = button.getText();
            for (int i = 0; i < 25; i++) {
                if(text.equals("item " + (i+1))){
                    if(!btnPressed[i]) {
                        itemList.addItem("item " + (i+1));
                        button.setForeground(Color.RED);
                        btnPressed[i] = true;
                    } else {
                        button.setForeground(Color.BLACK);
                        itemList.removeItem("item " + (i+1));
                        btnPressed[i] = false;
                    }
                }
            }
        }
    }
}
//poopybutthole