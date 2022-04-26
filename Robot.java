import java.awt.*;

public class Robot {
	private Point positie;

	public Robot() {
		this(new Point(0, 0));
	}

	public Robot(Point positie) {
		this.positie = positie;
	}

	public Point getPositie() {
		return positie;
	}
}
