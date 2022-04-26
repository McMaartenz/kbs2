import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderPanel extends JPanel implements ActionListener {

	private Database db;
	private JFrame parent;

	public OrderPanel(Display parent) {
		this.parent = parent;
		db = parent.getDB();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object src = ae.getSource();
		if (src instanceof JButton) {
			JButton srcBtn = (JButton)src;
		}
	}
}
