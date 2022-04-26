import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InpakPanel extends JPanel implements ActionListener {

	private Database db;
	private JFrame parent;
	private JButton openAlgoritmeSelectie;

	public InpakPanel(Display parent) {
		this.parent = parent;
		db = parent.getDB();

		openAlgoritmeSelectie = new JButton("Open algoritme selectie");
		openAlgoritmeSelectie.addActionListener(this);
		add(openAlgoritmeSelectie);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object src = ae.getSource();
		if (src instanceof JButton) {
			JButton srcBtn = (JButton)src;
			if (srcBtn == openAlgoritmeSelectie) {
				Selectie selectie = new Selectie(Selectie.Optie.DISPOSED);
				AlgoritmeSelectie as = new AlgoritmeSelectie(parent, "Selecteer een algoritme", true, selectie);
				System.out.println("Selectie: " + selectie.optie);
				// TODO: if logica baseerd op selectie
			}
		}
	}
}
