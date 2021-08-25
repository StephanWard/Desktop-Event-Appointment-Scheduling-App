package StephanWardSchedulingApp.controller;

import javafx.event.ActionEvent;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.*;
import java.net.URL;
import java.time.*;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import StephanWardSchedulingApp.dbAccess.DBAppointments;
import StephanWardSchedulingApp.dbAccess.DBPassword;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import StephanWardSchedulingApp.model.Appointments;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * The login form controller class
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class LoginFormController implements Initializable {
    //Calls the FXML Initializer to load the scene data information
    @FXML private TextField usernameInput;
    @FXML private PasswordField passwordInput;
    @FXML private Label zoneIdLabel;
    @FXML private Label username;
    @FXML private Label password;
    @FXML private Button loginButton;
    @FXML private Label loginLabel;
    //Applies the UTC formatter for the database
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a z");
    //Time difference is directed
    public static int timeDifference;
    public static int startTime = 8;
    public static int endTime = 22;
    //Alert system
    Alert alert;
    ResourceBundle rb;

    /**
     * Determines user time zone upon loading to ensure it's from 8AM-10PM EST and system language settings (french).
     * @param url The url relative path location for the root object
     * @param resourceBundle The localized root resource bundle for the relative object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //The Default system timezone
        ZoneId currentZoneId = ZoneId.of(TimeZone.getDefault().getID());
        //The User's system time zone
        ZonedDateTime currentTimeZone = ZonedDateTime.of(LocalDateTime.now().toLocalDate(), LocalDateTime.now().toLocalTime(), currentZoneId);
        //This converts the current time zone to UTC
        Instant currentToUTC = currentTimeZone.toInstant();
        //The converts the current system time zone to EST
        ZonedDateTime utcToEST = currentTimeZone.withZoneSameInstant(ZoneId.of("America/New_York"));
        //Converts the UTC time zone to current time
        ZonedDateTime utcToLocal = currentToUTC.atZone(currentZoneId);
        // Creates the time zone offsets relative to ETC
        timeDifference = utcToLocal.getHour() - utcToEST.getHour();
        startTime += timeDifference;
        endTime += timeDifference;
        if (timeDifference > 2) {
            endTime -= 24;
        } else if (timeDifference < -8) {
            startTime += 24;
        }

        // Refers to resource bundle for the language settings to the labels
        if (Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("fr")) {
            try {
                //Calls the utility resource bundle for appointments
                rb = ResourceBundle.getBundle("StephanWardSchedulingApp/utils/Appts", Locale.getDefault());
                //Updates username
                username.setText(rb.getString("username"));
                //Updates password
                password.setText(rb.getString("password"));
                //Label for the login
                loginLabel.setText(rb.getString("login"));
                //String assigned to login button
                loginButton.setText(rb.getString("login"));
                zoneIdLabel.setText(ZoneId.systemDefault().getId());

            } catch (MissingResourceException e) {
                e.printStackTrace();
            }
        } else {
            try {
                //Sets the locale
                Locale.setDefault(new Locale("en"));
                //Receives the resource bundle
                rb = ResourceBundle.getBundle("StephanWardSchedulingApp/utils/Appts", Locale.getDefault());
                //Sets the username
                username.setText(rb.getString("username"));
                //Sets the password
                password.setText(rb.getString("password"));
                //Sets the login label
                loginLabel.setText(rb.getString("login"));
                //Sets the login button
                loginButton.setText(rb.getString("login"));
                //Sets the zone ID label
                zoneIdLabel.setText(ZoneId.systemDefault().getId());
            } catch (MissingResourceException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks for matching username and password, while logging attempt into login_activity txt file for record keeping.
     * @param actionEvent Sets next scene
     * @throws IOException A error for incorrect scene operation.
     */
    public void loginButton(ActionEvent actionEvent) throws IOException {
        String user = usernameInput.getText();
        String pass = passwordInput.getText();
        boolean loginSuccess = DBPassword.getPassword(user, pass);
        //Outputs information to the login_activity text
        String file = "login_activity.txt";
        PrintWriter txtFile = null;
        ObservableList<Appointments> appointments = DBAppointments.getEveryAppointments();
        List<Appointments> tempList = new ArrayList<>();
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Upcoming Appointment Alert");
        alert.setHeaderText("Upcoming Appointment Alert");
        // Scans for any upcoming appointments based upon local time
        for (Appointments a : appointments) {
            long diff = ChronoUnit.MINUTES.between(LocalDateTime.now().toLocalTime(),
                    a.getStart().toLocalDateTime().toLocalTime());
            //Gets users information the matches upcoming appointment if within 15 minutes.
            if (a.getUserName().equals(user) && diff > 0 && diff <= 15) {
                tempList.add(a);
            }
        }
        if (tempList.isEmpty()) {
            alert.setContentText(user.toUpperCase() + " has no upcoming appointments.");
        } else { // print last upcoming appointment in list
            alert.setContentText(user.toUpperCase() + " does have a upcoming appointment\nAppointment ID: " +
                    tempList.get(tempList.size() - 1).getAppointmentId() +
                    " At: " + dateTimeFormatter.format(tempList.get(tempList.size() - 1).getStart().toLocalDateTime()));
        }

        if (loginSuccess) {
            // The valid username credentials are timestamped and stored in the login_activity text file.
            try {
                txtFile = new PrintWriter(new FileWriter(file, true));
                //Displays information into the text file
                txtFile.println("SUCCESS -> User: " + user + " At: " + Date.from(Instant.now()));
                txtFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Alert is displayed
            alert.showAndWait();
            //Notifies user of successful log in
            System.out.println("User is logged in.");
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("/StephanWardSchedulingApp/view/ViewAppointments.fxml"));
            Scene mainScene = new Scene(scene);
            stage.setScene(mainScene);
            stage.centerOnScreen();
            stage.show();

        } else {
            // The invalid username credentials are timestamped and stored in the login_activity text file
            try {
                txtFile = new PrintWriter(new FileWriter(file, true));
                txtFile.println("FAILURE -> User: " + user + " At: " + Date.from(Instant.now()));
                txtFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Displays an alert for a invalid login attempt
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("title"));
            alert.setHeaderText(rb.getString("header"));
            alert.setContentText(rb.getString("content"));
            alert.showAndWait();
        }
    }
}
