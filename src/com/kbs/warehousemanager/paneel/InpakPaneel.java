package com.kbs.warehousemanager.paneel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InpakPaneel extends JPanel implements ActionListener
{

	/**
	 * De inpak paneel
	 * Dit bevat de dozen met progress, en inhoud
	 */
	public InpakPaneel()
	{
		super.setBackground(Color.WHITE);
		super.setLayout(new GridLayout(2,1));


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
