package com.kbs.warehousemanager.paneel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class OrderpickPaneel extends JPanel implements ActionListener
{
	/**
	 * De orderpick paneel
	 * Dit bevat de schappen, en de orderselectie
	 */
	public OrderpickPaneel()
	{

	}

	/**
	 * Verwerk een event
	 * @param ae Action event: de event dat binnenkomt
	 */
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object src = ae.getSource();
		if (src instanceof JButton)
		{
			JButton srcBtn = (JButton)src;
		}
	}
}
