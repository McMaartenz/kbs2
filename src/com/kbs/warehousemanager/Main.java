package com.kbs.warehousemanager;

import com.kbs.warehousemanager.paneel.HoofdPaneel;
import com.kbs.warehousemanager.serial.*;
import com.kbs.warehousemanager.serial.Robot;

import java.awt.*;

public class Main
{
	static SerialManager serialManager;

	/**
	 * Start programma
	 * @param args Programma argumenten
	 */
	public static void main(String[] args)
	{
		// Do not block AWT GUI thread
		new Thread(() ->
		{
			Thread.currentThread().setName("Serial Initialiser Thread");
			serialManager = new SerialManager(Robot.ORDERPICK_ROBOT);

			// Example
			if (serialManager.good(Robot.INPAK_ROBOT))
			{
				String response = serialManager.sendPacket("ping1\n", Robot.INPAK_ROBOT, true);

				System.out.println(System.currentTimeMillis() + "The response to ping1 is: " + response);

				response = serialManager.sendPacket("ping2\n", Robot.INPAK_ROBOT, true);

				System.out.println(System.currentTimeMillis() + "The response to ping2 is: " + response);

				response = serialManager.sendPacket("ping3\n", Robot.INPAK_ROBOT, true);

				System.out.println(System.currentTimeMillis() + "The response to ping3 is: " + response);

				Point[] points = new Point[5];
				for (int i = 0; i < 5; i++)
				{
					points[i] = new Point(i, i);
				}

				response = serialManager.sendPointsPacket(points, Robot.INPAK_ROBOT, true);
				System.out.println(System.currentTimeMillis() + "Response: " + response);
			}
		}).start();

		HoofdPaneel hoofdPaneel = new HoofdPaneel();
		SerialManager.close(serialManager);
	}
}
