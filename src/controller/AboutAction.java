package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.MainWindow;

public class AboutAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		JOptionPane.showMessageDialog(MainWindow.getMainWindow(), "403_4 :)", "About us", JOptionPane.PLAIN_MESSAGE);
	}
}
