package com.kbs.warehousemanager.paneel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ControlePaneel extends JPanel implements ActionListener {
	/**
	 * De controle paneel
	 * Dit bevat de knoppen om het programma te sturen
	 */
	private final JButton StartButton = new JButton("Start");
	private final JButton StopButton = new JButton("Stop");
	private final JButton RepeatButton = new JButton("Herhaal Pickronde");



	public ControlePaneel() {
		super.setLayout(new GridLayout(1, 1));
		super.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		final JLabel bppLabel = new JLabel("BPP-algoritme:");
		final JLabel boxesLabel = new JLabel("Aantal dozen: ");
		final JLabel tspLabel = new JLabel("TSP-algoritme");

		add(StartButton);
		add(StopButton);
		add(RepeatButton);
		add(bppLabel);
		add(boxesLabel);

		String[] tspOptions = {"Brute-force", "Branch-and-Bound", "Nearest Neighbour"};
		JComboBox tspList = new JComboBox(tspOptions);
		tspList.addActionListener(this);
		add(tspList);

		add(tspLabel);
		String[] bppOptions = {"First Fit", "Next Fit", "Best Fit", "Worst Fit", "First-Fit-Decreasing", "Next-Fit-Decreasing", "Worst-Fit-Decreasing"};
		JComboBox bppList = new JComboBox(bppOptions);
		bppList.addActionListener(this);
		add(bppList);
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
