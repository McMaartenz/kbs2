import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

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
    
    // toevoegen dialog
    JDialog klantDialogToevoegen;
    JButton klantDialogToevoegenConfirm;

    // bewerken dialog
    JDialog klantDialogBewerken;
    JButton klantDialogBewerkenConfirm;

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
        
        // Headers
        String[] headers = new String[] {"Klantnr", "Voornaam", "Achternaam", "Functie"};
        for (String header : headers) {
            model.addColumn(header);
        }
        
        /* VOEG HIER KLANTENTABEL IN */
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
                klantDialogToevoegen = new JDialog(this, "Voeg een klant toe", true);
                klantDialogToevoegen.setSize(360, 120);
                klantDialogToevoegen.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                klantDialogToevoegen.setLayout(new GridLayout(2, 1));

                JScrollPane scrollPane = new JScrollPane();
                JTable table = new JTable(new DefaultTableModel());
                DefaultTableModel model = ((DefaultTableModel)table.getModel());
                String[] headers = new String[] {"Klantnr", "Voornaam", "Achternaam", "Functie"};
                for (String header : headers) {
                    model.addColumn(header);
                }
                model.addRow(new Object[] {});
                scrollPane.setViewportView(table);

                klantDialogToevoegenConfirm = new JButton("Voeg toe");
                klantDialogToevoegenConfirm.addActionListener(this);

                klantDialogToevoegen.add(scrollPane);
                klantDialogToevoegen.add(klantDialogToevoegenConfirm);

                klantDialogToevoegen.setVisible(true);
                
                String[] data = new String[4];
                for (int i = 0; i < 4; i++) {
                    data[i] = model.getValueAt(0, i).toString();
                }

                DefaultTableModel mainmodel = ((DefaultTableModel)klantTable.getModel());
                mainmodel.addRow(data);

                // TODO: voeg toe naar database
            }
            else if (srcBtn == klantVerwijderen) {
                int index = klantTable.getSelectedRow();

                int confirmation = JOptionPane.showConfirmDialog(this, "Verwijder regel nr. " + index + "?", "Confirm dit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirmation == JOptionPane.YES_OPTION) {
                    ((DefaultTableModel)klantTable.getModel()).removeRow(index);
                    // TODO: Verwijder uit database
                }
            }
            else if (srcBtn == klantDialogToevoegenConfirm) {
                klantDialogToevoegen.dispose();
            }
            else if (srcBtn == klantDialogBewerkenConfirm) {
                klantDialogBewerken.dispose();
            }
            else if (srcBtn == klantBewerken) {
                int index = klantTable.getSelectedRow();

                klantDialogBewerken = new JDialog(this, "Bewerk een klant", true);
                klantDialogBewerken.setSize(360, 120);
                klantDialogBewerken.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                klantDialogBewerken.setLayout(new GridLayout(2, 1));

                JScrollPane scrollPane = new JScrollPane();
                JTable table = new JTable(new DefaultTableModel());
                DefaultTableModel model = ((DefaultTableModel)table.getModel());
                String[] headers = new String[] {"Klantnr", "Voornaam", "Achternaam", "Functie"};
                for (String header : headers) {
                    model.addColumn(header);
                }

                DefaultTableModel mainmodel = ((DefaultTableModel)klantTable.getModel());

                String[] data = new String[4];
                for (int i = 0; i < 4; i++) {
                    data[i] = mainmodel.getValueAt(index, i).toString();
                }
                model.addRow(data);
                scrollPane.setViewportView(table);

                klantDialogBewerkenConfirm = new JButton("Voeg toe");
                klantDialogBewerkenConfirm.addActionListener(this);

                klantDialogBewerken.add(scrollPane);
                klantDialogBewerken.add(klantDialogBewerkenConfirm);

                klantDialogBewerken.setVisible(true);

                for (int i = 0; i < 4; i++) {
                    String value = model.getValueAt(0, i).toString();
                    mainmodel.setValueAt(value, index, i);
                    System.out.println(value);
                }

                // TODO: verander in database
            }
        }
    }
}
