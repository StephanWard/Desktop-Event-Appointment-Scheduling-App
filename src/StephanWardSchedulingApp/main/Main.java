package StephanWardSchedulingApp.main;

import javafx.stage.Stage;
import StephanWardSchedulingApp.dbAccess.*;
import javafx.fxml.FXMLLoader;
import StephanWardSchedulingApp.model.*;
import javafx.application.Application;
import StephanWardSchedulingApp.utils.DBConnection;
import javafx.scene.Scene;
import javafx.scene.Parent;

/**
 * The main model class used for the extension of the application to the rest of the GUI
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class Main extends Application {

    /**
     * A parent root uses FXML loader to load the login screen
     * @param stage The stage parameter for the login screen.
     * @throws Exception Error when failing to load login screen.
     */
    @Override
    public void start(Stage stage) throws Exception {
        //public override method to change scene to the login form when called upon
        Parent root = FXMLLoader.load(getClass().getResource("/StephanWardSchedulingApp/view/LoginForm.fxml"));
        Scene loginForm = new Scene(root);
        stage.setScene(loginForm);
        stage.show();
    }

    /**
     * Main argument string method that starts the database connection. Then sets all countries, first level divisions,
     * users, contacts, and finally closes connection.
     * @param args The basis input.
     */
    public static void main(String[] args) {
        //Starts the database connection
        DBConnection.startConnection();
        Countries.setAllCountries(DBCountries.getAllCountries());
        FirstLevelDivisions.setAllDivisions(DBFirstLevelDivisions.getAllDivisions());
        Users.setAllUsers(DBUsers.getAllUsers());
        Contacts.setAllContacts(DBContacts.getAllContacts());
        launch(args);
        //Closes the database connection
        DBConnection.closeConnection();
    }
}
