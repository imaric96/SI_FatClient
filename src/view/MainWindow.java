package view;

import actions.Cancel;
import actions.ReadDB;
import model.Entitet;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainWindow extends JFrame {

	private static MainWindow instance = null;
	MiddlePane middle = null;
	private static Connection myConn = null;
	private static DatabaseMetaData databaseMetaData = null;
	private static String tableName = "";
	private static Entitet entitet;

	private MainWindow() {
		ReadDB rdb = new ReadDB();
		try {
			if (myConn == null) {

				myConn = DriverManager.getConnection(
						"jdbc:mysql://" + rdb.getIp() + ":" + rdb.getPort() + "/" + rdb.getDataBase(), rdb.getUser(),
						rdb.getPass());

				databaseMetaData = myConn.getMetaData();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println();

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();

		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 700);
		setResizable(false);

		int xPos = (dim.width / 2) - (getWidth() / 2);
		int yPos = (dim.height / 2) - (getHeight() / 2);
		setLocation(xPos, yPos);

		MenuBar mb = new MenuBar();
		add(mb, BorderLayout.NORTH);

		middle = new MiddlePane();
		add(middle, BorderLayout.CENTER);

		addWindowListener(new Cancel());

		setVisible(true);
	}

	public MiddlePane getMiddle() {
		return middle;
	}

	public static MainWindow getMainWindow() {
		if (instance == null) {
			instance = new MainWindow();
		}

		return instance;
	}

	public static Connection getMyConn() {
		return myConn;
	}

	public static void setMyConn(Connection myConn) {
		MainWindow.myConn = myConn;
	}

	public static DatabaseMetaData getDatabaseMetaData() {
		return databaseMetaData;
	}

	public static void setDatabaseMetaData(DatabaseMetaData databaseMetaData) {
		MainWindow.databaseMetaData = databaseMetaData;
	}

	public static String getTableName() {
		return tableName;
	}

	public static void setTableName(String tableName) {
		MainWindow.tableName = tableName;
	}

	public static Entitet getEntitet() {
		return entitet;
	}

	public static void setEntitet(Entitet entitet) {
		MainWindow.entitet = entitet;
	}

}