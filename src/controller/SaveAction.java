package controller;

import model.InfResurs;
import org.json.JSONObject;
import sun.net.www.protocol.http.HttpURLConnection;
import util.Constants;
import view.MainWindow;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class SaveAction implements ActionListener {
	Statement sql = null;
	ResultSet myRs = null;

	public static Set<String> getPrimaryKeyColumnsForTable(Connection connection, String tableName) throws SQLException {
		try (ResultSet pkColumns = connection.getMetaData().getPrimaryKeys(null, null, tableName)) {
			SortedSet<String> pkColumnSet = new TreeSet<>();
			while (pkColumns.next()) {
				String pkColumnName = pkColumns.getString("COLUMN_NAME");
				Integer pkPosition = pkColumns.getInt("KEY_SEQ");

				pkColumnSet.add(pkColumnName);
			}
			return pkColumnSet;
		}
	}

	public static Set<String> getColumnsForTable(Connection connection, String tableName) throws SQLException {
		try (ResultSet pkColumns = connection.getMetaData().getColumns(null, null, null, tableName)) {
			SortedSet<String> pkColumnSet = new TreeSet<>();
			while (pkColumns.next()) {
				String pkColumnName = pkColumns.getString("COLUMN_NAME");
				Integer pkPosition = pkColumns.getInt("KEY_SEQ");
				pkColumnSet.add(pkColumnName);
			}
			return pkColumnSet;
		}
	}

	// private static String tableName = null;
	public void actionPerformed(ActionEvent e) {

//		MainWindow.getMainWindow().getMiddle().getTableModel().addRow(new Object[]{});
		int row = MainWindow.getMainWindow().getMiddle().getTable().getSelectedRow();

		try {
			myRs = MainWindow.getDatabaseMetaData().getTables(null, null, null, null);
			sql = MainWindow.getMyConn().createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		if (row == -1) {
			JOptionPane.showMessageDialog(MainWindow.getMainWindow(), "Morate izabrati zapis u tabeli!", "Upozorenje",
					JOptionPane.WARNING_MESSAGE);
		} else {
			// String Table_click =
			// (MainWindow.getMainWindow().getMiddle().getTable().getModel().getValueAt(row,
			// 0).toString());

			DefaultMutableTreeNode node = (DefaultMutableTreeNode) MainWindow.getMainWindow().getMiddle().getTree()
					.getLastSelectedPathComponent();
			if (node == null || MainWindow.getTableName() == null)
				return;

			InfResurs infResurs = (InfResurs) node.getUserObject();

			try {


				int column = 0;
				int row1 = MainWindow.getMainWindow().getMiddle().getTable().getSelectedRow();
//					for (int i = 0; i < MainWindow.getMainWindow().getMiddle().getTable().getColumnCount(); i++) {
//						System.out.println(MainWindow.getMainWindow().getMiddle().getTable().getModel().getColumnName(i));
//						if (MainWindow.getMainWindow().getMiddle().getTable().getModel().getColumnName(i).equals(rs.getString("COLUMN_NAME"))) {
//							column = i;
//							break;
//						}
//					}
				getPrimaryKeyColumnsForTable(MainWindow.getMyConn(), MainWindow.getTableName());

				Map<String, String> mapaKljuceva = new HashMap<>();
				Map<String, String> mapaVrednosti = new HashMap<>();
				// 1.onaj kod za vracanje primarnih kljuceva ide ovde
				// 2.prolzaim kroz sve kolone iz reda

				for (String s : getPrimaryKeyColumnsForTable(MainWindow.getMyConn(), MainWindow.getTableName())) {
					for (int i = 0; i < MainWindow.getMainWindow().getMiddle().getTable().getColumnCount(); i++) {
						//System.out.println(MainWindow.getMainWindow().getMiddle().getTable().getModel().getColumnName(i));
						if (MainWindow.getMainWindow().getMiddle().getTable().getModel().getColumnName(i).equals(s)) {
							mapaKljuceva.put(s, MainWindow.getMainWindow().getMiddle().getTable().getModel()
									.getValueAt(row1, i).toString());
						}
					}
				}
				System.out.println(MainWindow.getMainWindow().getMiddle().getTable().getColumnCount());
				for (int i = 0; i < MainWindow.getMainWindow().getMiddle().getTable().getColumnCount(); i++) {
					if (MainWindow.getMainWindow().getMiddle().getTable().getModel().getValueAt(row1, i) != null)
						mapaVrednosti.put(MainWindow.getMainWindow().getMiddle().getTable().getModel()
								.getColumnName(i), MainWindow.getMainWindow().getMiddle().getTable().getModel()
								.getValueAt(row1, i).toString());
					else
						mapaVrednosti.put(MainWindow.getMainWindow().getMiddle().getTable().getModel()
								.getColumnName(i), null);

					System.out.println(MainWindow.getMainWindow().getMiddle().getTable().getModel().getValueAt(row1, i));
				}

				JSONObject ceo = new JSONObject();
				ceo.put("List of keys", mapaKljuceva);
				ceo.put("Rows", mapaVrednosti);

				URL url;
				HttpURLConnection con;
				System.out.println("CRUD TESKI");
				try {

					url = new URL("http://localhost:8080/api/v1/teski/send?tableName=" + MainWindow.getTableName());
					System.out.println(url);
					con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					con.setRequestProperty("Authorization", Constants.TOKEN);
					con.setDoOutput(true);

					if (ceo != null) {
						System.out.println("body");
						con.setRequestProperty("Content-Type", "application/json; utf-8");

						OutputStream os = con.getOutputStream();
						OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
						osw.write(ceo.toString());

						osw.flush();
						osw.close();
						os.close();
					}

					con.connect();
					int status = con.getResponseCode();

					System.out.println(status);

					if (status == HttpURLConnection.HTTP_OK) {
						StringBuffer content = null;

						BufferedReader in = new BufferedReader(
								new InputStreamReader(con.getInputStream()));
						String inputLine;
						content = new StringBuffer();
						while ((inputLine = in.readLine()) != null) {
							content.append(inputLine);
						}
						in.close();
						con.disconnect();
						//System.out.println(pkJson);
						String header = con.getHeaderField("Authorization");
						System.out.println("OVDEEE --- > " + content);
						System.out.println(header);
//						return new ResponseEntity(pkJson, HttpStatus.OK);
					}
				} catch (MalformedURLException malformedURLException) {
					malformedURLException.printStackTrace();
				} catch (ProtocolException protocolException) {
					protocolException.printStackTrace();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}


//					String vrednost = MainWindow.getMainWindow().getMiddle().getTable().getModel()
//							.getValueAt(row1, column).toString();
				//pozivacu ovde sercis za delete

//					sql.executeUpdate("DELETE FROM " + MainWindow.getTableName() + " WHERE " + getPrimaryKeyColumnsForTable(MainWindow.getMyConn(),MainWindow.getTableName());


			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		}
	}
}
