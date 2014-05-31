import java.awt.*;
import java.awt.geom.*;

public class RubberView {

	private Rubber rubber;   
	private Rectangle2D.Double shape = null;
	private Color color = Color.GRAY;

	public RubberView(Rubber r) {
		this.rubber = r;
		this.shape = new Rectangle2D.Double();
	}

	void updateView(Graphics2D g) {
		double ax = rubber.getAendPosition();
		double bx = rubber.getBendPosition();

		shape.setFrameFromDiagonal(ax, -0.04, bx, 0.04);
		g.setColor(color);
		g.fill(shape);
	}

	public boolean contains(double x, double y){
		return shape.getBounds2D().contains(x,y);
	}
	public void setSelected() {
		color = Color.RED;
	}
	public void setReleased() {
		color = Color.GRAY;
	}
}