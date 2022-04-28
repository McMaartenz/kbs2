import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

public class DemoPanel extends JPanel implements ActionListener
{
	private JFrame parent;
	private Robot robot;

	private boolean statusLED;
	private Queue<String> requestQueue;
	private String[] serialIn;

	private JButton roodToggle;
	private JSlider potmeterSlider;

	public DemoPanel(Display parent)
	{
		this.parent = parent;
		robot = parent.getRobot(1);

		setLayout(new GridLayout(3, 1));

		potmeterSlider = new JSlider(0, 100);

		statusLED = true;
		roodToggle = new JButton("Zet LED uit");
		roodToggle.addActionListener(this);

		requestQueue = new LinkedList<>();
		serialIn = new String[5];

		add(potmeterSlider);
		add(roodToggle);

		Thread requestQueueThread = new Thread(this::requestQueueHandler);
		requestQueueThread.start();
		Thread potmeterThread = new Thread(this::potmeterThread);
		potmeterThread.start();
	}

	private void wachtOpSerial()
	{
		Display display = (Display) parent;
		while (true)
		{
			robot = display.getRobot(1);
			if (robot != null)
			{
				if (robot.serial != null && robot.ok)
				{
					System.out.println("Robot 1");
					break;
				}
			}
			robot = display.getRobot(2);
			if (robot != null)
			{
				if (robot.serial != null && robot.ok)
				{
					System.out.println("Robot 2");
					break;
				}
			}
			Thread.onSpinWait();
		}
	}

	private void requestQueueHandler()
	{
		wachtOpSerial();
		while (true)
		{
			if (!requestQueue.isEmpty())
			{
				String totaalrequest = "";
				for (int i = 0; i < 5; i++)
				{
					if (requestQueue.isEmpty())
					{
						break;
					}
					String request = requestQueue.poll();
					System.out.println("Stuur " + request + " request (Event queue size: " + requestQueue.size() + ")");
					totaalrequest += request + ",";
				}
				robot.serial.serialWrite(totaalrequest);
			}
			for(int i = 0; i < 5; i++)
			{
				serialIn[i] = robot.serial.serialRead();
				if (serialIn[i].length() > 0)
				{
					System.out.println("Get van arduino: " + serialIn[i]);
				}
			}
		}
	}

	private void wacht(int millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch (InterruptedException ie)
		{
			System.out.println("Interrupted: " + ie);
		}
	}

	private void toggleLED()
	{
		statusLED = !statusLED;

		if (statusLED)
		{
			roodToggle.setText("Zet LED uit");
			requestQueue.add("roodaan");
		}
		else
		{
			roodToggle.setText("Zet LED aan");
			requestQueue.add("rooduit");
		}
	}

	public static int totalLength(String[] arr)
	{
		if (arr == null)
		{
			return 0;
		}

		int totaal = 0;
		for (int i = 0; i < arr.length; i++)
		{
			if (arr[i] == null)
			{
				continue;
			}
			totaal += arr[i].length();
		}
		return totaal;
	}

	public void potmeterThread()
	{
		wachtOpSerial();
		System.out.println("Connected");

		while (true)
		{
			requestQueue.add("pot");

			int tries = 0;
			while (totalLength(serialIn) == 0 && tries++ <= 10)
			{
				Thread.onSpinWait();
			}

			String[][] responseLijst = new String[serialIn.length][];
			for (int i = 0; i < serialIn.length; i++)
			{
				if (serialIn[i] == null)
				{
					responseLijst[i] = new String[0];
					continue;
				}
				responseLijst[i] = serialIn[i].split(",");
			}

			try
			{
				SwingUtilities.invokeAndWait(() ->
				{
					for (String[] strings : responseLijst)
					{
						boolean set = false;
						int j = 0;
						while (!set)
						{
							if (j >= strings.length)
							{
								break;
							}
							try
							{
								potmeterSlider.setValue(Integer.parseInt(strings[j++]));
								set = true;
							}
							catch (NumberFormatException ne)
							{
							}
						}
					}
				});
			}
			catch (InvocationTargetException | InterruptedException ie)
			{
				System.out.println("potmeterThread: ");
				ie.printStackTrace();
			}
			wacht(750);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object src = ae.getSource();
		if (src instanceof JButton)
		{
			JButton srcBtn = (JButton) src;
			if (srcBtn == roodToggle)
			{
				toggleLED();
			}
		}
	}
}
