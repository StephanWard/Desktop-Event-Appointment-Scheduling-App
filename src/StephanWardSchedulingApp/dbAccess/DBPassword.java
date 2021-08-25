package StephanWardSchedulingApp.dbAccess;

import StephanWardSchedulingApp.utils.DBConnection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The database password class that uses R queries for the database.
 * @author Stephan Ward
 * @since 07/06/2021
 */
public final class DBPassword {

    /**
     * The getter query for username and password match.
     * @param username The user's username.
     * @param password The user's password.
     * @return Boolean true if username and password match, or else false
     */
    public static boolean getPassword(String username, String password) {
        boolean answer = false;
        //A Try Catch method for querying the database fields to get results to be displayed
        try {
            String sql = "SELECT User_Name, Password FROM users WHERE User_Name = ? AND Password = ?;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            //Obtains the results from the database query
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                answer = true;
            }
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not match password error");
            e.printStackTrace();
        }
        //Returns the list of items
        return answer;
    }
}
