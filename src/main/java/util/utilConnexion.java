package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class utilConnexion {
	
	private static final String URL = "jdbc:mysql://localhost:3308/gestionstockV1?useSSL=false&characterEncoding=UTF-8";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Driver MySQL introuvable : vérifiez le jar dans WEB-INF/lib", e);
		}
	}

	public static Connection getConn() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

}
