package util;

import events.EntityEvent;
import model.Atribut;
import model.Entitet;
import model.InfResurs;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.net.www.protocol.http.HttpURLConnection;
import view.MainWindow;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class CustomTree extends JTree implements Observer {

	private static DefaultMutableTreeNode SelectedNode = null;
	private static ResultSet myRs = null;

	public CustomTree(TreeModel treeModel) {
		super(treeModel);

		// Primer reagovanja na selektovanje cvora u stablu
		this.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {

				try {

					myRs = MainWindow.getDatabaseMetaData().getTables(null, null, null, null);

					DefaultMutableTreeNode node = (DefaultMutableTreeNode) MainWindow.getMainWindow().getMiddle()
							.getTree().getLastSelectedPathComponent();

					if (node == null || node.equals(SelectedNode))
						return;

					InfResurs infResurs = (InfResurs) node.getUserObject();

					if (infResurs instanceof Entitet) {

						SelectedNode = node;
						Entitet ed = (Entitet) infResurs;

						MainWindow.setTableName(infResurs.getKey());
						MainWindow.setEntitet((Entitet) infResurs);
//--------------------------ime tabele
						System.out.println("Entitet: " + infResurs.getKey());

						System.out.println("Atributi: " + ed.getAtributi());


						System.out.println("------>" + ed.getAtributi().get(0).getKey());

						String SWE = "{" + "\n" + "\"" + "Tabela" + "\"" + " : " + infResurs.getKey() + "\n";

						MainWindow.getMainWindow().getMiddle().getTableModel().removeAllColumn();

						for (int i = MainWindow.getMainWindow().getMiddle().getTableModel().getRowCount()
								- 1; i >= 0; i--) {
							MainWindow.getMainWindow().getMiddle().getTableModel().removeRow(i);
						}

						for (Atribut currentAtr : ed.getAtributi()) {
							MainWindow.getMainWindow().getMiddle().getTableModel().addColumn(currentAtr.getKey());
						}

						ResultSet columns = MainWindow.getDatabaseMetaData().getColumns(null, null, infResurs.getKey(),
								null);
						int br = 0;

						while (columns.next()) {

							JTextField textField = new JTextField();
							limitCharacters(textField, columns.getInt("COLUMN_SIZE"));
							MainWindow.getMainWindow().getMiddle().getTable().getColumnModel().getColumn(br++)
									.setCellEditor(new DefaultCellEditor(textField));

						}

						Statement sql = MainWindow.getMyConn().createStatement();

						ResultSet resultSet = sql.executeQuery("SELECT * FROM " + MainWindow.getMyConn().getCatalog()
								+ "." + infResurs.getKey() + ";");


						ArrayList<String> a = new ArrayList<String>();

						//KOD ZA KREIRANJE JSONA JEBENOG KOJI MORA DA POPRAVIM

						getPrimaryKeyColumnsForTable(MainWindow.getMyConn(), infResurs.getKey());


						JSONObject ceo = new JSONObject();
						ceo.put("List of keys", getPrimaryKeyColumnsForTable(MainWindow.getMyConn(), infResurs.getKey()));
						JSONArray nizJsona = new JSONArray();
						while (resultSet.next()) {
							JSONObject rowDetails = new JSONObject();
							for (int i = 0; i < ed.getAtributi().size(); i++) {
								a.add(resultSet.getString(ed.getAtributi().get(i).getKey()));
//								if (i == ed.getAtributi().size() - 1)
//									rowDetails.put("Tabela",infResurs.getKey());

								rowDetails.put(ed.getAtributi().get(i).getKey(), a.get(i));


							}
							nizJsona.put(rowDetails);
							Object[] array = a.toArray(new Object[a.size()]);

							MainWindow.getMainWindow().getMiddle().getTableModel().addRow(array);
							a.clear();
						}

						ceo.put("Rows", nizJsona);
						System.out.println(ceo);

						StringBuffer content = null;
						URL url;
						HttpURLConnection con;

						//ovo mi treba

						url = new URL("http://localhost:8080/api/v1/teski/send" + "/" + infResurs.getKey());
						con = (HttpURLConnection) url.openConnection();
						con.setRequestMethod("POST");
						con.setDoOutput(true);

						if (nizJsona != null) {
							con.setRequestProperty("Content-Type", "application/json; utf-8");

							OutputStream os = con.getOutputStream();
							OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);

							osw.write(String.valueOf(nizJsona));

							osw.flush();
							osw.close();
							os.close();
						}
//						if (nizJsona != null) {
//							con.setRequestProperty ("Authorization",  "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpdmFuIiwiU0FSQUROSUsiOmZhbHNlLCJLT1JJU05JSyI6ZmFsc2UsIlBST1ZJREVSIjpmYWxzZSwiSUQiOjMsIkFETUlOIjp0cnVlLCJFTUFJTCI6ImVtYWlsQGdtYWlsLmNvbSIsImV4cCI6MTU4NzkyNTI2NCwiUFJPVklERVJfSUQiOjJ9.FJVqCD9SM9WloSceYbG9YmlQJdlc30HFYHYBv3skHYpyaEUp_Kzi-BcicJ2eVgo1ZukJGLYHAPxfOoioXsaXDA");
//
//
//							OutputStream os = con.getOutputStream();
//							OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
//							osw.write(String.valueOf(nizJsona));
//
//							osw.flush();
//							osw.close();
//							os.close();
//						}

						con.connect();
						int status = con.getResponseCode();
						System.out.println(status);
						if (status == HttpURLConnection.HTTP_OK) {
							System.out.println(readFullyAsString(con.getInputStream(), "UTF-8"));
						}

					}
					//D:\DRAGOMIR\Desktop\Teski klient\schema.json
//						try (FileWriter file = new FileWriter("D:\\DRAGOMIR\\Desktop\\Teski klient\\schema.json")) {
//
//							file.write(nizJsona.totoJSONString());
//							file.flush();
//
//						} catch (IOException er) {
//							er.printStackTrace();
//						} catch (IOException ioException) {
//							ioException.printStackTrace();
//						}


				} catch (IOException ioException) {
					ioException.printStackTrace();
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}
//					else
//						MainWindow.setTableName(null);

			}


		});
	}

	public static Set<String> getPrimaryKeyColumnsForTable(Connection connection, String tableName) throws SQLException {
		try (ResultSet pkColumns = connection.getMetaData().getPrimaryKeys(null, null, tableName)) {
			SortedSet<String> pkColumnSet = new TreeSet<>();
			while (pkColumns.next()) {
				String pkColumnName = pkColumns.getString("COLUMN_NAME");
				Integer pkPosition = pkColumns.getInt("KEY_SEQ");
				System.out.println("" + pkColumnName + " is the " + pkPosition + ". column of the primary key of the table " + tableName);
				pkColumnSet.add(pkColumnName);
			}
			return pkColumnSet;
		}
	}

	public String readFullyAsString(InputStream inputStream, String encoding) throws IOException {
		return readFully(inputStream).toString(encoding);
	}

	private ByteArrayOutputStream readFully(InputStream inputStream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(buffer)) != -1) {
			baos.write(buffer, 0, length);
		}
		return baos;
	}


	private void limitCharacters(JTextField textField, final int limit) {
		PlainDocument document = (PlainDocument) textField.getDocument();

		document.setDocumentFilter(new DocumentFilter() {

			@Override
			public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;

				if (string.length() <= limit)
					super.replace(fb, offset, length, text, attrs);
			}

		});
	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof EntityEvent) {

			EntityEvent entityEvent = (EntityEvent) arg;

			if (entityEvent == EntityEvent.SAVE) {

				System.out.println("STH IS SAVED!!!");

				if (o instanceof Entitet) {
					Entitet entitet = (Entitet) o;

					System.out.println("Entity with name " + entitet.getNaziv() + " is saved!!!");
				} else {
					System.out.println("Ignore");
				}

			} else if (entityEvent == EntityEvent.DELETE) {
				System.out.println("STH IS DELETED!!!");
			} else if (entityEvent == EntityEvent.UPDATE) {
				System.out.println("STH IS UPDATED!!!");
			}
		} else {
			System.out.println("WTF");
		}
	}
}