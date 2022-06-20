package com.kbs.warehousemanager.algoritmes;

import java.awt.*;
import java.lang.annotation.Repeatable;
import java.util.ArrayList;

public class BruteForce
{
	Node[] possibleBranches;
	int depth;

	/**
	 * Generate the binary tree from an order, <b>REQUIRED</b> to run before using this algorithm
	 * @param order order object
	 */
	public void generateTreeFrom(Order order)
	{
		Point[] orderList = order.getOrderList().toArray(Point[]::new);

		int pointCount = orderList.length;

		possibleBranches = new Node[pointCount];
		depth = pointCount;
		for (int i = 0; i < pointCount; i++)
		{
			possibleBranches[i] = fillUp(i, without(orderList, i));
		}
	}

	/**
	 * Remove index from point and put it into a new array
	 * @param orderList Input array
	 * @param index Index to remove
	 * @return New array without removed item
	 */
	private Point[] without(Point[] orderList, int index)
	{
		if (orderList.length <= 0)
		{
			return new Point[0];
		}

		Point[] result = new Point[orderList.length];
		System.arraycopy(orderList, 0, result, 0, orderList.length);

		result[index] = null;

		return result;
	}

	/**
	 * Fill tree
	 * @param currentN Current node value
	 * @param orderList Remaining points to add
	 * @return Filled up tree
	 */
	private Node fillUp(int currentN, Point[] orderList)
	{
		Node tree = new Node(currentN);

		for (int i = 0; i < orderList.length; i++)
		{
			if (orderList[i] == null)
			{
				continue; // It doesn't exist. Trust me.
			}

			tree.addNode(fillUp(i, without(orderList, i)));
		}

		return tree;
	}

	/**
	 * Print the tree!
	 */
	public void printPossibleBranches()
	{
		for (Node tree: possibleBranches)
		{
			tree.print(0);
		}
	}

	/**
	 * Find the best path (Perform brute-force algorithm);
	 * @return The best path
	 */
	public Point[] findBestPath()
	{
		int[] indices = new int[depth];

		// TODO: Implement algorithm

		return new Point[0];
	}
}
