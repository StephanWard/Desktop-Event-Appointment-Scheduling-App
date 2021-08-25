package StephanWardSchedulingApp.dbAccess;

import StephanWardSchedulingApp.model.Users;
import StephanWardSchedulingApp.utils.DBConnection;
import java.sql.ResultSet;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The database users class that uses a R query for the database
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class DBUsers {

    /**
     * The getter query for all users of the database.
     * @return The observable list for all database users without passwords.
     */
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> userList = FXCollections.observableArrayList();
        //A Try/While/Catch method for querying the database fields to get results to be displayed
        try {
            String sql = "SELECT User_ID, User_Name FROM users ORDER BY User_ID;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            //Obtains the results from the database query
            while (resultSet.next()) {
                int userId = resultSet.getInt("User_ID");
                String username = resultSet.getString("User_Name");
                Users user = new Users(userId, username);
                userList.add(user);
            }
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not receive database users.");
            e.printStackTrace();
        }
        //Returns the list of items
        return userList;
    }
}
