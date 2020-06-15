package util;

import java.io.File;
import java.util.Scanner;

import org.codehaus.jackson.map.ObjectMapper;

import model.Skladiste;

public class ReadJSON {
	public static Skladiste citanjejson() {
		ObjectMapper obj = new ObjectMapper();
		String str = "";
		try {
			Scanner sc = new Scanner(new File("schema/data.json"));
			while (sc.hasNext()) {
				str += " " + sc.next();
			}
			sc.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Skladiste skladiste = null;
		try {
			skladiste = obj.readValue(str, Skladiste.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(skladiste);
		return skladiste;
	}
}