package dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionWrapper {
	public static java.sql.Connection conn() {
		Connection connection = null;
		final String filePath = "banco/aula.sqlite";

		try {
			File f = new File(filePath);
			if (f.exists()) {
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

}
