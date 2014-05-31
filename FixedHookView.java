import java.awt.*;
import java.awt.geom.*;

public class FixedHookView {
	private final double width;
	private Color color = Color.GREEN;
	private Rectangle2D.Double shape = null;
	private FixedHook hook;
	
	public FixedHookView(FixedHook h) {
		this.width = 0.1;
		this.hook = h;
		this.shape = new Rectangle2D.Double();
	}

	public boolean contains(double x, double y) {
		return shape.getBounds2D().contains(x,y);
	}

	public void setSelected() {
		color = Color.RED;
	}

	public void setReleased() {
		color = Color.GREEN;
	}

	void updateView(Graphics2D g) {
		double hPos = hook.getPosition();

		shape.setFrameFromCenter(hPos, 0, hPos + width, width);
		g.setColor(color);
		g.fill(shape);
	}
}