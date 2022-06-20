package com.kbs.warehousemanager.algoritmes;

import java.awt.*;

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
		try
		{
			bruteForce.generateTreeFrom(order);
		}
		catch (TooDeepException te)
		{
			System.out.println("Max iteration depth exceeded for brute-force algorithm");
			return;
		}

		Point[] bestPath = bruteForce.findBestPath();

		System.out.println("Distance: " + bruteForce.bestPathLength);
		System.out.println("Best path:\n[");
		for (int i = 0; i < bestPath.length; i++)
		{
			Point p = bestPath[i];
			System.out.format("\t%d = (%d, %d)\n", i, p.x, p.y);
		}
		System.out.println("]");
	}

	/**
	 * Generate the binary tree from an order, <b>REQUIRED</b> to run before using this algorithm
	 * @param order order object
	 */
	public void generateTreeFrom(Order order) throws TooDeepException
	{
		orderList = order.getOrderList().toArray(Point[]::new);

		int pointCount = orderList.length;

		if (pointCount >= 11)
		{
			throw new TooDeepException();
		}

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
		int nodeCount = tree.getNode(indices[depth]).getNodeCount();

		if (nodeCount == 0)
		{
			int length = getPathLengthFor(indices);
			if (length < bestPathLength)
			{
				bestPathLength = length;

				// Do not copy pointer
				System.arraycopy(indices, 0, bestPathFoundIndices, 0, depth);
			}

			return;
		}

		for(indices[depth] = 0; indices[depth] < nodeCount; indices[depth]++)
		{
			pathIterator(indices, depth + 1, tree.getNode(indices[depth]));
		}
	}

	/**
	 * Give path length for given indices
	 * @param indices indices for tree
	 * @return path length
	 */
	public int getPathLengthFor(int[] indices)
	{
		Node tree = new Node();
		for (Node node : possibleBranches)
		{
			tree.addNode(node);
		}

		int distance = 0;
		Point previousPoint = null;
		for (int j : indices)
		{
			tree = tree.getNode(j);
			int index = tree.getValue();

			Point currentPoint = orderList[index];

			if (previousPoint == null)
			{
				previousPoint = currentPoint;
			}
			distance += Math.abs(previousPoint.getX() - currentPoint.getX());
			distance += Math.abs(previousPoint.getY() - currentPoint.getY());
			previousPoint = currentPoint;
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

		Node tree = new Node();
		for (Node node : possibleBranches)
		{
			tree.addNode(node);
		}

		for (int i = 0; i < result.length; i++)
		{
			tree = tree.getNode(indices[i]);
			result[i] = orderList[tree.getValue()];
		}

		return result;
	}

	/**
	 * Find the best path (Perform brute-force algorithm);
	 * @return The best path
	 */
	public Point[] findBestPath()
	{
		bestPathLength = Integer.MAX_VALUE;
		bestPathFoundIndices = new int[depth];

		int[] indices = new int[depth];

		Node base = new Node();
		for (Node possibleBranch : possibleBranches)
		{
			base.addNode(possibleBranch);
		}

		pathIterator(indices, 0, base);

		return toPointArray(bestPathFoundIndices);
	}
}
