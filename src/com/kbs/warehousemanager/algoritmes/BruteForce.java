package com.kbs.warehousemanager.algoritmes;

import java.awt.*;
import java.lang.annotation.Repeatable;
import java.util.ArrayList;

public class BruteForce
{
	private int[] bestPathFoundIndices;
	private int bestPathLength;

	private Point[] orderList;
	private Node[] possibleBranches;
	private int depth;

	/**
	 * <b>Testing</b> function
	 * @param args
	 */
	public static void main(String[] args)
	{
		Order order = new Order();
		order.populate();
		order.printOrderList();

		BruteForce bruteForce = new BruteForce();
		bruteForce.generateTreeFrom(order);
		Point[] bestPath = bruteForce.findBestPath();
	}

	/**
	 * Generate the binary tree from an order, <b>REQUIRED</b> to run before using this algorithm
	 * @param order order object
	 */
	public void generateTreeFrom(Order order)
	{
		orderList = order.getOrderList().toArray(Point[]::new);

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
	 * Get the tree
	 * @return tree
	 */
	public Node[] getTree()
	{
		return possibleBranches;
	}

	/**
	 * Iterate over every possible path
	 * @param indices indices to fill up
	 * @param depth current depth in searching
	 * @param tree tree to read from
	 */
	public void pathIterator(int[] indices, int depth, Node tree)
	{
		for(indices[depth] = 0; indices[depth] < tree.getNode(indices[depth]).getNodeCount(); indices[depth]++)
		{
			if (depth == (indices.length - 1)) // Last node
			{
				int length = getPathLengthFor(indices);
				if (length < bestPathLength)
				{
					bestPathLength = length;
					bestPathFoundIndices = indices;
				}
			}
			else
			{
				pathIterator(indices, depth + 1, tree.getNode(indices[depth]));
			}
		}
	}

	/**
	 * Give path length for given indices
	 * @param indices indices for tree
	 * @return path length
	 */
	public int getPathLengthFor(int[] indices)
	{
		int distance = 0;
		Point previousPoint = null;
		for(int i = 0; i < indices.length; i++)
		{
			int index = indices[i];
			Point currentPoint = orderList[index];

			if (previousPoint == null)
			{
				previousPoint = currentPoint;
			}
			distance += Math.abs(previousPoint.getX() - currentPoint.getX());
			distance += Math.abs(previousPoint.getY() - currentPoint.getY());
		}

		return distance;
	}

	/**
	 * Create point array for given indices
	 * @param indices indices for tree
	 * @return point array
	 */
	public Point[] toPointArray(int[] indices)
	{
		Point[] result = new Point[indices.length];

		for(int i = 0; i < result.length; i++)
		{
			result[i] = orderList[i];
		}

		return result;
	}

	/**
	 * Find the best path (Perform brute-force algorithm);
	 * @return The best path
	 */
	public Point[] findBestPath()
	{
		bestPathLength = 0;
		bestPathFoundIndices = new int[] {};

		int[] indices = new int[depth];

		Node base = new Node();
		for(int i = 0; i < possibleBranches.length; i++)
		{
			base.addNode(possibleBranches[i]);
		}

		pathIterator(indices, 0, base);

		Point[] result = toPointArray(indices);

		return result;
	}
}
