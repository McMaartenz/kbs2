package com.kbs.warehousemanager;

import com.kbs.warehousemanager.paneel.HoofdPaneel;
import com.kbs.warehousemanager.serial.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
				String response = serialManager.sendPacket("ping\n", Robot.INPAK_ROBOT);

				System.out.println("The response to ping is: " + response);
			}
		}).start();

		HoofdPaneel hoofdPaneel = new HoofdPaneel();
	}
}
