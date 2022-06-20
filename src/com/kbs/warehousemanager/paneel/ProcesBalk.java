package com.kbs.warehousemanager.paneel;

import javax.swing.*;
import java.awt.*;

public class ProcesBalk extends JPanel {
    private static int pickVooruitgang;
    private static int inpakVooruitgang;
    private static int gepickteItems = 2;
    private static int ingepakteItems = 3;
    private static JProgressBar pickProcesBalk = new JProgressBar();
    private static JProgressBar packingProcesBalk = new JProgressBar();
    public ProcesBalk() {
        setLayout(new GridLayout(2, 1));
        pickProcesBalk.setString("Pickproces: " + pickVooruitgang + "%");
        packingProcesBalk.setString("Inpakproces: " + inpakVooruitgang + "%");

        pickProcesBalk.setStringPainted(true);
        packingProcesBalk.setStringPainted(true);

        pickProcesBalk.setForeground(Color.GREEN);
        packingProcesBalk.setForeground(Color.GREEN);

        add(pickProcesBalk);
        add(packingProcesBalk);

        pickProcesBalk.setVisible(true);
        packingProcesBalk.setVisible(true);
    }

    public static void veranderPickProcesBalk() {
        pickVooruitgang = (gepickteItems * 100 / ItemList.items.size());
        pickProcesBalk.setValue(pickVooruitgang);
        pickProcesBalk.setString("Pickproces: " + pickVooruitgang + "%");
    }

    public static void veranderInpakProcesBalk() {
        inpakVooruitgang = (ingepakteItems * 100 / ItemList.items.size());
        packingProcesBalk.setValue(inpakVooruitgang);
        packingProcesBalk.setString("Inpakproces: " + inpakVooruitgang + "%");
    }

}
