package com.kbs.warehousemanager.algoritmes;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Order
{
	private ArrayList<Point> orderList;
	private ArrayList<Integer> producten;

	/**
	 * New order
	 */
	public Order()
	{
		orderList = new ArrayList<>();
	}

	ThreadLocalRandom lclRnd = ThreadLocalRandom.current();
	int pointCount = lclRnd.nextInt(3, 26);
	/**
	 * Clear order list and populate with random values (Testing purposes)
	 */

	public void populate()
	{

		orderList.clear();
		for (int i = 0; i < pointCount; i++)
		{
			int x, y;
			x = lclRnd.nextInt(1, 6);
			y = lclRnd.nextInt(1, 6);

			Point point = new Point(x, y);
			orderList.add(point);
		}

	}

	public void InpakOrder(){
		for(int i = 0; i < pointCount; i++){
			int product = lclRnd.nextInt(1, 11);
			producten.add(product);
			System.out.println(product);
		}
	}

	/**
	 * Print order list
	 */
	public void printOrderList()
	{
		System.out.println("Order list\n[");
		for (int id = 0; id < orderList.size(); id++)
		{
			Point point = orderList.get(id);
			System.out.format("\t%d = (%d, %d)\n", id, point.x, point.y);
		}
		System.out.println("]");
	}

	/**
	 * Get order list
	 * @return order list
	 */
	public ArrayList<Point> getOrderList()
	{
		return orderList;
	}
}
