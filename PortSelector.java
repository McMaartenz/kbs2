import javax.swing.*;

public class PortSelector
{
	public static Port PortSelector(JFrame parent, Port port)
	{
		Port.updatePorten();
		Object[] comPorten = Port.porten;

		Object selectie = JOptionPane.showInputDialog(parent,
			"Kan niet verbinden op port " + port + ". Probeer opnieuw of annuleer:",
			"Verbindingsfout",
			JOptionPane.ERROR_MESSAGE,
			null,
			comPorten,
			comPorten[0]);

		if (selectie == null)
		{
			return null;
		}
		return new Port((String)selectie);
	}
}
