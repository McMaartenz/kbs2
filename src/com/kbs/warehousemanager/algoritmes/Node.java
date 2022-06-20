package com.kbs.warehousemanager.algoritmes;

import java.util.ArrayList;

public class Node
{
	private int value;
	private ArrayList<Node> nodes;

	public Node()
	{
		this(0);
	}

	public Node(int value)
	{
		this.value = value;
		nodes = new ArrayList<>();
	}

	/**
	 * Add a node to the tree
	 * @param node The node to add
	 */
	public void addNode(Node node)
	{
		nodes.add(node);
	}

	/**
	 * Get node at index
	 * @param index index of node
	 * @return the node at that index
	 * @throws IndexOutOfBoundsException when out of bounds
	 */
	public Node getNode(int index) throws IndexOutOfBoundsException
	{
		return nodes.get(index);
	}

	public int getNodeCount()
	{
		return nodes.size();
	}

	public int getValue()
	{
		return value;
	}

	/**
	 * Print the awesome tree!
	 * @param iteration Depth inside the tree (Increases each layer, starts from 0)
	 */
	public void print(int iteration)
	{
		System.out.println(repeatStr('`', iteration) + value);
		for (Node node: nodes)
		{
			node.print(iteration + 1);
		}
	}

	/**
	 * Create a string with a character repeated a certain amount
	 * @param c Char to repeat
	 * @param count Times to repeat
	 * @return Generated string
	 */
	public static String repeatStr(char c, int count)
	{
		return new String(new char[count]).replace('\0', c);
	}
}
