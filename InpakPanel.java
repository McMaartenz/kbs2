import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InpakPanel extends JPanel implements ActionListener {

	private Database db;
	private JFrame parent;

	public InpakPanel(Display parent) {
		this.parent = parent;
		db = parent.getDB();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object src = ae.getSource();
		if (src instanceof JButton)
		{

		}
	}
}
