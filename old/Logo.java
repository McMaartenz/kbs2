import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Logo extends JPanel
{
	BufferedImage logo;

	public Logo()
	{
		super();

		logo = Plaatje.laad("img/logo.jpg");
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (logo == null)
		{
			return;
		}
		g.drawImage(logo, 0, 0, getWidth(), getHeight(), null);
	}
}
