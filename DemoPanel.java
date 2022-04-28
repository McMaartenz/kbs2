import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

public class DemoPanel extends JPanel implements ActionListener
{
	private JFrame parent;
	private Robot robot;

	private JSlider potmeterSlider;

	public DemoPanel(Display parent)
	{
		this.parent = parent;
		robot = parent.getRobot(1);

		setLayout(new GridLayout(3, 1));

		potmeterSlider = new JSlider(0, 100);

		add(potmeterSlider);

		Thread potmeterThread = new Thread(this::potmeterThread);
		potmeterThread.start();
	}

	public void potmeterThread()
	{
		Display display = (Display) parent;
		while (true)
		{
			robot = display.getRobot(1);
			if (robot != null)
			{
				if (robot.serial != null && robot.ok)
				{
					break;
				}
			}
			robot = display.getRobot(2);
			if (robot != null)
			{
				if (robot.serial != null && robot.ok)
				{
					break;
				}
			}
			Thread.onSpinWait();
		}

		System.out.println("Connected");

		while (true)
		{
			String in;


			robot.serial.serialWrite("pot\n");
			System.out.println("Stuur POT request");
			in = robot.serial.serialRead();
			System.out.println("Terug: " + in);
			String[] inlijst = in.split(",");

			try
			{
				SwingUtilities.invokeAndWait(() ->
				{
					boolean set = false;
					int i = 0;
					while (!set)
					{
						if (i >= inlijst.length)
						{
							break;
						}
						try
						{
							//potmeterSlider.setValue(Integer.parseInt(inlijst[i++]));
							System.out.println(potmeterSlider.getValue());
							set = true;
						}
						catch (NumberFormatException ne)
						{
							ne.printStackTrace();
						}
					}
				});
			}
			catch (InvocationTargetException | InterruptedException ie)
			{
				System.out.println("potmeterThread: ");
				ie.printStackTrace();
			}

			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException ie)
			{
				System.out.println("potmeterThread: ");
				ie.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object src = ae.getSource();
		if (src instanceof JButton)
		{
			JButton srcBtn = (JButton) src;
			if (srcBtn == null)
			{

			}
		}
	}
}
