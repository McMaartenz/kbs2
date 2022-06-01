package com.kbs.warehousemanager.paneel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class HoofdPaneel extends JFrame
{
	private final OrderpickPaneel orderpickPaneel;
	private final InpakPaneel inpakPaneel;
	private final ControlePaneel controlePaneel;

	/**
	 * De hoofd paneel
	 * Dit bevat de drie andere panelen
	 * @see OrderpickPaneel
	 * @see InpakPaneel
	 * @see ControlePaneel
	 */

	public HoofdPaneel() {
		setLayout(new GridLayout(1, 3));
		orderpickPaneel = new OrderpickPaneel();
		inpakPaneel = new InpakPaneel();
		controlePaneel = new ControlePaneel();

		add(controlePaneel);
		add(orderpickPaneel);
		add(inpakPaneel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1280, 720);
		setVisible(true);
	}
}
