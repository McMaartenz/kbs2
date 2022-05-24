package com.kbs.warehousemanager.paneel;

import javax.swing.*;
import java.awt.*;

public class HoofdPaneel extends JFrame
{
	private OrderpickPaneel orderpickPaneel;
	private InpakPaneel inpakPaneel;
	private ControlePaneel controlePaneel;

	/**
	 * De hoofd paneel
	 * Dit bevat de drie andere panelen
	 * @see OrderpickPaneel
	 * @see InpakPaneel
	 * @see ControlePaneel
	 */
	public HoofdPaneel()
	{
		setLayout(new GridLayout(1, 3));
		orderpickPaneel = new OrderpickPaneel();
		inpakPaneel = new InpakPaneel();
		controlePaneel = new ControlePaneel();

		add(orderpickPaneel);
		add(inpakPaneel);
		add(controlePaneel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1280, 720);
		setVisible(true);
	}
}
