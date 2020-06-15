package util;

import sun.net.www.protocol.http.HttpURLConnection;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;

public class CreateMetaSchema {

	public CreateMetaSchema(Connection myConn) {
		StringBuffer content;
		URL url;
		HttpURLConnection con;
		try {
			//url = new URL(apiUrl);
			url = new URL("http://localhost:8080/api/v1/teski/getDB");
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", Constants.TOKEN);


			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			System.out.println(content);


			FileWriter myWriter = new FileWriter("schema/data.json");
			myWriter.write(content.toString());
			myWriter.close();

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}
