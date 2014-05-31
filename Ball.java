
/**
 * Ball class
 * @author Pedro Espinoza, Luis Ojeda, Felipe Veas
 */

import java.util.*;
import java.awt.*;

public class Ball extends PhysicsElement implements Simulateable, SpringAttachable {
	private static int id = 0;       // Ball identification number
	private final double mass;
	private final double radius;
	private double pos_t;            // Current position at time t
	private double pos_tPlusDelta;   // Next position in delta time in future
	private double speed_t;          // Speed at time t
	private double speed_tPlusDelta;   // Speed in delta time in future
	private double a_t;                // Acceleration at time t
	private double a_tMinusDelta;      // Acceleration delta time ago
	private BallView view;           // Ball view of Model-View-Controller design pattern
	private ArrayList<Elastic> springs; // ArrayList can grow, arrays cannot

	/**
	* Constructor Ball. Initialize a new physic ball.
	*/
	private Ball() {
		// Nobody can create a block without state
		this(1.0, 0.1, 0,0);
	}

	/**
	* Constructor Ball. Initialize a new physic ball with custom params.
	* @param mass   ball's mass
	* @param radius   ball's radius
	* @param position   ball's position
	* @param speed   ball's speed
	*/
	public Ball(double mass, double radius, double position, double speed){
		super(id++);
		this.pos_t = position;
		this.speed_t = speed;
		this.mass = mass;
		this.radius = radius;
		this.a_t = 0;
		this.a_tMinusDelta = 0;
		view = new BallView(this);
		springs = new ArrayList<Elastic>();
	}

	/**
	* Get the Ball' mass.
	* @return Ball's <tt>mass</tt>
	*/
	public double getMass() {
		return mass;
	}

	/**
	* Get the Ball's radius.
	* @return Ball's <tt>radius</tt>
	*/
	public double getRadius() {
		return radius;
	}

	/**
	* Get the Ball's position.
	* @return Ball's <tt>position</tt>
	*/
	public double getPosition() {
		return pos_t;
	}

	/**
	* Get the Ball's speed.
	* @return Ball's <tt>speed</tt>
	*/
	public double getSpeed() {
		return speed_t;
	}
	
	/**
	* Get the Ball's net force.
	* @return Ball's <tt>net force</tt>
	*/
	private double getNetForce() {
		double extForce = 0;
		
		for (Elastic s: springs) {
			extForce += s.getForce(this);
		}

		return extForce;
	}

	/**
	* Compute physics state.
	* @param delta_t   time's delta for computing
	* @param world   World class
	*/
	public void computeNextState(double delta_t, MyWorld world) {
		SpringAttachable b;
		a_t = getNetForce() / mass;

		/* Elastic collision */
		if ((b = world.findCollidingBall(this)) != null) {
			speed_tPlusDelta  = speed_t * (this.mass - b.getMass()) + 2 * b.getMass() * b.getSpeed();
			speed_tPlusDelta /= this.mass + b.getMass();
			pos_tPlusDelta = pos_t + speed_tPlusDelta * delta_t;
		} else {
			speed_tPlusDelta = speed_t + 0.5 * (3 * a_t - a_tMinusDelta) * delta_t;
			pos_tPlusDelta = pos_t + speed_t * delta_t + (4 * a_t - a_tMinusDelta) * delta_t * delta_t / 6;
		}
	}

	/**
	* Check that ball is colliding with something.
	* @return If this collide with another element
	* @param b   referency element
	*/
	public boolean collide(SpringAttachable b) {
		if (b == null)
			return false;

		return (Math.abs(b.getPosition() - this.pos_t) <= (b.getRadius() + this.radius));
	}

	/**
	* Update state after computing.
	*/
	public void updateState() {
		pos_t = pos_tPlusDelta;
		speed_t = speed_tPlusDelta;
		a_tMinusDelta = a_t;
	}

	/**
	* Update graphic element.
	*/
	public void updateView(Graphics2D g) {
		/*
		 * Update this Ball's view in Model-View-Controller
		 * design pattern
		 */
		view.updateView(g);
	}

	/**
	* Check that point x,y existing into shape.
	* @return Its true if that point (x, y) is contained
	* @param x   x posittion
	* @param y   y position
	*/
	public boolean contains(double x, double y) {
		return view.contains(x,y);
	}

	/**
	* Set select state graphic element.
	*/
	public void setSelected() {
		view.setSelected();
	}

	/**
	* Set released state graphic element.
	*/
	public void setReleased() {
		view.setReleased();
	}

	/**
	* Drag physic element with graphic element.
	* @param x   x position target
	*/
	public void dragTo(double x) {
		this.pos_t = x;
	}

	/**
	* Get ball' description.
	* @return Description for this element
	*/
	public String getDescription() {
		return "Ball_" + getId() + ":x";
	}

	/**
	* Get ball's state
	* @return Ball's state as string.
	*/
	public String getState() {
		return getPosition() + "";
	}

	/**
	* Attach an elastic element.
	* @param spring   spring element
	*/
	public void attachSpring(Elastic spring) {
		if (spring == null)
			return;

		springs.add(spring);
	}

	/**
	* Detach an elastic element
	* @param spring   spring element
	*/
	public void detachSpring(Elastic spring) {
		if (spring == null || springs.size() == 0)
			return;

		for (Elastic s: springs) {
			if (((PhysicsElement)spring).getId() == ((PhysicsElement)s).getId()) {
				springs.remove(s);
			}

		}
	}
}
