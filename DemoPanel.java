import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

public class DemoPanel extends JPanel implements ActionListener
{
	private JFrame parent;
	private Robot robot1;

	private JSlider potmeterSlider;

	public DemoPanel(Display parent)
	{
		this.parent = parent;
		robot1 = parent.getRobot(1);

		setLayout(new GridLayout(3, 1));

		potmeterSlider = new JSlider(0, 100);

		add(potmeterSlider);

		Thread potmeterThread = new Thread(this::potmeterThread);
		potmeterThread.start();
	}

	public void potmeterThread()
	{
		while (true)
		{
			String in;
			if (robot1 == null)
			{
				robot1 = ((Display)parent).getRobot(1);
				if (robot1 == null)
				{
					Thread.onSpinWait();
					continue;
				}
			}

			in = robot1.serial.serialRead();
			String[] inlijst = in.split("\n");

			try
			{
				SwingUtilities.invokeAndWait(() ->
				{
					boolean set = false;
					int i = 0;
					while (!set)
					{
						try
						{
							potmeterSlider.setValue(Integer.parseInt(inlijst[i++]));
							System.out.println(potmeterSlider.getValue());
							set = true;
						}
						catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
						{
							if (e instanceof ArrayIndexOutOfBoundsException)
							{
								set = true;
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
