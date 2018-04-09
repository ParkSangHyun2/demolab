package step4_1.store.io.util;

public class SqlCloseUtil {
	//
	public static void closeSql(AutoCloseable... autoCloseables) {
		//
		try {
			for (AutoCloseable autoCloseable : autoCloseables) {

				autoCloseable.close();
			}
		} catch (Exception e) {
			//
			System.out.println("SQL Closer Exception --> " + e.getMessage());
		}
	}
}
