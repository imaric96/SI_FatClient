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
	        JOptionPane.showMessageDialog(null, "JSON fajl nije prošao validaciju! Proverite sintaksu pa pokušajte ponovo.", "Greška", JOptionPane.ERROR_MESSAGE);
	    }
	}
}