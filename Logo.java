import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Logo extends JPanel
{
	BufferedImage logo;

	public Logo()
	{
		super();

		logo = loadImage("img/logo.jpg");
	}

	private BufferedImage loadImage(String filename)
	{
		BufferedImage result = null;
		try
		{
			result = ImageIO.read(new File(filename));
		}
		catch (IOException ie)
		{
			System.out.println("Unable to load papaya image");
		}
		return result;
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
