package actions;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

public class Validate {

	public Validate(String s) {
		try {
	        ObjectMapper obj = new ObjectMapper();
	        obj.readTree(s);
	        JOptionPane.showMessageDialog(null, "JSON fajl je validan!", "Potvrda", JOptionPane.INFORMATION_MESSAGE);
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "JSON fajl nije pro�ao validaciju! Proverite sintaksu pa poku�ajte ponovo.", "Gre�ka", JOptionPane.ERROR_MESSAGE);
	    }
	}
}