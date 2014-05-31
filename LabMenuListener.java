import java.awt.*;
import java.awt.event.*; 
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

public class LabMenuListener implements ActionListener {
	private MyWorld  world;
	public LabMenuListener (MyWorld  w){
		world = w;
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem menuItem = (JMenuItem)(e.getSource());
		String text = menuItem.getText();
		
		// Actions associated to main manu options
		if (text.equals("My scenario")) {  // here you define Etapa2's configuration
			/*double mass = 1.0;      //  1.0 [kg] 
			double radius = 0.1;    // 10.0 [cm] 
			double position = 0.0;  //  1.0 [m] 
			double speed = 0.5;     //  0.5 [m/s]
			Ball b0 = new Ball(mass, radius, position, speed);
			Ball b1 = new Ball(mass, radius, 2.0, 0);
			FixedHook f0 = new FixedHook(1.0);
			world.addElement(b0);
			world.addElement(b1);
			world.addElement(f0);*/

			double mass = 2.0;      //  1.0 [kg] 
			double radius = 0.1;    // 10.0 [cm] 
			double position = 0.0;  //  1.0 [m] 
			double speed = 1.0;     //  0.5 [m/s]
			Ball b0 = new Ball(mass, radius, 1.5, speed);
			Ball b1 = new Ball(mass, radius, 2.5, 0);
			FixedHook f0 = new FixedHook(0.0);
			Spring sp0 = new Spring(1.0, 1.0);

			sp0.attachAend(f0);
			sp0.attachBend(b0);

			world.addElement(b0);
			world.addElement(b1);
			world.addElement(f0);
			world.addElement(sp0);
		}
		if (text.equals("Ball")) {
			Ball b0 = new Ball(1.0, 0.1, 0, 0);
			world.addElement(b0);      
		}
		if (text.equals("Fixed Hook")) {
			FixedHook f0 = new FixedHook(0.0);
			world.addElement(f0);
		} 
		if (text.equals("Spring")){
			Spring sp0 = new Spring(1.0, 1.0);
			world.addElement(sp0);
		}

		if (text.equals("Block")){
			Block bl = new Block(1.0, 0.1, 0, 0, 0.004);
			world.addElement(bl);
		}

		if (text.equals("Rubber")){
			Rubber rb0 = new Rubber(1.0, 1.0);
			world.addElement(rb0);
		}

		// Actions associated to MyWorld submenu
		if (text.equals("Start")) world.start();

		if (text.equals("Stop")) world.stop();

		if (text.equals("Delta time")) {
			String data = JOptionPane.showInputDialog("Enter delta t [s]");
			world.setDelta_t(Double.parseDouble(data));
		}
		if (text.equals("View Refresh time")) {
			String data = JOptionPane.showInputDialog("Enter sample-rate t [s]");
			world.setRefreshPeriod(Double.parseDouble(data));
		}
	}
}