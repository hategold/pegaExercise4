package yt.item4;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnCtrl {

	private static Connection conn;

	public static Connection getConnection() {
		if (conn != null)
			return conn;
		Properties properties = new Properties();
		try {
			properties.load(DbConnCtrl.class.getClassLoader().getResourceAsStream("/shoesDb.properties"));

			String driver = properties.getProperty("driver", "com.mysql.jdbc.Driver");
			String host = properties.getProperty("host");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");

			Class.forName(driver);

			conn = DriverManager.getConnection(host, user, password);

		} catch (SQLException e) {
			System.out.println("SQLException :" + e.toString());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException :" + e.toString());
		} catch (IOException e) {
			System.out.println("IOException :" + e.toString());
		}
		return conn;
	}

}
