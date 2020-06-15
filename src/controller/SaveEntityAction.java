package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import events.EntityEvent;
import model.Entitet;
import view.MainWindow;

public class SaveEntityAction extends AbstractAction {

	public SaveEntityAction(final String name) {
		this.putValue(Action.NAME, name);
	}

	@Override
	public void actionPerformed(final ActionEvent actionEvent) {

		Entitet entitet = new Entitet();
		entitet.setKey("RANDOM_ENTITET");
		entitet.setNaziv("NEKI ENTITET");

		// save into db and whatever...

		entitet.addObserver(MainWindow.getMainWindow().getMiddle().getTree());
		entitet.notifyObservers(EntityEvent.SAVE);
	}
}
