import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

class KlantPanel extends JPanel implements ActionListener
{

	private Database db;
	private JFrame parent;

	private JScrollPane klantTableScroller;
	private JTable klantTable;
	private JButton klantToevoegen;
	private JButton klantBewerken;
	private JButton klantVerwijderen;

	// toevoegen dialog
	private JDialog dialogToevoegen;
	private JButton dialogToevoegenConfirm;

	// bewerken dialog
	private JDialog dialogBewerken;
	private JButton dialogBewerkenConfirm;

	public KlantPanel(Display parent)
	{
		this.parent = parent;
		db = parent.getDB();
		klantTableScroller = new JScrollPane();
		DefaultTableModel model = new DefaultTableModel();
		klantTable = new ReadonlyTable(model);

		// Krijg model van tabel om wijzigingen door te voeren
		klantTable.getTableHeader().setReorderingAllowed(false);

		// Headers
		String[] headers = new String[]{"Klantnr", "Voornaam", "Achternaam", "Functie"};
		for (String header : headers)
		{
			model.addColumn(header);
		}

		/* TODO: VOEG HIER KLANTENTABEL IN */
		model.addRow(new Object[]{"1173066", "Maarten", "van Keulen", "Programmeur"});
		model.addRow(new Object[]{"1173055", "Netraam", "van Koln", "CEO"});
		model.addRow(new Object[]{"1173044", "Teun", "Smith", "Tester"});
		model.addRow(new Object[]{"1173033", "Jan", "Piet", "Koper"});
		model.addRow(new Object[]{"1173022", "Don", "Sperziboom", "Mens"});
		klantTableScroller.setViewportView(klantTable);

		setLayout(new GridBagLayout()); // Constraints van de componenten
		GridBagConstraints gbc = new GridBagConstraints();
		{
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

		add(klantTableScroller, gbc);

		gbc.gridheight = 1;
		gbc.gridx = 1;
		add(klantToevoegen, gbc);

		gbc.gridy = 1;
		add(klantBewerken, gbc);

		gbc.gridy = 2;
		add(klantVerwijderen, gbc);
	}


	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object src = ae.getSource();
		if (src instanceof JButton)
		{
			JButton srcBtn = (JButton) src;
			if (srcBtn == klantToevoegen)
			{
				dialogToevoegen = new JDialog(parent, "Voeg een klant toe", true);
				dialogToevoegen.setSize(360, 120);
				dialogToevoegen.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				dialogToevoegen.setLayout(new GridLayout(2, 1));

				JScrollPane scrollPane = new JScrollPane();
				JTable table = new JTable(new DefaultTableModel());
				table.getTableHeader().setReorderingAllowed(false);
				DefaultTableModel model = ((DefaultTableModel) table.getModel());
				String[] headers = new String[]{"Klantnr", "Voornaam", "Achternaam", "Functie"};
				for (String header : headers)
				{
					model.addColumn(header);
				}
				model.addRow(new Object[]{});
				scrollPane.setViewportView(table);

				dialogToevoegenConfirm = new JButton("Voeg toe");
				dialogToevoegenConfirm.addActionListener(this);

				dialogToevoegen.add(scrollPane);
				dialogToevoegen.add(dialogToevoegenConfirm);

				dialogToevoegen.setVisible(true);

				String[] data = new String[4];
				for (int i = 0; i < 4; i++)
				{
					Object obj = model.getValueAt(0, i);
					if (obj == null)
					{
						obj = "";
					}
					data[i] = obj.toString();
				}

				DefaultTableModel mainmodel = ((DefaultTableModel) klantTable.getModel());
				mainmodel.addRow(data);

				db.addRecordToDatabase(data);
			}
			else if (srcBtn == klantVerwijderen)
			{
				int index = klantTable.getSelectedRow();

				if (index == -1)
				{
					Fout.toon(parent, "Selecteer een regel om weg te halen");
					return;
				}

				int confirmation = JOptionPane.showConfirmDialog(this, "Verwijder regel nr. " + (index + 1) + "?", "Confirm dit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (confirmation == JOptionPane.YES_OPTION)
				{
					DefaultTableModel model = (DefaultTableModel) klantTable.getModel();
					db.removeRecordFromDatabase(model.getValueAt(index, 0));
					model.removeRow(index);
				}
			}
			else if (srcBtn == dialogToevoegenConfirm)
			{
				dialogToevoegen.dispose();
			}
			else if (srcBtn == dialogBewerkenConfirm)
			{
				dialogBewerken.dispose();
			}
			else if (srcBtn == klantBewerken)
			{
				int index = klantTable.getSelectedRow();

				if (index == -1)
				{
					Fout.toon(parent, "Selecteer een regel om te bewerken");
					return;
				}

				dialogBewerken = new JDialog(parent, "Bewerk een klant", true);
				dialogBewerken.setSize(360, 120);
				dialogBewerken.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				dialogBewerken.setLayout(new GridLayout(2, 1));

				JScrollPane scrollPane = new JScrollPane();
				JTable table = new JTable(new DefaultTableModel());
				DefaultTableModel model = ((DefaultTableModel) table.getModel());
				String[] headers = new String[]{"Klantnr", "Voornaam", "Achternaam", "Functie"};
				for (String header : headers)
				{
					model.addColumn(header);
				}

				DefaultTableModel mainmodel = ((DefaultTableModel) klantTable.getModel());

				{
					String[] data = new String[4];
					for (int i = 0; i < 4; i++)
					{
						data[i] = mainmodel.getValueAt(index, i).toString();
					}
					model.addRow(data);
				}
				scrollPane.setViewportView(table);

				dialogBewerkenConfirm = new JButton("Voeg toe");
				dialogBewerkenConfirm.addActionListener(this);

				dialogBewerken.add(scrollPane);
				dialogBewerken.add(dialogBewerkenConfirm);

				dialogBewerken.setVisible(true);

				{
					String[] data = new String[4];

					for (int i = 0; i < 4; i++)
					{
						Object obj = model.getValueAt(0, i);
						if (obj == null)
						{
							obj = "";
						}
						data[i] = obj.toString();
					}

					for (int i = 0; i < 4; i++)
					{
						mainmodel.setValueAt(data[i], index, i);
					}
					db.editRecordInDatabase(mainmodel.getValueAt(index, 0), data);
				}
			}
		}
	}
}
