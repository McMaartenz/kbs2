import javax.swing.*;

public class Fout
{
	public static void toon(JFrame parent, String probleembeschrijving)
	{
		JOptionPane.showMessageDialog(parent, probleembeschrijving, "Probleem", JOptionPane.ERROR_MESSAGE);
	}
}