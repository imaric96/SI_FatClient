package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import actions.ReadDB;
import controller.SubmitAction;
import util.CreateMetaSchema;

public class LoginForm extends JFrame {

	private static LoginForm instance = null;
	JSONObject rootJson;
	JTextField ipText;
	JTextField portText;
	JTextField db_text;
	JTextField userName_text;
	JPasswordField password_text;

	public LoginForm() {

		ReadDB rdb = new ReadDB();
		Connection myConn = null;

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();

		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 700);
		setResizable(false);

		int xPos = (dim.width / 2) - (getWidth() / 2);
		int yPos = (dim.height / 2) - (getHeight() / 2);
		setLocation(xPos, yPos);

		JLabel ip = new JLabel();
		ip.setText("IP :");
		ipText = new JTextField();

		JLabel port = new JLabel();
		port.setText("Port :");
		portText = new JTextField();

		JLabel db_label = new JLabel();
		db_label.setText("Database :");
		db_text = new JTextField();

		JLabel user_label = new JLabel();
		user_label.setText("Username :");
		userName_text = new JTextField();

		JLabel password_label = new JLabel();
		password_label.setText("Password :");
		password_text = new JPasswordField();

		// Submit
		rootJson = new JSONObject();
		JButton submit = new JButton("LogIn");

		JPanel panel = new JPanel(new GridLayout(3, 1));

		panel.add(db_label);
		panel.add(db_text);
		panel.add(ip);
		panel.add(ipText);
		panel.add(user_label);
		panel.add(userName_text);
		panel.add(password_label);
		panel.add(password_text);
		panel.add(port);
		panel.add(portText);

		JLabel message = new JLabel();
		panel.add(message);
		panel.add(submit);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(panel, BorderLayout.CENTER);
		setTitle("Connection Settings");
		setSize(400, 150);

		try {
			if (myConn == null) {

				myConn = DriverManager.getConnection(
						"jdbc:mysql://" + rdb.getIp() + ":" + rdb.getPort() + "/" + rdb.getDataBase(), rdb.getUser(),
						rdb.getPass());

				CreateMetaSchema create = new CreateMetaSchema(myConn);
				myConn.close();
				MainWindow.getMainWindow();
				setVisible(false);
				dispose();

			}
		} catch (SQLException e) {
			setVisible(true);
		}

		submit.addActionListener(new SubmitAction());

	}

	public static LoginForm getLoginForm() {
		if (instance == null) {
			instance = new LoginForm();
		}

		return instance;
	}

	public JSONObject getRootJson() {
		return rootJson;
	}

	public void setRootJson(JSONObject rootJson) {
		this.rootJson = rootJson;
	}

	public JTextField getIpText() {
		return ipText;
	}

	public void setIpText(JTextField ipText) {
		this.ipText = ipText;
	}

	public JTextField getPortText() {
		return portText;
	}

	public void setPortText(JTextField portText) {
		this.portText = portText;
	}

	public JTextField getDb_text() {
		return db_text;
	}

	public void setDb_text(JTextField db_text) {
		this.db_text = db_text;
	}

	public JTextField getUserName_text() {
		return userName_text;
	}

	public void setUserName_text(JTextField userName_text) {
		this.userName_text = userName_text;
	}

	public JPasswordField getPassword_text() {
		return password_text;
	}

	public void setPassword_text(JPasswordField password_text) {
		this.password_text = password_text;
	}

}
