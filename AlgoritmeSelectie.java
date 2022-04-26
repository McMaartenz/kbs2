import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static javax.swing.SwingConstants.CENTER;

public class AlgoritmeSelectie extends JDialog implements ActionListener
{
	private Selectie selectie;

	private JLabel algoritme;
	private JLabel online;
	private JLabel offline;

	private JRadioButton bestFit;
	private JRadioButton nextFit;
	private JRadioButton firstFit;
	private JRadioButton worstFit;
	private JRadioButton bestFitDecreasing;
	private JRadioButton nextFitDecreasing;
	private JRadioButton firstFitDecreasing;

	private JRadioButton[] radioButtons;

	private JButton ok;
	private JButton cancel;

	public AlgoritmeSelectie(JFrame parent, String title, boolean modal, Selectie selectie)
	{
		super(parent, title, modal);
		this.selectie = selectie;

		GridBagConstraints gbc = new GridBagConstraints();
		{
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.ipadx = 0;
			gbc.ipady = 0;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			gbc.gridwidth = 5;
			gbc.fill = GridBagConstraints.BOTH;
		}
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Event zorgt voor disposing
		this.addWindowListener(new WindowListener()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				selectie.optie = Selectie.Optie.DISPOSED;
				dispose();
			}

			/* negeer deze */
			@Override
			public void windowOpened(WindowEvent e)
			{
			}

			@Override
			public void windowClosed(WindowEvent e)
			{
			}

			@Override
			public void windowIconified(WindowEvent e)
			{
			}

			@Override
			public void windowDeiconified(WindowEvent e)
			{
			}

			@Override
			public void windowActivated(WindowEvent e)
			{
			}

			@Override
			public void windowDeactivated(WindowEvent e)
			{
			}
		});

		algoritme = new JLabel("Algoritme: (Aantal geschatte dozen: 0)", CENTER);
		online = new JLabel("<html><b>Online:</b></html>");
		offline = new JLabel("<html><b>Offline:</b></html>");

		ok = new JButton("OK");
		cancel = new JButton("Cancel");

		ok.addActionListener(this);
		cancel.addActionListener(this);

		add(algoritme, gbc);

		bestFit = new JRadioButton("Best fit");
		nextFit = new JRadioButton("Next fit");
		firstFit = new JRadioButton("First fit");
		worstFit = new JRadioButton("Worst fit");
		bestFitDecreasing = new JRadioButton("Best fit decreasing");
		nextFitDecreasing = new JRadioButton("Next fit decreasing");
		firstFitDecreasing = new JRadioButton("First fit decreasing");

		radioButtons = new JRadioButton[7];
		radioButtons[0] = bestFit;
		radioButtons[1] = nextFit;
		radioButtons[2] = firstFit;
		radioButtons[3] = worstFit;
		radioButtons[4] = bestFitDecreasing;
		radioButtons[5] = nextFitDecreasing;
		radioButtons[6] = firstFitDecreasing;

		bestFit.setSelected(true);
		selectie.optie = Selectie.Optie.BEST_FIT;

		ButtonGroup buttonGroup = new ButtonGroup();
		for (JRadioButton btn : radioButtons)
		{
			buttonGroup.add(btn);
			btn.addActionListener(this);
		}

		gbc.gridwidth = 1;
		gbc.gridy++;
		add(online, gbc);
		gbc.gridx++;
		add(bestFit, gbc);
		gbc.gridx++;
		add(nextFit, gbc);
		gbc.gridx++;
		add(firstFit, gbc);
		gbc.gridx++;
		add(worstFit, gbc);
		gbc.gridx = 0;
		gbc.gridy++;
		add(offline, gbc);
		gbc.gridx++;
		add(bestFitDecreasing, gbc);
		gbc.gridx++;
		add(nextFitDecreasing, gbc);
		gbc.gridx++;
		add(firstFitDecreasing, gbc);
		gbc.gridx = 1;
		gbc.gridy++;
		add(ok, gbc);
		gbc.gridx++;
		add(cancel, gbc);

		setSize(480, 360);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object src = ae.getSource();
		if (src instanceof JRadioButton)
		{
			JRadioButton srcBtn = (JRadioButton) src;
			for (int i = 0; i < 7; i++)
			{
				if (srcBtn == radioButtons[i])
				{
					selectie.optie = Selectie.Optie.values()[i + 1];
					// TODO: Calculate aantal dozen & update label!
					break;
				}
			}
		}
		else if (src instanceof JButton)
		{
			JButton srcBtn = (JButton) src;
			if (srcBtn == cancel)
			{
				selectie.optie = Selectie.Optie.DISPOSED;
			}
			dispose();
		}
	}
}
