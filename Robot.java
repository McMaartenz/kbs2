import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.ConnectException;

import arduino.Arduino;

public class Robot
{
	private Point positie;
	public Arduino serial;
	public static final BufferedImage plaatje = Plaatje.laad("img/arduino.png");

	public Robot()
	{
		serial = null;
		positie = new Point(0, 0);
	}

	public Robot(Port port) throws ConnectException
	{
		this();
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
		if (!ok)
		{
			throw new ConnectException("Cannot connect to port " + port + ": not available");
		}
		System.out.println("Opened serial connection to port " + port);
	}

	public Point getPositie()
	{
		return positie;
	}
}
