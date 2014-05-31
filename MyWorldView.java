import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*; 
import java.awt.*;
import java.util.*;

public class MyWorldView extends JPanel { 
	/* BEGIN declarations to use metric coordinate system (not pixels) */
	public static int WIDTH = 900;  // [px]
	public static int HEIGHT = 150; // [px]
	/*
	 * MyWorld space origen (x,y) will be on
	 * (X_ORIGEN, Y_ORIGEN) of the panel space.
	 */
	public static int X_ORIGEN = (int)(WIDTH * 0.1);
	public static int Y_ORIGEN = (int)(HEIGHT * 0.9);
	// Transforms (x,y) in (X,Y) of panel
	public static AffineTransform SPACE_TRANSFORM;
	// Transforms (X,Y) in (x,y) of my world
	public static AffineTransform SPACE_INVERSE_TRANSFORM;
	// Lines to draw my world axes (singular axis, plural axes)
	public static Line2D.Double X_AXIS;
	public static Line2D.Double Y_AXIS;
	// 1 [m] equals 100 [px]
	private static double AXES_SCALE = 200.0;

	static {
		SPACE_TRANSFORM = AffineTransform.getTranslateInstance(X_ORIGEN, Y_ORIGEN);
		// It also inverts direction of y-coordinate 
		SPACE_TRANSFORM.scale(AXES_SCALE, -AXES_SCALE);
		try {
			SPACE_INVERSE_TRANSFORM = SPACE_TRANSFORM.createInverse();
		} catch (NoninvertibleTransformException e) {
			System.exit(0);
		}
		// Each axis length is 3 [m] from origin
		X_AXIS = new Line2D.Double(-0.2, 0, 4.0, 0);
		Y_AXIS = new Line2D.Double(0, -0.1, 0, 0.1);
	}
	// END declarations to use metric coordinate system (not pixels)
	
	private MyWorld world;

	private MouseListener mListener;
	
	public MyWorldView(MyWorld w){
		world = w;
		mListener = new MouseListener(w);
		addMouseMotionListener(mListener);
		addMouseListener(mListener);
		addKeyListener(mListener);
		setFocusable(true);
	}

	public void repaintView() {
		repaint();
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g); // It paints the background
		g2.drawString("ELO329 1er.Sem. 2014,   1 [m] = "+AXES_SCALE+" [pixels]", WIDTH/4, 30);
		g2.transform(SPACE_TRANSFORM);
		g2.setStroke(new BasicStroke(0.02f));
		g2.draw(X_AXIS);
		g2.draw(Y_AXIS);
		/* .......*/
		ArrayList<PhysicsElement> elements = world.getPhysicsElements();
		for (PhysicsElement e: elements) {
			if (e instanceof Ball || e instanceof FixedHook || e instanceof Spring || e instanceof Block || e instanceof Rubber) {
				e.updateView(g2);
			}
		}
	}

	/**/

	public void enableMouseListener(){
		addMouseMotionListener(mListener);
		addMouseListener(mListener);         
	}
	public void desableMouseListener(){
		removeMouseMotionListener(mListener);
		removeMouseListener(mListener);
	}
}