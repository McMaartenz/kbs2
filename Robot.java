import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.ConnectException;

import arduino.Arduino;

public class Robot
{
	private Point positie;
	public Arduino serial;
	public int robotNummer;
	public boolean ok = false;
	public static final BufferedImage plaatje = Plaatje.laad("img/arduino.png");

	public Robot(int robotNummer)
	{
		serial = null;
		positie = new Point(0, 0);
		this.robotNummer = robotNummer;
	}

	public Robot(Port port, int robotNummer) throws ConnectException
	{
		this(robotNummer);
		initCom(port);
	}

	public void verplaatsNaar(Point positie)
	{
		this.positie = positie;
	}

	public void initCom(Port port) throws ConnectException
	{
		serial = new Arduino(String.valueOf(port));
		boolean ok = serial.openConnection();
		this.ok = ok;
		if (!ok)
		{
			throw new ConnectException("Robot " + robotNummer + ": Cannot connect to port " + port + ": not available");
		}
		System.out.println("Robot " + robotNummer + ": Opened serial connection to port " + port);
	}

	public Point getPositie()
	{
		return positie;
	}
}
