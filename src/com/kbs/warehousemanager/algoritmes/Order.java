package com.kbs.warehousemanager.algoritmes;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Order
{
	private ArrayList<Point> orderList;

	/**
	 * New order
	 */
	public Order()
	{
		orderList = new ArrayList<>();
	}

	/**
	 * Clear order list and populate with random values (Testing purposes)
	 */
	public void populate()
	{
		ThreadLocalRandom lclRnd = ThreadLocalRandom.current();

		orderList.clear();

		int pointCount = lclRnd.nextInt(3, 26);
		for (int i = 0; i < pointCount; i++)
		{
			int x, y;
			x = lclRnd.nextInt(1, 6);
			y = lclRnd.nextInt(1, 6);

			Point point = new Point(x, y);
			orderList.add(point);
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
