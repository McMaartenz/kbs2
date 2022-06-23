package com.kbs.warehousemanager;

import com.kbs.warehousemanager.algoritmes.NearestNeighbour;
import com.kbs.warehousemanager.algoritmes.Order;
import com.kbs.warehousemanager.paneel.HoofdPaneel;
import com.kbs.warehousemanager.serial.*;
import com.kbs.warehousemanager.serial.Robot;
import com.kbs.warehousemanager.paneel.DozenTabel;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.kbs.warehousemanager.paneel.ControlePaneel.COM_INPAK;
import static com.kbs.warehousemanager.paneel.DozenTabel.dozenArray;

public class Main
{
	public static SerialManager serialManager;

	public static String tostring(ArrayList<Integer> dozenArray){
		StringBuilder sb = new StringBuilder();
		for(Integer i: dozenArray){
			sb.append(i);
		}

		return sb.toString();
	}

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
//			serialManager = new SerialManager(Robot.ORDERPICK_ROBOT);

			// Example
//			Order order1 = new Order();
//			order1.populate();
//			NearestNeighbour nb1 = new NearestNeighbour();
//			Point[] points = nb1.generatePath(order1.getOrderList()).toArray(Point[]::new);
//
////			Point[] points = new Point[5];
////			for (int i = 0; i < 5; i++)
////			{
////				points[i] = new Point(i, i);
////			}
//
//			boolean ok = serialManager.performPath(points);
//			System.out.println("Perform path successful? " + ok);

			//COM_INPAK();

		}).start();

		HoofdPaneel hoofdPaneel = new HoofdPaneel();


		SerialManager.close(serialManager);
	}
}
