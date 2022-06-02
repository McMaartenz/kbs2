package com.kbs.warehousemanager.paneel;

import javax.swing.*;
import java.awt.*;

public class ProcesBalk extends JPanel {
    private static double pickProgress;
    private static double packingProgress;
    private static int pickedItems = 2;
    private static int packedItems = 3;
    private static JProgressBar pickProgressBar = new JProgressBar();
    private static JProgressBar packingProgressBar = new JProgressBar();
    public ProcesBalk() {
        setLayout(new GridLayout(2, 1));
        pickProgressBar.setString("Pickproces: " + pickProgress + "%");
        packingProgressBar.setString("Inpakproces: " + packingProgress + "%");

        pickProgressBar.setStringPainted(true);
        packingProgressBar.setStringPainted(true);

        pickProgressBar.setForeground(Color.GREEN);
        packingProgressBar.setForeground(Color.GREEN);

        add(pickProgressBar);
        add(packingProgressBar);

        pickProgressBar.setVisible(true);
        packingProgressBar.setVisible(true);
    }

    public static void updatePickProgressBar() {
        pickProgress = (pickedItems * 100 / ItemList.items.size());
        pickProgressBar.setValue((int) round(pickProgress, 1));
    }

    public static void updatePackingProgressBar() {
        packingProgress = (packedItems * 100 / ItemList.items.size());
        packingProgressBar.setValue((int) round(packingProgress, 1));
    }

    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }
}
