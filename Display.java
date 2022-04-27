import arduino.Arduino;

import javax.swing.*;
import java.awt.*;
import java.net.ConnectException;

class Display extends JFrame
{

	private Database db;

	private JTabbedPane keuzeMenu;
	private JPanel orderPanel;
	private JPanel klantPanel;
	private JPanel inpakPanel;

	private Robot robot1;
	private Robot robot2;

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

		setSize(520, 480);
		setLayout(new GridLayout(1, 1));

		orderPanel = new OrderPanel(this);
		klantPanel = new KlantPanel(this);
		inpakPanel = new InpakPanel(this);

		keuzeMenu = new JTabbedPane();
		keuzeMenu.addTab("Order", orderPanel);
		keuzeMenu.addTab("Klant", klantPanel);
		keuzeMenu.addTab("Inpak", inpakPanel);

		add(keuzeMenu);

		// TODO: selecteer een port als connectie faalt met mogelijkheden: OK/Cancel

		new Thread(()->
		{
			try
			{
				robot1 = new Robot(Port.COM3);
			}
			catch (ConnectException ce)
			{
				robot1 = new Robot();
				JOptionPane.showMessageDialog(this, ce, "Port connectie fout", JOptionPane.ERROR_MESSAGE);
			}
		}).start();

		new Thread(() ->
		{
			try
			{
				robot2 = new Robot(Port.COM4);
			}
			catch (ConnectException ce)
			{
				robot2 = new Robot();
				JOptionPane.showMessageDialog(this, ce, "Port connectie fout", JOptionPane.ERROR_MESSAGE);
			}
		}).start();

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

	public Robot getRobot(int n)
	{
		if (n == 1)
		{
			return robot1;
		}
		else if (n == 2)
		{
			return robot2;
		}
		else
		{
			return null;
		}
	}
}
