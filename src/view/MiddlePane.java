package view;

import controller.AddAction;
import controller.DeleteAction;
import controller.SaveAction;
import model.Skladiste;
import model.TableModel;
import util.CustomTree;
import util.ReadJSON;
import util.TreeUtil;

import javax.swing.*;
import java.awt.*;

public class MiddlePane extends JPanel {

    private CustomTree tree = null;
    private TableModel tableModel = null;
    private JTable table = null;

    public MiddlePane() {
        setLayout(new BorderLayout());


        JPanel TablePane = new JPanel();
		TablePane.setLayout(new BorderLayout());
		JButton gore = new JButton("gore");

		tableModel = new TableModel();
		table = new JTable(tableModel);

		table.setBounds(30, 40, 200, 300);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);

		JScrollPane sp = new JScrollPane(table);

		TablePane.add(sp, BorderLayout.CENTER);

		JPanel donji = new JPanel();

		JButton save = new JButton("Save table");
		JButton add = new JButton("Add new row");
		JButton delete = new JButton("Delete record");

		donji.add(add);
		donji.add(save);
		donji.add(delete);

		JSplitPane SplitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, TablePane, donji);

		SplitV.setDividerLocation(300);

		Skladiste skladiste = ReadJSON.citanjejson();

		// Kreiranje i popunjavanje stabla
		tree = TreeUtil.createTree(skladiste);

		JScrollPane scrollPane = new JScrollPane(tree);
		add(scrollPane, BorderLayout.CENTER);

		JSplitPane SplitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, SplitV);
		SplitH.setDividerLocation(200);
		add(SplitH, BorderLayout.CENTER);

		delete.addActionListener(new DeleteAction());
		add.addActionListener(new AddAction(tableModel));
		save.addActionListener(new SaveAction());

	}

	public CustomTree getTree() {
		return tree;
	}

	public TableModel getTableModel() {
        return tableModel;
    }

	public void setTableModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

}
