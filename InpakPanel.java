import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.swing.SwingConstants.CENTER;

public class InpakPanel extends JPanel implements ActionListener {

	private Database db;
	private JFrame parent;

	private JLabel ingepakteDozen;
	private JLabel gekozenAlgoritme;
	private JLabel huidigInpakProces;

	private JPanel logo;
	private JPanel doos;

	private JScrollPane tableScroller;
	private JTable table;
	private DefaultTableModel model;

	private JButton openAlgoritmeSelectie;

	public InpakPanel(Display parent) {
		this.parent = parent;
		db = parent.getDB();

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints(); {
			gbc.gridx = 2;
			gbc.gridy = 0;
			gbc.ipadx = 0;
			gbc.ipady = 0;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			gbc.gridheight = 1;
			gbc.fill = GridBagConstraints.BOTH;
		}

		ingepakteDozen = new JLabel("Ingepakte dozen", CENTER);
		gekozenAlgoritme = new JLabel("<html>Gekozen algoritme<br>ALGORITME</html>", CENTER);
		huidigInpakProces = new JLabel("Huidig inpakproces", CENTER);

		logo = new JPanel() {

			BufferedImage logo = loadImage();

			private BufferedImage loadImage() {
				BufferedImage result = null;
				try {
					result = ImageIO.read(new File("img/logo.jpg"));
				}
				catch (IOException ie) {
					System.out.println("Unable to load papaya image");
				}
				return result;
			}

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (logo == null) return;
				g.drawImage(logo, 0, 0, getWidth(), getHeight(), null);
			}
		};

		doos = new JPanel() { // TODO Teken een doos
			int getal = 9454;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				Dimension size = this.getSize();
				int offset = size.width / 6;
				g.drawLine(0, offset, offset, 0);
				g.drawLine(size.width, offset, size.width - offset, 0);

				// box
				g.setColor(new Color(239, 184, 100));
				g.fillRect(offset, 0, size.width - (offset * 2), size.height);
				g.setColor(Color.BLACK);
				g.drawRect(offset, 0, size.width - (offset * 2), size.height);

				g.setFont(new Font("Arial", Font.PLAIN, size.width / 6));
				String str = Integer.toString(getal);
				g.drawString(str, size.width / 2 - g.getFontMetrics().stringWidth(str) / 2, size.height / 2);
			}

			public void setGetal(int getal) {
				this.getal = getal;
				repaint();
			}
		};

		model = new DefaultTableModel();
		table = new JTable(model) {
			@Override
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		table.getTableHeader().setReorderingAllowed(false);

		// Headers
		String[] headers = new String[] {"Doosnr", "Producten", "Ordernr"};
		for (String header : headers) {
			model.addColumn(header);
		}

		/* TODO: VOEG HIER INGEPAKTE DOZEN DATA */
		model.addRow(new Object[] {"9452", "Product 1, Product 2, Product 3", "224466"});
		model.addRow(new Object[] {"9453", "Product 4", "446678"});

		TableColumnModel cmodel = table.getColumnModel();
		cmodel.getColumn(0).setPreferredWidth(20);
		cmodel.getColumn(1).setPreferredWidth(80);
		cmodel.getColumn(2).setPreferredWidth(20);

		tableScroller = new JScrollPane() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(150, 450);
			}
		};
		tableScroller.setViewportView(table);

		add(ingepakteDozen, gbc);
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridheight = 4;
		add(logo, gbc);
		gbc.gridx++;
		gbc.gridheight = 2;
		add(gekozenAlgoritme, gbc);
		gbc.gridy += 2;
		gbc.gridheight = 1;
		add(huidigInpakProces, gbc);
		gbc.gridy++;
		add(doos, gbc);
		gbc.gridx++;
		gbc.gridy = 1;
		gbc.gridheight = 4;
		add(tableScroller, gbc);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object src = ae.getSource();
		if (src instanceof JButton) {
			JButton srcBtn = (JButton)src;
			if (srcBtn == openAlgoritmeSelectie) {
				Selectie selectie = new Selectie(Selectie.Optie.DISPOSED);
				AlgoritmeSelectie as = new AlgoritmeSelectie(parent, "Selecteer een algoritme", true, selectie);
				System.out.println("Selectie: " + selectie.optie);
				// TODO: if logica baseerd op selectie
			}
		}
	}
}
