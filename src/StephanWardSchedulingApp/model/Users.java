package StephanWardSchedulingApp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The model used for the users class
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class Users {
    private String username;
    private int userId;
    //String to create the ObservableList for all Users as a Array
    private static ObservableList<Users> allUsers = FXCollections.observableArrayList();

    /**
     * Assigning Users constructor.
     * @param userId User ID.
     * @param username Username.
     */
    public Users(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    /**
     * Assigning getter for User ID.
     * @return User ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Assigning getter for username.
     * @return Username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * A request to get and return a list of all users
     * @return Observable list of all users.
     */
    public static ObservableList<Users> getAllUsers() {
        return allUsers;
    }

    /**
     * The database query that sets the observable list of all users from a query.
     * @param allUsers ObservableList from database query.
     */
    public static void setAllUsers(ObservableList<Users> allUsers) {
        Users.allUsers = allUsers;
        System.out.println("The query has successfully applied all users to the list");
    }

    /**
     * To display the user's name and ID together in the combo box, a override method that uses a to-string method with concatenation.
     * @return Concatenated username and user ID.
     */
    @Override
    public String toString() {
        return username + " [" + userId + "]";
    }
}
