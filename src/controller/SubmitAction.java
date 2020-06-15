package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import view.LoginForm;
import view.MainWindow;

public class SubmitAction implements ActionListener {

	public SubmitAction() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LoginForm.getLoginForm().getRootJson().put("Username", LoginForm.getLoginForm().getUserName_text().getText());
		LoginForm.getLoginForm().getRootJson().put("Password", LoginForm.getLoginForm().getPassword_text().getText());
		LoginForm.getLoginForm().getRootJson().put("DataBase", LoginForm.getLoginForm().getDb_text().getText());
		LoginForm.getLoginForm().getRootJson().put("IP", LoginForm.getLoginForm().getIpText().getText());
		LoginForm.getLoginForm().getRootJson().put("Port", LoginForm.getLoginForm().getPortText().getText());

		String s = LoginForm.getLoginForm().getRootJson().toString();
		try {
			FileWriter myWriter = new FileWriter("schema/login.json");
			myWriter.write(s);
			myWriter.close();
			System.out.println("Successfully wrote to the file schema/login.json.");
		} catch (IOException e2) {
			System.out.println("An error occurred.");
			e2.printStackTrace();
		}

		MainWindow.getMainWindow();

		LoginForm.getLoginForm().setVisible(false); // you can't see me!
		LoginForm.getLoginForm().dispose(); // Destroy the JFrame object

	}

}
