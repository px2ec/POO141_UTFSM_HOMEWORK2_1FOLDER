
/**
 * MyWorld class
 * @author Pedro Espinoza, Luis Ojeda, Felipe Veas
 */

import java.util.*;
import java.io.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class MyWorld implements ActionListener {
	private PrintStream out;
	private ArrayList<PhysicsElement> elements;  // array to hold everything in my world.
	private MyWorldView view;   // NEW
	private Timer passingTime;   // NEW
	private double t;        // simulation time
	private double delta_t;        // in seconds
	private double refreshPeriod;  // in seconds
	private double gravity;
	private ArrayList<PhysicsElement> inpos;

	/**
	* Constructor Myworld. Initialize a new physic World.
	*/
	public MyWorld() {
		this(System.out, -9.8);
	}

	/**
	* Constructor Myworld. Initialize a new physic World with custom params.
	* @param output  text out
	*/
	public MyWorld(PrintStream output) {
		this(output, -9.8); 
	}

	/**
	* Constructor Myworld. Initialize a new physic World with custom params.
	* @param output  text out
	* @param gravity   World's gravity
	*/
	public MyWorld(PrintStream output, double gravity) {
		view = null;
		this.gravity = gravity;
		out = output;
		t = 0;
		refreshPeriod = 0.06; // 60.00 [ms]
		delta_t = 0.00001;    //  0.01 [ms]
		elements = new ArrayList<PhysicsElement>();
		inpos = new ArrayList<PhysicsElement>();
		passingTime = new Timer((int)(refreshPeriod*1000), this); 
	}

	/**
	* Get gravity value from this world.
	* @return MyWorld's gravity
	*/
	public double getGravity(){
		return gravity;
	}

	/**
	* Set gravity value in this world.
	* @param gr   Gravity's value
	*/
	public void setGravity(double gr){
		gravity = gr;
	}

	/**
	* Add a new physic element.
	* @param e   Physic element
	*/
	public void addElement(PhysicsElement e) {
		elements.add(e);
		view.repaintView();
	}

	/**
	* Asociate a graphic world class.
	* @param view   graphic world
	*/
	public void setView(MyWorldView view) {
		this.view = view;
	}

	/**
	* Set delta time in this world.
	* @param delta   asign delta
	*/
	public void setDelta_t(double delta) {
		delta_t = delta;
	}

	/**
	* Set sample-rate in this world.
	* @param rp   sample-rate value
	*/
	public void setRefreshPeriod(double rp) {
		refreshPeriod = rp;
		passingTime.setDelay((int)(refreshPeriod*1000)); // convert from [s] to [ms]
	}

	/**
	* Start simulation.
	*/
	public void start() {
		if (passingTime.isRunning())
			return;
		passingTime.start();
		view.desableMouseListener();
      
	}

	/**
	* Stop Simulation.
	*/
	public void stop() {
		passingTime.stop();
		view.enableMouseListener(); 
	}

	/**
	* Simulate iterative way.
	* @param event   param for listener
	*/
	public void actionPerformed(ActionEvent event) {
		/*
		 * Like simulate method of Assignment 1,
		 * The arguments are attributes here.
		 */
		double nextStop = t + refreshPeriod;
		for (; t<nextStop; t += delta_t) {
			// Compute each element next state based on current global state
			for (PhysicsElement e: elements) {
				if (e instanceof Simulateable) {
					Simulateable s = (Simulateable) e;
					s.computeNextState(delta_t,this);
				}
			}
			// For each element update its state
			for (PhysicsElement e: elements) {
				if (e instanceof Simulateable) {
					Simulateable s = (Simulateable) e;
					s.updateState();
					repaintView();
				}
			}
		}
	}

	/**
	* Update graphic world.
	*/
	public void repaintView() {
		view.repaintView();
	}

	/**
	* Check that elements is colliding with something.
	* @return Ball colliding
	* @param me   consulting ball
	*/
	public SpringAttachable findCollidingBall(SpringAttachable me) {
		for (PhysicsElement e: elements)
			if ((e instanceof Ball) || (e instanceof Block)) {
				SpringAttachable b = (SpringAttachable)e;
				if ((b!=me) && b.collide(me)) return b;
			}
		return null;
	}

	/**
	* Check elements asociated with elastic elements.
	* @return Foundnd attachable elements
	* @param s   Spring source
	*/
    public SpringAttachable findAttachableElement(Elastic s){
		for (PhysicsElement e: elements) {
			if (e instanceof SpringAttachable) {
				if (s.isAattachedTo((SpringAttachable)e)) continue;
				if (s.isBattachedTo((SpringAttachable)e)) continue;
				double pos_e  = ((SpringAttachable)e).getPosition();
				double rad  = ((SpringAttachable)e).getRadius();
				double posA = s.getAendPosition();
				double posB = s.getBendPosition();
				if (((posA > pos_e - rad) && (posA < pos_e + rad)) || ((posB > pos_e - rad) && (posB < pos_e + rad)))
					return (SpringAttachable) e;
			}
		}
    	return null;
    }

	/**
	* Get all physic elements asociated with this world.
	* @return Elements from this world
	*/
	public ArrayList<PhysicsElement> getPhysicsElements() {
		return elements;
	}

	/**
	* Get all physic elements in (x,y) point.
	* @return Elements in a point
	* @param x   x position
	* @param y   y position
	*/
	public ArrayList<PhysicsElement> find(double x, double y) {
		inpos.clear();
		for (PhysicsElement e: elements) {
				if (e.contains(x,y)) inpos.add(e);
		}
		return inpos;
	}
}