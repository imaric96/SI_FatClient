package controller;

import model.TableModel;
import view.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAction implements ActionListener {
	Object[][] object;
	TableModel tableModel;
//	private static String tableName = "";

	public AddAction(TableModel tableModel) {
		this.tableModel = tableModel;
	}

	public void actionPerformed(ActionEvent e) {
		MainWindow.getMainWindow().getMiddle().getTableModel().addRow(new Object[]{});

	}


}
