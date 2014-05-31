import java.awt.*;

public abstract class PhysicsElement {
	// To identify each element within its category
	private final int myId;

	protected PhysicsElement( int id){
		myId = id;
	}

	protected int getId() {
		return myId;
	}

	public abstract String getDescription();
	public abstract String getState();
	public abstract void updateView(Graphics2D g);
	public abstract boolean contains(double x, double y);
	public abstract void setSelected();
	public abstract void setReleased();
	public abstract void dragTo(double x);
}