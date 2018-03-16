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
	
	public static PreparedStatement runQuery(String query, String...values) {
		try {
			connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/" + DATABASE_NAME, USER_NAME, PASSWORD);
			state = connection.prepareStatement(query);
			
			for(int i =1; i <= values.length; i++) {
				state.setString(i, values[i-1]);
			}
		} catch (SQLException e) {
			//
			System.out.println("Maria_db RunQuery Exception---> " + e.getMessage());
		}
		return state;
	}
	
	public static Connection getConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DATABASE_NAME, USER_NAME, PASSWORD);
			return connection;
		} catch (SQLException e) {
			//
			System.out.println("Maria_db getConnection Exception ---> " + e.getMessage());
		}
		return null;
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
