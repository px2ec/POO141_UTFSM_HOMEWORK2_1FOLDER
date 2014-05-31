import javax.swing.JFrame;
import javax.swing.*;
import java.awt.Container;
import javax.swing.KeyStroke;
import java.awt.event.*;

public class PhysicsLab {
	public static void main(String[] args) {
		PhysicsLab_GUI lab_gui = new PhysicsLab_GUI();
		lab_gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lab_gui.setVisible(true);
	}
}

class PhysicsLab_GUI extends JFrame {
	MyWorld world = new MyWorld();
	public PhysicsLab_GUI() {
		setTitle("My Small and Nice Physics Laboratory");
		setSize(MyWorldView.WIDTH, MyWorldView.HEIGHT+50);  // height+50 to account for menu height
		
		MyWorldView  worldView = new MyWorldView(world);
		world.setView(worldView);
		add(worldView);  
		LabMenuListener menuListener = new LabMenuListener(world);
		this.setJMenuBar(this.createLabMenuBar(menuListener));
		/* ... */
	}

	public JMenuBar createLabMenuBar(LabMenuListener menu_l) {
		JMenuBar mb = new JMenuBar();
		
		JMenu menu = new JMenu ("Configuration");
		mb.add(menu);
		JMenu subMenu = new JMenu("Insert");  
		menu.add(subMenu);
		
		JMenuItem menuItem = new JMenuItem("Ball");
		menuItem.addActionListener(menu_l);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("Spring");
		menuItem.addActionListener(menu_l);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("Fixed Hook");
		menuItem.addActionListener(menu_l);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("Block");
		menuItem.addActionListener(menu_l);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("Rubber");
		menuItem.addActionListener(menu_l);
		subMenu.add(menuItem);

		menuItem = new JMenuItem("My scenario");
		menuItem.addActionListener(menu_l);
		subMenu.add(menuItem);
 
		menu = new JMenu("MyWorld");
		mb.add(menu);
		menuItem = new JMenuItem("Start");
		menuItem.addActionListener(menu_l);
		menu.add(menuItem);

		menuItem = new JMenuItem("Stop");
		menuItem.addActionListener(menu_l);
		menu.add(menuItem);

		menuItem = new JMenuItem("Next Item");
		menuItem.addActionListener(menu_l);
		menu.add(menuItem);

		JMenu submenu = new JMenu("Simulator");

		menuItem = new JMenuItem("Delta time");
		menuItem.addActionListener(menu_l);
		submenu.add(menuItem);

		menuItem = new JMenuItem("View Refresh time");
		menuItem.addActionListener(menu_l);
		submenu.add(menuItem);

		menu.add(submenu);
		/* ...*/
		return mb;
	}
}