import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Plaatje
{
	public static BufferedImage laad(String filename)
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
}
