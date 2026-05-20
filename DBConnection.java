import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection getConnection() {
		try {
			Class.forName("org.h2.Driver");
			return DriverManager.getConnection("jdbc:h2:./donationdb", "sa", "");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
