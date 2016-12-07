package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static final String URL = "jdbc:mysql://localhost:3306/uploadfile?useSSL=false";
	private static final String USER = "root";
	private static final String PWD = "1011";
	private static Connection CONN = null;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			CONN = DriverManager.getConnection(URL, USER, PWD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getCONN() {
		return CONN;
	}
}
