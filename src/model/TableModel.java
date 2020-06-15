package model;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {

	public void removeColumn(int column) {
		columnIdentifiers.remove(column);
		for (Object row : dataVector) {
			((Vector) row).remove(column);
		}
		fireTableStructureChanged();
	}

	public void removeAllColumn() {
		columnIdentifiers.removeAllElements();

		fireTableStructureChanged();
	}

}
