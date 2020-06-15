package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.AboutAction;

public class MenuBar extends JMenuBar {
	public MenuBar() {
		JMenu menu1 = new JMenu("File");
		add(menu1);

		JMenu menu2 = new JMenu("View");
		add(menu2);

		JMenu menu3 = new JMenu("Other");

		JMenuItem menuItem = new JMenuItem("About us");
		menuItem.addActionListener(new AboutAction());
		menu3.add(menuItem);
		add(menu3);
	}
}
