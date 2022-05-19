package com.kbs.warehousemanager.paneel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class OrderpickPaneel extends JPanel {

	private MagazijnPaneel Magazijn = new MagazijnPaneel();
	private Orderpicker Orderpick = new Orderpicker();

	public OrderpickPaneel() {
		super.setLayout(new GridLayout(2, 1));
		super.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(Magazijn);
		add(Orderpick);
	}
}




		/**
		 * Verwerk een event
		 *
		 * @param ae Action event: de event dat binnenkomt
		 */


