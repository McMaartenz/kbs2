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
			Point[] points = new Point[5];
			for (int i = 0; i < 5; i++)
			{
				points[i] = new Point(i, i);
			}

			boolean ok = serialManager.performPath(points);
			System.out.println("Perform path successful? " + ok);
		}).start();

		HoofdPaneel hoofdPaneel = new HoofdPaneel();
		SerialManager.close(serialManager);
	}
}
