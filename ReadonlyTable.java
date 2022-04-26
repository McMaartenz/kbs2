import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReadonlyTable extends JTable
{
	public ReadonlyTable(DefaultTableModel model) {
		super(model);
	}

	@Override
	public boolean isCellEditable(int r, int c) {
		return false;
	}
}
