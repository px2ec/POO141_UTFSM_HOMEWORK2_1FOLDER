import java.util.*;
import java.lang.Math.*;
import java.awt.*;

public class Block extends PhysicsElement implements SpringAttachable, Simulateable {
	private static int id = 0;         // Block identification number
	private final double mass;
	private final double radius;
	private final double cof;          // Coefficient of friction. Used as static and kinetic friction.
	private double pos_t;              // Current position at time t
	private double pos_tPlusDelta;     // Next position in delta time in future
	private double speed_t;            // Speed at time t
	private double speed_tPlusDelta;   // Speed in delta time in future
	private double a_t;                // Acceleration at time t
	private double a_tMinusDelta;      // Acceleration delta time ago
	private ArrayList<Elastic> springs; // ArrayList can grow, arrays cannot
	private BlockView view;           // Ball view of Model-View-Controller design pattern

	private boolean in_collision = false;

	private Block() {
		// Nobody can create a block without state
		this(1.0, 0.1, 0, 0, 0);
	}

	public Block(double mass, double radius, double position, double speed, double cof) {
		super(id++);
		pos_t = position;
		speed_t = speed;
		this.cof = cof;
		this.mass = mass;
		this.radius = radius;

		a_t = 0;
		a_tMinusDelta = 0;
		springs = new ArrayList<Elastic>();
		view = new BlockView(this);
	}

	public double getRadius() {
		return radius;
	}
	public double getSpeed() {
		return speed_t;
	}
	private double getNetForce(MyWorld world) {
		double extForce = 0;
		double gravity = world.getGravity(); // Falta implementarla en la clase MyWorld
		//add kinetic friction force
		extForce += gravity * mass * cof * (int) Math.signum(speed_t);
		//add static friction force
		if ((int) Math.signum(speed_t) == 0){
			extForce += gravity * mass * cof * (int) Math.signum(a_t);
		}
		for (Elastic s: springs) {
			extForce += s.getForce(this);
		}
		
		return extForce;
	}
	public String getState() {
		if (speed_t != 0)
			return "Moviendose";

		return "Detenida";
	}
	public double getPosition() {
		return pos_t;
	}
	public double getMass() {
		return this.mass;
	}

	public void computeNextState(double delta_t, MyWorld world) {
		SpringAttachable b;
		a_t = getNetForce(world) / mass;
		
		/* Elastic collision */
		if ((b = world.findCollidingBall(this)) != null && !in_collision) {
			speed_tPlusDelta  = speed_t * (this.mass - b.getMass()) + 2 * b.getMass() * b.getSpeed();
			speed_tPlusDelta /= this.mass + b.getMass();
			pos_tPlusDelta = pos_t + speed_tPlusDelta * delta_t;
		//	System.err.println("pos_t: " + pos_t + "; pos_tPlusDelta: " + pos_tPlusDelta);

			in_collision = true;
		} else {
			speed_tPlusDelta = speed_t + 0.5 * (3 * a_t - a_tMinusDelta) * delta_t;
			// pos_tPlusDelta = pos_t + speed_t * delta_t + 0.5 * a_t * delta_t * delta_t;
			pos_tPlusDelta = pos_t + speed_t * delta_t + (4 * a_t - a_tMinusDelta) * delta_t * delta_t / 6;

			in_collision = false;
		}
	}

	public boolean collide(SpringAttachable b) {
		if (b == null)
			return false;

		return (Math.abs(b.getPosition() - this.pos_t) <= (b.getRadius() + this.radius));
	}

	public void updateState() {
		pos_t = pos_tPlusDelta;
		speed_t = speed_tPlusDelta;
		a_tMinusDelta = a_t;
	}

	public void updateView(Graphics2D g) {
		/*
		 * Update this Ball's view in Model-View-Controller
		 * design pattern
		 */
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

	public String getDescription() {
		return "Block_" + super.getId();
	}

	public void attachSpring(Elastic spring) {
		if (spring == null)
			return;

		springs.add(spring);
	}

	public void detachSpring(Elastic spring) {
		if (spring == null || springs == null)
			return;

		for (Elastic s: springs) {
			if (((PhysicsElement)spring).getId() == ((PhysicsElement)s).getId()) {
				springs.remove(s);
			}

		}
	}

	public void dragTo(double x) {
		this.pos_t = x;
	}
}
