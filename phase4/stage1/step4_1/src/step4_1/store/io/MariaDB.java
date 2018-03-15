package step4_1.store.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MariaDB {
	//
	private static Connection connection;
	private static PreparedStatement state;
	
	public MariaDB() {
		//
	}
	
	public static PreparedStatement runQuery(String query, String...values) {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/namoosori","root","");
			state = connection.prepareStatement(query);
			
			for(int i =1; i <= values.length; i++) {
				state.setString(i, values[i-1]);
			}
		} catch (SQLException e) {
			//
			System.out.println("Maria_db ---> " + e.getMessage());
		}
		return state;
	}
	
	public static Connection getConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/namoosori","root","");
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
