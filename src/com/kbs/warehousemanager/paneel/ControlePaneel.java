package com.kbs.warehousemanager.paneel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ControlePaneel extends JPanel implements ActionListener
{
	/**
	 * De controle paneel
	 * Dit bevat de knoppen om het programma te sturen
	 */
	public ControlePaneel()
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
