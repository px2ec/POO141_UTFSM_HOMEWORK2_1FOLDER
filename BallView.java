
/**
 * BallView class
 * @author Pedro Espinoza, Luis Ojeda, Felipe Veas
 */

import java.awt.*;
import java.awt.geom.*;

public class BallView {
	private Color color = Color.BLUE;
	private Ellipse2D.Double shape = null;
	private Ball ball;

	/**
	* Constructor BallViw. Initialize a new graphic ball.
	* @param b   ball class asociated
	*/
	public BallView (Ball b) {
		this.ball = b;
		this.shape = new Ellipse2D.Double();
	}

	/**
	* Check that point x,y existing into shape.
	* @return Its true if that point (x, y) is contained
	* @param x   x posittion
	* @param y   y position
	*/
	public boolean contains(double x, double y) {
		double bPos = ball.getPosition();
		double bRad = ball.getRadius();

		if ((bPos - bRad <= x) && (x <= bPos + bRad))
			if ((-bRad <= y) && (y <= bRad))
				return true;

		return false;
	}

	/**
	* Set select state graphic element.
	*/
	public void setSelected() {
		color = Color.RED;
	}

	/**
	* Set released state graphic element.
	*/
	public void setReleased() {
		color = Color.BLUE;
	}

	/**
	* Update graphic element.
	*/
	void updateView(Graphics2D g) {
		double radius = ball.getRadius();
		shape.setFrame(ball.getPosition() - radius, -radius, 2 * radius, 2 * radius);
		g.setColor(color);
		g.fill(shape);
	}
}