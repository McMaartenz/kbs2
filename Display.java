import javax.swing.*;
import java.awt.*;

class Display extends JFrame {

    private Database db;

    /* Main panel */
    private JTabbedPane keuzeMenu;
    private JPanel orderPanel;
    private JPanel klantPanel;
    private JPanel inpakPanel;

    public Display(Database db) {
        try { // Set system window appearance
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        this.db = db;

        setSize(480, 360);
        setLayout(new GridLayout(1, 1));

        orderPanel = new JPanel();
        klantPanel = new KlantPanel(this);
        inpakPanel = new JPanel();

        keuzeMenu = new JTabbedPane();
        keuzeMenu.addTab("Order", orderPanel);
        keuzeMenu.addTab("Klant", klantPanel);
        keuzeMenu.addTab("Inpak", inpakPanel);

        add(keuzeMenu);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public Database getDB() {
        return db;
    }
}
