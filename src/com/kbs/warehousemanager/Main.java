package com.kbs.warehousemanager;

import com.kbs.warehousemanager.paneel.HoofdPaneel;
import com.kbs.warehousemanager.serial.*;

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
			Serial orderpickRobot = serialManager.getRobot(Robot.ORDERPICK_ROBOT);
			Serial inpakRobot = serialManager.getRobot(Robot.INPAK_ROBOT);

			// Example
			if (orderpickRobot.good())
			{
				orderpickRobot.send("hello\n");
				orderpickRobot.send("status\n");
			}

			if (inpakRobot.good())
			{
				inpakRobot.send("hello\n");
				inpakRobot.send("status\n");
			}
		}).start();

		HoofdPaneel hoofdPaneel = new HoofdPaneel();
	}
}
