package com.kbs.warehousemanager.paneel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class OrderpickPaneel extends JPanel implements ActionListener
{
	JPanel magazijnPaneel = new MagazijnPaneel();
	JPanel orderpicker = new Orderpicker();

	public OrderpickPaneel()
	{
		super.setLayout(new GridLayout(2, 1));
		super.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(magazijnPaneel);
		add(orderpicker);
	}

	public class MagazijnPaneel extends JPanel {
		public MagazijnPaneel() {
			setLayout(new GridLayout(5, 5));
			File file = new File("images");
			for (String name : file.list()) {
				JLabel label = new JLabel();

				label.setIcon(new ImageIcon(
						new ImageIcon("images/" + name).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));
				label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				add(label);
			}
		}
	}

	public class Orderpicker extends JPanel implements ActionListener {
		public Orderpicker(){
			String[] orderOptions = {"voorbeeldOrder 1", "voorbeeldOrder 2", "voorbeeldOrder 3"};
			JComboBox orderList = new JComboBox(orderOptions);
			orderList.addActionListener(this);
			add(orderList);
		}

		@Override
		public void actionPerformed(ActionEvent e) {

		}
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
