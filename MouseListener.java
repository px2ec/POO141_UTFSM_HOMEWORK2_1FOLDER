import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class MouseListener extends MouseAdapter implements KeyListener {
	private MyWorld world;
	private PhysicsElement currentElement;
	private ListIterator<PhysicsElement> litr;
	private ArrayList<PhysicsElement> inpos;
	 
	/** Handle the key typed event from the text field. */
	public void keyTyped(KeyEvent e) {
		
	}
	 
	/** Handle the key pressed event from the text field. */
	public void keyPressed(KeyEvent e) {
		//System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));

		if(e.getKeyCode()!=KeyEvent.VK_N) return;

		if (inpos.size() == 0) return;

		if (!(litr.hasNext())) return;

		PhysicsElement newElement = litr.next();
	
		if (currentElement!=null) 
			currentElement.setReleased();

		currentElement = newElement;
		currentElement.setSelected();

		world.repaintView();

	}
	 
	/** Handle the key released event from the text field. */
	public void keyReleased(KeyEvent e) {
		
	}

	public MouseListener (MyWorld w){
		inpos = new ArrayList<PhysicsElement>();
		world = w;
	} 

	public void mouseMoved(MouseEvent e) {
		Point2D.Double p = new Point2D.Double(0,0); // Change mouse coordenates from
		MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);// pixels to meters.
		inpos.clear();
		inpos = world.find(p.getX(), p.getY());
		//for (PhysicsElement el: inpos)
		//	System.out.println(el.getDescription());
		if (inpos.size() == 0) return;
		litr = inpos.listIterator();
		PhysicsElement newElement = litr.next();
		if (newElement == currentElement) return;
		if (currentElement != null) {
			currentElement.setReleased();
			currentElement = null;
		}
		if (newElement != null) { 
			currentElement = newElement;
			currentElement.setSelected();
		}
		world.repaintView();
	}
	public void mouseDragged(MouseEvent e) {
		Point2D.Double p = new Point2D.Double(0,0); // Change mouse coordenates from
		MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);// pixels to meters.
		if (currentElement != null)
			currentElement.dragTo(p.getX());

		world.repaintView();
	}
	public void mouseReleased(MouseEvent e) {
		if (currentElement == null) return;
		if (currentElement instanceof Elastic) {
			Elastic spring = (Elastic) currentElement;
			
			Point2D.Double p= new Point2D.Double(0,0);
			MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);

			 // we dragged a spring, so we look for and attachable element near by  
			SpringAttachable element = world.findAttachableElement(spring);
			if (element != null) {
				//System.out.print("Voy a enganchar");
				double pos_e  = ((SpringAttachable)element).getPosition();
				double rad  = ((SpringAttachable)element).getRadius();
				// we dragged a spring and it is near an attachable element,
				// so we hook it to a spring end.
				double a=spring.getAendPosition();
				if ((a > pos_e - rad) && (a < pos_e + rad)) {
					spring.attachAend(element);
					//System.out.println(" en A");
				}
				double b=spring.getBendPosition();
				if ((b > pos_e - rad) && (b < pos_e + rad)) {
					spring.attachBend(element);
					//System.out.println(" en B");
				}
			 }
		}    
		currentElement.setReleased();
		currentElement = null;
		world.repaintView();
	}
}