package util;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import model.Atribut;
import model.Entitet;
import model.PackEnt;
import model.Paket;
import model.Skladiste;

public class TreeUtil {

	public static CustomTree createTree(Skladiste db) {

		DefaultMutableTreeNode root = new DefaultMutableTreeNode(db);
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		CustomTree tree = new CustomTree(treeModel);

		// Popunjavanje stabla
		populateTree(root, db.getDeca());

		return tree;
	}

	private static void populateTree(DefaultMutableTreeNode node, ArrayList<PackEnt> packEnts) {
		for (PackEnt packEnt : packEnts) {
			DefaultMutableTreeNode child = new DefaultMutableTreeNode(packEnt);
			node.add(child);
			if (packEnt instanceof Paket) {
				populateTree(child, ((Paket) packEnt).getDeca());
			} else if (packEnt instanceof Entitet) {
				Entitet entitet = (Entitet) packEnt;

				for (Atribut atribut : entitet.getAtributi()) {
					child.add(new DefaultMutableTreeNode(atribut));
				}
			}
		}
	}
}