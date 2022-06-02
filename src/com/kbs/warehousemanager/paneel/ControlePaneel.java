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
			if(orderList.getSelectedItem() != null) {
				selectedOrder = orderList.getSelectedItem().toString();
			}
			System.out.println(extractNumber.extract(selectedOrder));

			ArrayList<Integer> items = (DatabaseConnection.orderLineArray.get(extractNumber.extract(selectedOrder)));
			Collections.sort(items);
			for(int i : items){
				ItemList.addItem("Item " + i);
				MagazijnPaneel.buttonArray[(i-1)].setBackground(Color.GREEN);
				}
			}
			bppLabel.setText("BPP-algoritme: " + bppSelected);
			tspLabel.setText("TSP-algoritme: " + tspSelected);
		if (src instanceof JButton srcBtn) {
			if (srcBtn == StartButton) {
				//code voor de startbutton, buttons resetten en geselecteerde producten ophalen moeten gedaan worden
				DozenTabel.voegToe(5);
//				MagazijnPaneel.resetPanel();
				ItemList.clearList();
				System.out.println(Arrays.deepToString(MagazijnPaneel.buttonArray));
				//get the correct bpp agorithm
//					int inhoudContainer = 10;
//					int alleContainers;
//					if (bppSelected.equals("First Fit")) {
//										public static int firstFit(int[] productLijst) {
//											int[] productlijst = productLijst;
//											// Initialize result (Count of bins)
//											int result = 0;
//											int[] bin_rem = new int[alleContainers];
//
//											// Place items one by one
//											for (int i = 0; i < alleContainers; i++) {
//												int j;
//												for (j = 0; j < result; j++) {
//													if (bin_rem[j] >= productlijst[i]) {
//														bin_rem[j] = bin_rem[j] - productlijst[i];
//														break;
//													}
//												}
//
//												if (j == result) {
//													bin_rem[result] = inhoudContainer - productlijst[i];
//													result++;
//												}
//											}
//											return result;
//										}
//									}
//									if(bppSelected.equals("Next Fit")) {
//										public static int nextFit(int[] productlijst) {
//
//											// Initialize result (Count of bins) and remaining
//											// capacity in current bin.
//											int res = 0, bin_rem = inhoudContainer;
//
//											// Place items one by one
//											for (int i = 0; i < alleContainers; i++) {
//												// If this item can't fit in current bin
//												if (productlijst[i] > bin_rem) {
//													res++; // Use a new bin
//													bin_rem = inhoudContainer - productlijst[i];
//												} else
//													bin_rem -= productlijst[i];
//											}
//											return res;
//										}
//									}
//									if (bppSelected.equals("Best Fit")) {
//										public static int bestFit(int[] productlijst) {
//											int res = 0;
//											int[] bin_rem = new int[alleContainers];
//
//											for (int i = 0; i < alleContainers; i++) {
//												int j;
//												int min = inhoudContainer + 1, bi = 0;
//												for (j = 0; j < res; j++) {
//													if (bin_rem[j] >= productlijst[i] &&
//															bin_rem[j] - productlijst[i] < min) {
//														bi = j;
//														min = bin_rem[j] - productlijst[i];
//													}
//												}
//												if (min == inhoudContainer + 1) {
//													bin_rem[res] = inhoudContainer - productlijst[i];
//													res++;
//												} else
//													bin_rem[bi] -= productlijst[i];
//											}
//											return res;
//										}
//									}
//									if (bppSelected.equals("Worst Fit")) {
//
//										// Initialize result (Count of bins)
//										int res = 0;
//
//										// Create an array to store remaining space in bins
//										// there can be at most n bins
//										int bin_rem[] = new int[alleContainers];
//
//										// Place items one by one
//										for (int i = 0; i < alleContainers; i++) {
//
//											// Find the best bin that ca\n accommodate
//											// weight[i]
//											int j;
//
//											// Initialize maximum space left and index
//											// of worst bin
//											int mx = -1, wi = 0;
//
//											for (j = 0; j < res; j++) {
//												if (bin_rem[j] >= product[i] && bin_rem[j] - product[i] > mx) {
//													wi = j;
//													mx = bin_rem[j] - product[i];
//												}
//											}
//
//											// If no bin could accommodate weight[i],
//											// create a new bin
//											if (mx == -1) {
//												bin_rem[res] = inhoudContainer - product[i];
//												res++;
//											} else // Assign the item to best bin
//												bin_rem[wi] -= product[i];
//										}
//										return res;
//									}
//									if (bppSelected.equals("First-Fit-Decreasing")) {
//										public static int firstFitDec() {
//											List<Integer> list = new ArrayList<>();
//											for (int i : product) {
//												list.add(i);
//											}
//											Integer[] array = list.toArray(new Integer[0]);
//											// First sort all weights in decreasing order
//											Arrays.sort(array, Collections.reverseOrder());
//											// Now call first fit for sorted items
//											int i;
//											int[] sortProduct = new int[array.length];
//											for (i = 0; i < array.length; i++) {
//												sortProduct[i] = array[i];
//											}
//											return firstFit(sortProduct);
//										}
//									}
//									if (bppSelected.equals("Next-Fit-Decreasing")) {
//										public static int nextFitDec() {
//											List<Integer> list = new ArrayList<>();
//											for (int i : product) {
//												list.add(i);
//											}
//											Integer[] array = list.toArray(new Integer[0]);
//											// First sort all weights in decreasing order
//											Arrays.sort(array, Collections.reverseOrder());
//											// Now call first fit for sorted items
//											int i;
//											int[] sortProduct = new int[array.length];
//											for (i = 0; i < array.length; i++) {
//												sortProduct[i] = array[i];
//											}
//											return nextFit(sortProduct);
//										}
//									}
//									if (bppSelected.equals("Worst-Fit-Decreasing")) {
//										public static int bestFitDec() {
//											List<Integer> list = new ArrayList<>();
//											for (int i : product) {
//												list.add(i);
//											}
//											Integer[] array = list.toArray(new Integer[0]);
//											// First sort all weights in decreasing order
//											Arrays.sort(array, Collections.reverseOrder());
//											// Now call first fit for sorted items
//											int i;
//											int[] sortProduct = new int[array.length];
//											for (i = 0; i < array.length; i++) {
//												sortProduct[i] = array[i];
//											}
//											return bestFit(sortProduct);
//										}
			}

		}
	}
}

