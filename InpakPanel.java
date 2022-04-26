import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class InpakPanel extends JPanel implements ActionListener {

	private Database db;
	private JFrame parent;

	private JLabel ingepakteDozen;
	private JLabel gekozenAlgoritme;
	private JLabel huidigInpakProces;

	private JPanel logo;
	private JPanel doos;

	private JScrollPane tabelScroller;

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

		ingepakteDozen = new JLabel("Ingepakte dozen");
		gekozenAlgoritme = new JLabel("<html>Gekozen algoritme<br>ALGORITME</html>");
		huidigInpakProces = new JLabel("Huidig inpakproces");

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

		};

		tabelScroller = new JScrollPane(new JTable()); // TODO: maak tabel

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
		add(tabelScroller, gbc);

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
