package StephanWardSchedulingApp.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import StephanWardSchedulingApp.dbAccess.DBReports;
import StephanWardSchedulingApp.model.Reports;

import java.io.IOException;
import java.net.URL;
import java.util.*;
//imports the static date time formatter from the log in controller
import static StephanWardSchedulingApp.controller.LoginFormController.dateTimeFormatter;

/**
 * The view reports controller class
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class ViewReportsController implements Initializable {
    //Calls the FXML Initializer to load the scene data information
    @FXML private Label reportLabel; // For the Main Label
    @FXML private Label reportLabel1; // For the month
    @FXML private Label reportLabel2; // For the appointment type
    @FXML private Label reportLabel3; // For the appointment count
    ObservableList<Reports> reportsList;

    /**
     * On load, no data loaded in display. Data loaded on buttons pressed within view.
     * @param url The root object resources url location or null
     * @param resourceBundle The root object resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Method override to initialize the root and resource bundle
    }

    /**
     * The appointment by customer reports by type and month, upon button press; via string builder.
     */
    public void customerAppointmentsReport() {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        reportLabel.setText("");
        reportLabel1.setText("");
        reportLabel2.setText("");
        reportLabel3.setText("");
        reportsList = DBReports.getCustomerAppointments();

        reportLabel.setText("Customer Appointments By Type and Month Report: \n\n");
        //Appends Month to the customer report
        sb1.append("Month:\n");
        for (Reports reports : reportsList) {
            sb1.append(reports.getMonthName()).append("\n");
        }
        reportLabel1.setText(sb1.toString());
        //Appends Type to the customer report
        sb2.append("Type:\n");
        for (Reports reports : reportsList) {
            sb2.append(reports.getType()).append("\n");
        }
        reportLabel2.setText(sb2.toString());
        //Appends total count to the customer report
        sb3.append("Count:\n");
        for (Reports reports : reportsList) {
            sb3.append(reports.getAppointmentCount()).append("\n");
        }
        reportLabel3.setText(sb3.toString());
    }

    /**
     * The contact schedules report, upon button press; via string builder.
     */
    public void contactScheduleReport() {
        reportLabel.setText("");
        reportLabel1.setText("");
        reportLabel2.setText("");
        reportLabel3.setText("");
        reportsList = DBReports.getContactSchedule();
        StringBuilder sb = new StringBuilder();
        sb.append("Appointments Schedule By Contact Report: \n");
        int id = 0;
        for (Reports r : reportsList) {
            if (r.getContactId() != id) {
                sb.append("\n" + r.getContactName().toUpperCase() + "\n");
                sb.append("Appt. ID: " + r.getAppointmentId() + " \t Title: " + r.getTitle() + " \t Type: " +
                        r.getType() + " \t Desc: " + r.getDescription() + " \t Start: " +
                        dateTimeFormatter.format(r.getStart().toLocalDateTime()) + " \t End: " +
                        dateTimeFormatter.format(r.getEnd().toLocalDateTime()) + " \t Customer ID: " +
                        r.getCustomerId() + "\n");
                id = r.getContactId();
            } else {
                sb.append("Appt. ID: " + r.getAppointmentId() + " \t Title: " + r.getTitle() + " \t Type: " +
                        r.getType() + " \t Desc: " + r.getDescription() + " \t Start: " +
                        dateTimeFormatter.format(r.getStart().toLocalDateTime()) + " \t End: " +
                        dateTimeFormatter.format(r.getEnd().toLocalDateTime()) + " \t Customer ID: " +
                        r.getCustomerId() + "\n");
            }
        }
        reportLabel.setText(sb.toString());
    }

    /**
     * The number of appointments per customers report, upon button press; via string builder.
     * The lambda operation is included here as each object is iterated and added to the reports list.
     */
    public void userAppointmentsReport() {
        reportLabel.setText("");
        reportLabel1.setText("");
        reportLabel2.setText("");
        reportLabel3.setText("");
        reportsList = DBReports.getUserAppointments();
        System.out.println(reportsList.toArray().length);
        StringBuilder sb = new StringBuilder();
        sb.append("Appointments Added By User Report:" + "\n");

        reportsList.forEach(r -> sb.append("\n" + r.getUserName().toUpperCase() + "\n" +
                r.getAppointmentCount() + " appointments scheduled for " + r.getCustomerCount() + " customers\n"));
        reportLabel.setText(sb.toString());
    }

    /**
     * Loads appointment view when button is pressed.
     * @param actionEvent Changes to view appointment scene.
     * @throws IOException Error for incorrect operation
     */
    public void viewAppointmentsButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/StephanWardSchedulingApp/view/ViewAppointments.fxml"));
        Scene mainScene = new Scene(scene);
        stage.setScene(mainScene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Loads customer view when button is pressed.
     * @param actionEvent Changes to view customers scene.
     * @throws IOException Error for incorrect operation
     */
    public void viewCustomersButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/StephanWardSchedulingApp/view/ViewCustomers.fxml"));
        Scene mainScene = new Scene(scene);
        stage.setScene(mainScene);
        stage.centerOnScreen();
        stage.show();
    }
}
