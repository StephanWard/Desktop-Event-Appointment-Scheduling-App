package StephanWardSchedulingApp.utils;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The database connection manager with auto-connection enabled.
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class DBConnection {
    //Protocol jdbc string
    private static final String protocol = "jdbc:";
    //Creates the vendorName string for the mysql
    private static final String vendorName = "mysql:";
    //URL locations of the WGU database
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    //Name of the database
    private static final String dbName = "WJ07zY9";
    //Auto reconnection string
    private static final String autoReconnect = "?autoReconnect=true";
    //Final jdbcURL string to connect to the database
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName + autoReconnect;
    //Database connection drive that was used
    private static final String mySQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;
    //Personalized username
    private static final String username = "U07zY9";
    //Personalized password
    private static final String password = "53689179785";

    /**
     * The driver manager connects the application to the database.
     * @return The Main.Java connection object that is used.
     */
    public static Connection startConnection() {
        //Database attempts to connect the driver to the application
        try {
            Class.forName(mySQLJDBCDriver);
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Database successfully connected to the application");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        //Returns the connection to the application
        return connection;
    }

    /**
     * The application requires database queries that will call and return database connection
     * @return Database connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Properly closes the database connection
     */
    public static void closeConnection() {
        try {
            //Closes the database connection
            connection.close();
            //System print out of a successfully closed database connection
            System.out.println("Database connection was successfully closed");
            //This catch error does not do anything.
        } catch (Exception e) {
        }
    }
}
