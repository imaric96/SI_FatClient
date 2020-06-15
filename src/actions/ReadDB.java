package actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONObject;

public class ReadDB {
	String User;
	String pass;
	String port;
	String DataBase;
	String ip;

	public ReadDB() {

		String myJson = "";
		try {
			myJson = new Scanner(new File("schema/login.json")).useDelimiter("Z").next();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject myJsonobject = new JSONObject(myJson);

		User = myJsonobject.getString("Username");
		pass = myJsonobject.getString("Password");
		DataBase = myJsonobject.getString("DataBase");
		port = myJsonobject.getString("Port");
		ip = myJsonobject.getString("IP");

		System.out.println(myJsonobject);

	}

	public String getUser() {
		return User;
	}

	public void setUser(String user) {
		User = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDataBase() {
		return DataBase;
	}

	public void setDataBase(String dataBase) {
		DataBase = dataBase;
	}

}