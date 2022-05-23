package com.kbs.warehousemanager.paneel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ControlePaneel extends JPanel implements ActionListener {
	/**
	 * De controle paneel
	 * Dit bevat de knoppen om het programma te sturen
	 */
	//define the buttons
	private final JButton StartButton = new JButton("Start");
	private final JButton StopButton = new JButton("Stop");
	private final JButton RepeatButton = new JButton("Herhaal Pickronde");

	//define the comboBoxes and their respective options
	private String[] orderOptions = {"voorbeeldOrder 1", "voorbeeldOrder 2", "voorbeeldOrder 3"};
	private JComboBox<String> orderList = new JComboBox<>(orderOptions);
	private String[] tspOptions = {"Brute-force", "Branch-and-Bound", "Nearest Neighbour"};
	private JComboBox<String> tspList = new JComboBox<>(tspOptions);
	private String[] bppOptions = {"First Fit", "Next Fit", "Best Fit", "Worst Fit", "First-Fit-Decreasing", "Next-Fit-Decreasing", "Worst-Fit-Decreasing"};
	private JComboBox<String> bppList = new JComboBox<>(bppOptions);

	//define the labels for the currently selected algorithms, as well as the estimated boxes for the order
	private String bppSelected = bppList.getSelectedItem().toString();
	private String tspSelected = tspList.getSelectedItem().toString();
	int aantalDozen = 23;

	//define the different labels
	JLabel bppLabel = new JLabel("BPP-algoritme: " + bppSelected);
	JLabel boxesLabel = new JLabel("Aantal dozen: " + aantalDozen);
	JLabel tspLabel = new JLabel("TSP-algoritme: " + tspSelected);

	public ControlePaneel() {
		super.setBackground(Color.WHITE);
		super.setLayout(new GridLayout(9,1, 5, 5));
		super.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		StartButton.setBorder(new RoundBtn(15));
		StopButton.setBorder(new RoundBtn(15));
		RepeatButton.setBorder(new RoundBtn(15));

		StartButton.addActionListener(this);
		StopButton.addActionListener(this);
		RepeatButton.addActionListener(this);
		tspList.addActionListener(this);
		bppList.addActionListener(this);

		orderList.setFont(new Font("Rockwell", Font.PLAIN, 15));
		StartButton.setFont(new Font("Rockwell", Font.PLAIN, 15));
		StopButton.setFont(new Font("Rockwell", Font.PLAIN, 15));
		RepeatButton.setFont(new Font("Rockwell", Font.PLAIN, 15));
		bppLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		boxesLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		bppList.setFont(new Font("Rockwell", Font.PLAIN, 15));
		tspLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		tspList.setFont(new Font("Rockwell", Font.PLAIN, 15));


		String bppSelected = bppList.getSelectedItem().toString();
		String tspSelected = tspList.getSelectedItem().toString();



		add(orderList);
		add(StartButton);
		add(StopButton);
		add(RepeatButton);

		add(bppLabel);
		add(boxesLabel);

		add(bppList);
		add(tspLabel);
		add(tspList);
	}

	/**
	 * Verwerk een event
	 * @param ae Action event: de event dat binnenkomt
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object src = ae.getSource();
		// if it's a combobox, change the labels to see which algorithms are currently selected and update them
		if (src instanceof JComboBox) {
			bppSelected = bppList.getSelectedItem().toString();
			tspSelected = tspList.getSelectedItem().toString();

			bppLabel.setText("BPP-algoritme: " + bppSelected);
			tspLabel.setText("TSP-algoritme: " + tspSelected);
		}
		if (src instanceof JButton srcBtn) {
			if (srcBtn == StartButton) {
				//code voor de startbutton, buttons resetten en geselecteerde producten ophalen moeten gedaan worden
				//MagazijnPaneel.resetPanel();
				System.out.println(ItemList.getItems());
			}
		}

	}
}
