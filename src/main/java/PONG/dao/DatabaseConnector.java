package PONG.dao;
//---------------------------------------
//	IMPORTS
//---------------------------------------
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//--------------------------------------------------
//	CLASS DatabaseConnector
//--------------------------------------------------
/**
 * This class models a connection to the database.
 */
public class DatabaseConnector {
    //---------------------------------------
    //	Fields
    //---------------------------------------
    private static Connection connection;
    //---------------------------------------
    //	GET METHODS
    //---------------------------------------
    /**
     * Gets the connection to the database. <br>
     * If a connection to the database does not exist OR is closed,
     * a new connection is made.
     * @return A connection to the database.
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            createConnection();
        }
        return connection;
    }
    //---------------------------------------
    //	EXTRA METHODS
    //---------------------------------------
    /**
     * Creates a connection to the SQL database.
     */
    private static void createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/assignment";
        String username = "root";
        String password = "";
        connection = DriverManager.getConnection(url, username, password);
    }
}
