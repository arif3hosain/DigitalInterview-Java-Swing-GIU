package DBUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection connection = null;
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/digitalinterview";
    public static final String USERNAME = "dev_user";
    public static final String PASSWORD = "asdfsdf@123_";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public static void main(String[] args) {
        try {
            // Establishing connection
            connection = getConnection();
            System.out.println("Connected to MySQL database.");

            // Close the connection when done
            // connection.close();
            //System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.err.println("Error connecting to MySQL database: " + e.getMessage());
        }
    }
 
}
