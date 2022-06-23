package com.kbs.warehousemanager.paneel;

import com.kbs.warehousemanager.algoritmes.FirstFitAlgoritme;

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
	static ArrayList<String> orderOptions = new ArrayList<>();
	private final JComboBox<String> orderList = new JComboBox();
	private final String[] tspOptions = {"Brute-force", "Branch-and-Bound", "Nearest Neighbour"};
	private final JComboBox<String> tspList = new JComboBox<>(tspOptions);
	private final String[] bppOptions = {"First Fit", "Next Fit", "Best Fit", "Worst Fit", "First-Fit-Decreasing", "Next-Fit-Decreasing", "Worst-Fit-Decreasing"};
	private final JComboBox<String> bppList = new JComboBox<>(bppOptions);

	//define the labels for the currently selected algorithms, as well as the estimated boxes for the order
	private String bppSelected = bppList.getSelectedItem().toString();
	private String tspSelected = tspList.getSelectedItem().toString();
	private String selectedOrder;
	int aantalDozen = 23;

	//define the different labels
	JLabel bppLabel = new JLabel("BPP-algoritme: " + bppSelected);
	JLabel boxesLabel = new JLabel("Aantal dozen: " + aantalDozen);
	JLabel tspLabel = new JLabel("TSP-algoritme: " + tspSelected);



	public ControlePaneel() {
		System.out.println(orderOptions);

		for(String s : orderOptions){
			orderList.addItem("Order " + s);
		}


		super.setBackground(Color.WHITE);
		super.setLayout(new GridLayout(9,1, 5, 5));
		super.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		StartButton.setBorder(new RoundBtn(15));
		StopButton.setBorder(new RoundBtn(15));
		RepeatButton.setBorder(new RoundBtn(15));

		orderList.addActionListener(this);
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

			MagazijnPaneel.resetPanel();
			ItemList.clearList();
			DozenTabel.resetDozen();
			FirstFitAlgoritme.ruimteVrijmakenInDozen();

			if(orderList.getSelectedItem() != null) {
				selectedOrder = orderList.getSelectedItem().toString();
			}
			System.out.println(extractNumber.extract(selectedOrder));

			ArrayList<Integer> items = (DatabaseConnection.orderLineArray.get(extractNumber.extract(selectedOrder)));
			for(int i : items){
				ItemList.addItem("Item " + i);
				MagazijnPaneel.buttonArray[(i-1)].setBackground(Color.GREEN);
				}
			}

			bppLabel.setText("BPP-algoritme: " + bppSelected);
			tspLabel.setText("TSP-algoritme: " + tspSelected);
		if (src instanceof JButton srcBtn) {
			if (srcBtn == StartButton) {
				if(bppList.getSelectedItem().toString().equals("First Fit")) {
					DozenTabel.voegToeFirstFit();
				}
				if(bppList.getSelectedItem().toString().equals("Next Fit")) {
					DozenTabel.voegToeFirstFit();
				}

				ProcesBalk.veranderPickProcesBalk();
				ProcesBalk.veranderInpakProcesBalk();
				System.out.println(Arrays.deepToString(MagazijnPaneel.buttonArray));
				ItemList.clearList();
			}
		}


	}

}
