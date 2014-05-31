import java.util.*;
import java.awt.*;

public class FixedHook extends PhysicsElement implements Simulateable, SpringAttachable {
	private static int id = 0;         // Ball identification number
	private double pos_t;              // Current position at time t
	private double pos_tPlusDelta;     // Next position in delta time in future
	private double speed_t;
	private ArrayList<Elastic> springs; // ArrayList can grow, arrays cannot
	private FixedHookView view;

	private FixedHook() {
		// Nobody can create a block without state
		this(0);
	}
	
	public FixedHook(double position) {
		super(id++);
		pos_t = position;

		springs = new ArrayList<Elastic>();
		view = new FixedHookView(this);
	}

	public double getPosition() {
		return pos_t;
	}

	public double getSpeed() {
		return speed_t;
	}

	public void computeNextState(double delta_t, MyWorld world) {
		pos_tPlusDelta = pos_t;
	}

	public void updateState() {
		pos_t = pos_tPlusDelta;
	}
	
	public double getRadius() {
		return 0.1;
	}

	public String getDescription() {
		return "FixedHook_" + super.getId();
	}

	public void attachSpring(Elastic spring) {
		if (spring == null)
			return;

		springs.add(spring);
	}
	public void detachSpring(Elastic spring) {
		if (spring == null)
			return;

		for (Elastic s: springs) {
			if (((PhysicsElement)spring).getId() == ((PhysicsElement)s).getId()) {
				springs.remove(spring);
			}

		}
	}
	public void updateView (Graphics2D g){
    	view.updateView(g);
    }
	public boolean contains(double x, double y) {
		return view.contains(x,y);
	}
	public void setSelected() {
		view.setSelected();
	}
	public void setReleased() {
		view.setReleased();
	}
	public void dragTo(double x) {
		this.pos_t = x;
	}
	public String getState() {
		return getPosition() + "";
	}
	
	public double getMass() {
		return 0;
	}
	
	public boolean collide(SpringAttachable b) {
		return false;
	}
}