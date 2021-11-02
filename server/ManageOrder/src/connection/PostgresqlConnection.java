package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresqlConnection {
	public static Connection getPostgresqlConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {}
		
		Properties properties = new Properties();
		properties.put("user", "postgres");
		properties.put("password", "1234");
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/manageorder", properties);
		return connection;
	}
}

