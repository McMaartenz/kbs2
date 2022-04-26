import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.SwingConstants.CENTER;

public class OrderPanel extends JPanel implements ActionListener
{

	private Database db;
	private JFrame parent;

	private JPanel robotMap;
	private JPanel logo;

	private JScrollPane orderScroller;
	private JScrollPane productScroller;
	private JTable orderTable;
	private JTable productTable;
	private DefaultTableModel orderTableModel;
	private DefaultTableModel productTableModel;

	private JLabel orders;
	private JLabel producten;
	private JLabel kiesOrder;

	private JProgressBar ophalenProgress;
	private JProgressBar inpakProgress;
	private JProgressBar totalProgress;

	private JButton ok;
	private JButton cancel;
	private JButton herhaal;

	public OrderPanel(Display parent)
	{
		this.parent = parent;
		db = parent.getDB();

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		{
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.ipadx = 0;
			gbc.ipady = 0;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			gbc.gridheight = 1;
			gbc.fill = GridBagConstraints.BOTH;
		}

		robotMap = new JPanel()
		{
			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);

				// Grootte gedefinieerd per schermontwerp
				final Dimension GRID_SIZE = new Dimension(6, 6);
				final int CELL_SIZE = Math.min(getWidth(), getHeight()) / Math.min(GRID_SIZE.width, GRID_SIZE.height);

				g.drawLine(0, 0, CELL_SIZE, CELL_SIZE);
				for (int i = 0; i < GRID_SIZE.width; i++)
				{
					g.drawLine(0, CELL_SIZE * i,GRID_SIZE.width * CELL_SIZE, CELL_SIZE * i);
					g.drawLine(CELL_SIZE * i, 0, CELL_SIZE * i, GRID_SIZE.height * CELL_SIZE);

					if (i == 0)
					{
						continue;
					}
					g.drawString(Integer.toString(i), CELL_SIZE * (i + 1) - CELL_SIZE / 2 - 3, CELL_SIZE / 2 + 5);
					g.drawString(Character.toString('A' + (i - 1)), CELL_SIZE / 2 - 3, CELL_SIZE * (i + 1) - CELL_SIZE / 2 + 5);
				}
				g.setColor(getBackground());
				g.drawLine(CELL_SIZE, 1, CELL_SIZE, CELL_SIZE - 1);
				g.drawLine(1, CELL_SIZE, CELL_SIZE - 1, CELL_SIZE);

				// TODO: Teken robot plaatje
			}
		};

		logo = new Logo();

		orderScroller = new JScrollPane();
		productScroller = new JScrollPane();
		orderTableModel = new DefaultTableModel();
		productTableModel = new DefaultTableModel();
		orderTable = new ReadonlyTable(orderTableModel);
		productTable = new ReadonlyTable(productTableModel);

		/* TODO: VOEG HIER ORDER & PRODUCT TABLE DATA */
		orderTableModel.addColumn("Ordernr");
		productTableModel.addColumn("Product");

		orderTableModel.addRow(new Object[]{"224466"});
		orderTableModel.addRow(new Object[]{"113355"});
		orderTableModel.addRow(new Object[]{"336699"});

		productTableModel.addRow(new Object[]{"Product 1"});
		productTableModel.addRow(new Object[]{"Product 2"});
		productTableModel.addRow(new Object[]{"Product 3"});
		productTableModel.addRow(new Object[]{"Product 4"});

		orderTable.getTableHeader().setReorderingAllowed(false);
		productTable.getTableHeader().setReorderingAllowed(false);
		orderScroller.setViewportView(orderTable);
		productScroller.setViewportView(productTable);

		orders = new JLabel("Orders:");
		producten = new JLabel("Producten:");
		kiesOrder = new JLabel("Kies uw order", CENTER);

		ophalenProgress = new JProgressBar();
		inpakProgress = new JProgressBar();
		totalProgress = new JProgressBar();
		ophalenProgress.setStringPainted(true);
		inpakProgress.setStringPainted(true);
		totalProgress.setStringPainted(true);

		ok = new JButton("Stuur naar inpakmachine");
		cancel = new JButton("Cancel");
		herhaal = new JButton("Herhaal pickronde");

		ok.addActionListener(this);
		cancel.addActionListener(this);
		herhaal.addActionListener(this);

		add(robotMap, gbc);

		gbc.gridy++;
		add(orders, gbc);

		gbc.gridy++;
		gbc.gridheight = 3;
		add(orderScroller, gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		add(producten, gbc);

		gbc.gridy++;
		gbc.gridheight = 4;
		add(productScroller, gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		add(kiesOrder, gbc);

		gbc.gridy += 2;
		gbc.gridheight = 1;
		add(ophalenProgress, gbc);

		gbc.gridy++;
		add(inpakProgress, gbc);

		gbc.gridy++;
		add(totalProgress, gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		add(logo, gbc);

		gbc.gridy++;
		add(cancel, gbc);

		gbc.gridy++;
		add(ok, gbc);

		gbc.gridy += 2;
		add(herhaal, gbc);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object src = ae.getSource();
		if (src instanceof JButton)
		{
			JButton srcBtn = (JButton) src;
			if (srcBtn == ok)
			{
				Selectie selectie = new Selectie(Selectie.Optie.DISPOSED);
				new AlgoritmeSelectie(parent, "Selecteer een algoritme", true, selectie);
				// TODO: Doe iets met selectie
				if (selectie.optie != Selectie.Optie.DISPOSED)
				{
					((Display) parent).switchTab(Tab.INPAK_TAB);
				}
			}
		}
	}
}
