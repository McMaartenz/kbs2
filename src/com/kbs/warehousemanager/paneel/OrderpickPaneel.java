package com.kbs.warehousemanager.paneel;

import java.awt.*;
import javax.swing.*;

public class OrderpickPaneel extends JPanel {

	private ItemList itemList = new ItemList();
	private MagazijnPaneel Magazijn = new MagazijnPaneel(itemList);

	public OrderpickPaneel() {
		super.setLayout(new GridLayout(2, 1));
		super.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(Magazijn);
		add(itemList);
	}
}




		/**
		 * Verwerk een event
		 *
		 * @param ae Action event: de event dat binnenkomt
		 */


