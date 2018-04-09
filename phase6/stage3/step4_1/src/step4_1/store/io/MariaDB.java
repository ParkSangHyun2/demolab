package step4_1.store.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MariaDB {
	//
	private static Connection connection;
	private static PreparedStatement state;
	private final static String DATABASE_NAME = "NAMOOSORI_PHASE4";
	private final static String USER_NAME = "root";
	private final static String PASSWORD = "";

	public MariaDB() {
		//
	}
	
	public static PreparedStatement runQuery(String query) {
		try {
			connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/" + DATABASE_NAME, USER_NAME, PASSWORD);
			state = connection.prepareStatement(query);
		} catch (SQLException e) {
			//
			System.out.println("Maria_db RunQuery Exception---> " + e.getMessage());
		}
		return state;
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	public static void closeQuery() {
		//
		try {
			connection.close();
		} catch (SQLException e) {
			//
			System.out.println("Close_db --->" + e.getMessage() );
		}
	}
	
}
