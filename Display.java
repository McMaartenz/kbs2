import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class Display extends JFrame implements ActionListener {
    
    /* Main panel */
    JTabbedPane keuzeMenu;
    JPanel orderPanel;
    JPanel klantPanel;
    JPanel inpakPanel;

    /* Klant panel */
    JScrollPane klantTableScroller;
    JTable klantTable;
    JButton klantToevoegen;
    JButton klantBewerken;
    JButton klantVerwijderen;

    public Display() {
        try { // Set system window appearance
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        setSize(480, 360);
        setLayout(new GridLayout(1, 1));

        orderPanel = new JPanel();
        klantPanel = new JPanel();
        inpakPanel = new JPanel();

        klantTableScroller = new JScrollPane();
        klantTable = new JTable(new DefaultTableModel()) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        
        };
        
        // Krijg model van tabel om wijzigingen door te voeren
        DefaultTableModel model = ((DefaultTableModel)klantTable.getModel());
        
        String[] headers = new String[] {"Klantnr", "Voornaam", "Achternaam", "Functie"};
        for (String header : headers) {
            model.addColumn(header);
        }
        
        model.addRow(new Object[] {"1173066", "Maarten", "van Keulen", "Programmeur"});
        klantTableScroller.setViewportView(klantTable);
        

        klantPanel.setLayout(new GridBagLayout()); // Constraints van de componenten
        GridBagConstraints gbc = new GridBagConstraints(); {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.ipadx = 0;
            gbc.ipady = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.gridheight = 3;
            gbc.fill = GridBagConstraints.BOTH;
        }
        
        klantToevoegen = new JButton("Klant toevoegen");
        klantBewerken = new JButton("Klant bewerken");
        klantVerwijderen = new JButton("Klant verwijderen");

        klantToevoegen.addActionListener(this);
        klantBewerken.addActionListener(this);
        klantVerwijderen.addActionListener(this);

        klantPanel.add(klantTableScroller, gbc);
        gbc.gridheight = 1;
        gbc.gridx = 1;
        klantPanel.add(klantToevoegen, gbc);
        gbc.gridy = 1;
        klantPanel.add(klantBewerken, gbc);
        gbc.gridy = 2;
        klantPanel.add(klantVerwijderen, gbc);

        keuzeMenu = new JTabbedPane();
        keuzeMenu.addTab("Order", orderPanel);
        keuzeMenu.addTab("Klant", klantPanel);
        keuzeMenu.addTab("Inpak", inpakPanel);

        add(keuzeMenu);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        if (src instanceof JButton) {
            JButton srcBtn = (JButton)src;
            if (srcBtn == klantToevoegen) {
                // jdialogje
            }
            else if(srcBtn == klantBewerken) {
                // jdialogje
            }
            else if(srcBtn == klantVerwijderen) {
                int index = klantTable.getSelectedRow();
                // jinfopan CONFIRMATION
                //verwijderen zoja
                ((DefaultTableModel)klantTable.getModel()).removeRow(index);
                System.out.println(index);
            }
        }
        System.out.println(ae);
    }
}
