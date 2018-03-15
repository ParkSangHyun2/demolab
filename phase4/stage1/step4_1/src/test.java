import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class test {
	private static Connection connection;
	private static PreparedStatement state;
	
	public static void main(String[] args) throws SQLException {
		//
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/namoosori","root","");
		state = connection.prepareStatement("INSERT INTO BOARD VALUES(?,?,?,?)");
		state.setString(1, "good");
		state.setString(2, "for");
		state.setString(3, "you");
		state.setLong(4, 9944);
		state.executeUpdate();
	}
}
