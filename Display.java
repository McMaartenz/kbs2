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

		Port.updatePorten();

		new Thread(()->
		{
			Port gewenstePort;
			try
			{
				gewenstePort = new Port(Port.porten[0]);
			}
			catch (ArrayIndexOutOfBoundsException ae)
			{
				gewenstePort = new Port(-1);
			}

			boolean connected = false;
			while (!connected)
			{
				try
				{
					robot1 = new Robot(gewenstePort, 1);
					connected = true;
				}
				catch (ConnectException ce)
				{
					gewenstePort = PortSelector.keuze(this, gewenstePort, 1);
				}

				if (gewenstePort == null)
				{
					robot1 = new Robot(1);
					break;
				}
			}
		}).start();

		new Thread(()->
		{
			Port gewenstePort;
			try
			{
				gewenstePort = new Port(Port.porten[0]);
			}
			catch (ArrayIndexOutOfBoundsException ae)
			{
				gewenstePort = new Port(-1);
			}

			boolean connected = false;
			while (!connected)
			{
				try
				{
					robot2 = new Robot(gewenstePort, 2);
					connected = true;
				}
				catch (ConnectException ce)
				{
					gewenstePort = PortSelector.keuze(this, gewenstePort, 2);
				}

				if (gewenstePort == null)
				{
					robot2 = new Robot(2);
					break;
				}
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

	public Runnable setAlgoritme(Selectie selectie, Object orderNummer)
	{
		return () -> ((InpakPanel)inpakPanel).setAlgoritme(selectie, orderNummer);
	}
}
