package actions;

import model.TableModel;

public class ReadFromTable {
	Object[][] object;

	public Object[][] ReadFromTable(TableModel tableModel) {
		object = new Object[tableModel.getColumnCount()][tableModel.getColumnCount()];
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			for (int j = 0; j < tableModel.getColumnCount(); j++) {
				object[i][j] = tableModel.getValueAt(i, j);
				System.out.println(object[i][j]);
			}
		}
		return object;
	}
}
