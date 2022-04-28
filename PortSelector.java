import javax.swing.*;

public class PortSelector
{
	public static Port keuze(JFrame parent, Port port)
	{
		Port.updatePorten();
		Object[] comPorten = Port.porten;

		Object selectie;
		if (comPorten.length == 0)
		{
			JOptionPane.showMessageDialog(parent, "Kan niet verbinden met COM. Sluit een Arduino aan en herstart het programma.",
					"Verbindingsfout", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		else
		{
			selectie = JOptionPane.showInputDialog(parent,
					"Kan niet verbinden op port " + port + ". Probeer opnieuw of annuleer:",
					"Verbindingsfout",
					JOptionPane.ERROR_MESSAGE,
					null,
					comPorten,
					comPorten[0]);
		}
		if (selectie == null)
		{
			return null;
		}
		return new Port((String)selectie);
	}
}
