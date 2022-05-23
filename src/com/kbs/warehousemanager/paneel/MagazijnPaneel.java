package com.kbs.warehousemanager.paneel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MagazijnPaneel extends JPanel implements ActionListener {

    static Boolean[] btnPressed = new Boolean[25];
        private ItemList itemList;

        public MagazijnPaneel(ItemList itemList) {
            this.itemList = itemList;

            setLayout(new GridLayout(5, 5));
            for (int i = 0; i < 25; i++) {
                JButton button = new JButton("item " + (i + 1));
                boolean BtnPressed = false;
                btnPressed[i] = BtnPressed;
                add(button);
                button.addActionListener(this);
                button.setBorder(new RoundBtn(15));
                button.setForeground(Color.BLACK);
            }
        }


    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj instanceof JButton ) {
            String text = ((JButton) e.getSource()).getText();
            JButton button = ((JButton) e.getSource());
            for (int i = 0; i < 25; i++) {
                if(text.equals("item " + (i+1))){
                    if(!btnPressed[i]) {
                        itemList.addItem("item " + (i+1));
                        button.setForeground(Color.RED);
                        btnPressed[i] = true;
                        itemList.refresh();
                    } else {
                        button.setForeground(Color.BLACK);
                        itemList.removeItem("item " + (i+1));
                        btnPressed[i] = false;
                        itemList.refresh();
                    }
                }
            }
        }
    }
}

