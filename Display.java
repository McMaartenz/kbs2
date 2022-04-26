import javax.swing.*;
import java.awt.*;

class Display extends JFrame
{

	private Database db;

	/* Main panel */
	private JTabbedPane keuzeMenu;
	private JPanel orderPanel;
	private JPanel klantPanel;
	private JPanel inpakPanel;

	public Display(Database db)
	{
		try
		{ // Set system window appearance
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		this.db = db;

		setSize(640, 480);
		setLayout(new GridLayout(1, 1));

		orderPanel = new OrderPanel(this);
		klantPanel = new KlantPanel(this);
		inpakPanel = new InpakPanel(this);

		keuzeMenu = new JTabbedPane();
		keuzeMenu.addTab("Order", orderPanel);
		keuzeMenu.addTab("Klant", klantPanel);
		keuzeMenu.addTab("Inpak", inpakPanel);

		add(keuzeMenu);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public Database getDB()
	{
		return db;
	}

	public void switchTab(Tab newTab)
	{
		keuzeMenu.setSelectedIndex(newTab.ordinal());
	}
}
