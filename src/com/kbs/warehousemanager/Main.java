package com.kbs.warehousemanager;

import com.kbs.warehousemanager.paneel.HoofdPaneel;
import com.kbs.warehousemanager.serial.Robot;
import com.kbs.warehousemanager.serial.SerialManager;

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
		new Thread(() -> serialManager = new SerialManager(Robot.ORDERPICK_ROBOT)).start();

		HoofdPaneel hoofdPaneel = new HoofdPaneel();
	}
}
