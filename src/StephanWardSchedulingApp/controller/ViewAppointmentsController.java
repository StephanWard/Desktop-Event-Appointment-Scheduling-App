package StephanWardSchedulingApp.controller;

import StephanWardSchedulingApp.dbAccess.DBAppointments;
import StephanWardSchedulingApp.dbAccess.DBCustomers;
import StephanWardSchedulingApp.model.Appointments;
import StephanWardSchedulingApp.model.Contacts;
import StephanWardSchedulingApp.model.Customers;
import StephanWardSchedulingApp.model.Users;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static StephanWardSchedulingApp.controller.LoginFormController.*;

/**
 * View Appointment Controller class
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class ViewAppointmentsController implements Initializable {
    //Calls the FXML Initializer to load the scene data information for appointments
    @FXML private TableView<Appointments> appointmentsTable;
    @FXML private TableColumn<Appointments, Integer> appointmentsId;
    @FXML private TableColumn<Appointments, String> appointmentsTitle;
    @FXML private TableColumn<Appointments, String> appointmentsDescription;
    @FXML private TableColumn<Appointments, String> appointmentsLocation;
    @FXML private TableColumn<Appointments, String> appointmentsContact;
    @FXML private TableColumn<Appointments, String> appointmentsType;
    @FXML private TableColumn<Appointments, String> appointmentsStart;
    @FXML private TableColumn<Appointments, String> appointmentsEnd;
    @FXML private TableColumn<Customers, String> appointmentsCustomerName;
    //Calls the FXML Initializer to load the scene data information for all, month, and weekly appointments
    @FXML private RadioButton viewAllAppointments;
    @FXML private RadioButton viewMonthAppointments;
    @FXML private RadioButton viewWeekAppointments;
    //Calls the FXML Initializer to load the scene data information for appointment information
    @FXML private TextField apptId;
    @FXML private TextField apptTitle;
    @FXML private TextField apptDescription;
    @FXML private TextField apptLocation;
    @FXML private ComboBox<Contacts> apptContact;
    @FXML private TextField apptType;
    @FXML private DatePicker apptStartDate;
    @FXML private ComboBox<LocalTime> apptStartTime;
    @FXML private DatePicker apptEndDate;
    @FXML private ComboBox<LocalTime> apptEndTime;
    @FXML private ComboBox<Customers> apptCustomer;
    @FXML private ComboBox<Users> apptUser;
    //Alert for the observable lists.
    Alert alert;
    ObservableList<Users> usersList = Users.getAllUsers();
    ObservableList<Contacts> contactsList = Contacts.getAllContacts();


    /**
     * The appointment table view is loaded, along with the combo box options for users, contacts, and customers;
     * utilizing two listening lambdas for the dateTimeFormatter for easy viewing, by setting the cell value. One lambda
     * is for the selection toggle. The other lambda is for the start and end time combo boxes to be within the 8:00-22:00 EST
     * office hours; while preventing the selection of time outside of office hours. Also, lambda added to the date pickers
     * to contain associated dates for a fully functional date picker.
     * @param url The root objects relative path or null
     * @param resourceBundle The localized root resource bundle or null
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Applies the appointment data to the appointment table
        appointmentsId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentsTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentsDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentsLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentsContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appointmentsType.setCellValueFactory(new PropertyValueFactory<>("type"));
        //Todo: Display was fixed here
        appointmentsStart.setCellValueFactory((TableColumn.CellDataFeatures<Appointments, String> t) ->
                new ReadOnlyObjectWrapper<>(t.getValue().getStart().format(dateTimeFormatter)));
        appointmentsEnd.setCellValueFactory((TableColumn.CellDataFeatures<Appointments, String> t) ->
                new ReadOnlyObjectWrapper<>(t.getValue().getEnd().format(dateTimeFormatter)));
        appointmentsCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        appointmentsTable.setItems(DBAppointments.getEveryAppointments());
        appointmentsTable.getSortOrder().add(appointmentsStart);
        ToggleGroup appointmentsTG = new ToggleGroup();
        this.viewAllAppointments.setToggleGroup(appointmentsTG);
        this.viewMonthAppointments.setToggleGroup(appointmentsTG);
        this.viewWeekAppointments.setToggleGroup(appointmentsTG);
        viewAllAppointments.setSelected(true);
        appointmentsTG.selectedToggleProperty().addListener((observableValue, toggle, t1) -> {
            if (t1.equals(viewAllAppointments)) {
                appointmentsTable.setItems(DBAppointments.getEveryAppointments());
                appointmentsTable.getSortOrder().add(appointmentsStart);
            } else if (t1.equals(viewMonthAppointments)) {
                appointmentsTable.setItems(DBAppointments.getAppointmentsByMonth(0));
                appointmentsTable.getSortOrder().add(appointmentsStart);
            } else if (t1.equals(viewWeekAppointments)) {
                appointmentsTable.setItems(DBAppointments.getAppointmentsByWeek(0));
                appointmentsTable.getSortOrder().add(appointmentsStart);
            }
        });
        Customers.setAllCustomers(DBCustomers.getAllCustomers());
        apptCustomer.setItems(Customers.getAllCustomers());
        apptUser.setItems(usersList);
        apptContact.setItems(contactsList);
        LocalTime open = LocalTime.of(startTime,0);
        LocalTime localOpen = open; // local variable for use within lambda
        LocalTime close = LocalTime.of(endTime,0);
        LocalTime night = LocalTime.of(23,45);
        LocalTime midnight = LocalTime.of(0, 0);
        if (startTime < endTime) { // business hours are within same day
            while (open.isBefore(close)) {
                apptStartTime.getItems().add(open);
                open = open.plusMinutes(15);
            }
        } else if (startTime > endTime) { // business hours loops past midnight
            ObservableList<LocalTime> startList = FXCollections.observableArrayList();
            while (open.isBefore(night)) {
                startList.add(open);
                open = open.plusMinutes(15);
            }
            startList.add(night);
            while (midnight.isBefore(close)) {
                startList.add(midnight);
                midnight = midnight.plusMinutes(15);
            }
            apptStartTime.setItems(startList);
        }

        // Lambda listener to convert the local variable hours
        apptStartTime.getSelectionModel().selectedItemProperty().addListener((observableValue, localTime, t1) -> {
            ObservableList<LocalTime> endTimesList = FXCollections.observableArrayList();
            LocalTime localClose = close.plusMinutes(15);
            LocalTime localMidnight = LocalTime.of(0,0);
            apptEndTime.getSelectionModel().clearSelection();
            apptEndTime.getItems().removeAll();
            //Ensuring hours are within the office hours
            if (!apptStartTime.getSelectionModel().isEmpty()) {
                if (startTime < endTime) {
                    while (t1.isBefore(close)) {
                        endTimesList.add(t1.plusMinutes(15));
                        t1 = t1.plusMinutes(15);
                    }
                    apptEndTime.setItems(endTimesList);
                    //The business hours loop when past midnight time schedule
                } else if (startTime > endTime) {
                    if (t1.isBefore(night) && (t1.equals(localOpen) || t1.isAfter(localOpen))) {
                        while (t1.isBefore(night)) {
                            endTimesList.add(t1.plusMinutes(15));
                            t1 = t1.plusMinutes(15);
                        }
                        while (localMidnight.isBefore(close.plusMinutes(15))) {
                            endTimesList.add(localMidnight);
                            localMidnight = localMidnight.plusMinutes(15);
                        }
                    } else if (t1.equals(night)) {
                        while (localMidnight.isBefore(localClose)) {
                            endTimesList.add(localMidnight);
                            localMidnight = localMidnight.plusMinutes(15);
                        }
                    } else if (t1.isBefore(close) && (t1.equals(localMidnight) || t1.isAfter(localMidnight))) {
                        while (t1.isBefore(close)) {
                            endTimesList.add(t1.plusMinutes(15));
                            t1 = t1.plusMinutes(15);
                        }
                    }
                    apptEndTime.setItems(endTimesList);
                }
            }
        });
        // Applies date lambdas for the date picker
        apptStartDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
            }
        });
        apptEndDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
            }
        });
    }

    /**
     * Checks for overlapping customer appointments.
     * @param appointmentsList The appointment list to be compared against.
     * @param customerId The customer ID to be compared against the list.
     * @param startZoned start zoned UTC
     * @param endZoned End Zoned UTC
     * @return true for a appointment overlap, or false
     */
    public boolean checkAppointmentOverlap(ObservableList<Appointments> appointmentsList, Customers customerId,
                                           ZonedDateTime startZoned, ZonedDateTime endZoned) {
        // If the overlapping appointment exist will return true; otherwise false
        for (Appointments a : appointmentsList) {
            if (a.getCustomerId() == customerId.getCustomerId()) {
                if (a.getEnd().isAfter(startZoned) && a.getStart().isBefore(endZoned)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adds new appointment while checking for missing fields.
     */
    public void addAppointmentButton() {
        if (apptTitle.getText() == null ||
                apptDescription.getText() == null ||
                apptLocation.getText() == null ||
                apptType.getText() == null ||
                apptStartDate.getValue() == null ||
                apptStartTime.getValue() == null ||
                apptEndDate.getValue() == null ||
                apptEndTime.getValue() == null ||
                apptCustomer.getValue() == null ||
                apptUser.getValue() == null ||
                apptContact.getValue() == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Required fields are not filled");
            alert.setHeaderText("Required fields are not filled");
            alert.setContentText("Please complete all form fields to add a appointment.");
            alert.showAndWait();
            return;
        }

        String title = apptTitle.getText();
        String desc = apptDescription.getText();
        String loc = apptLocation.getText();
        String type = apptType.getText();
        //System.out.println(LocalDateTime.of(apptStartDate.getValue(),
                //apptStartTime.getSelectionModel().getSelectedItem()).toString());
        Timestamp start = Timestamp.valueOf(LocalDateTime.of(apptStartDate.getValue(),
                apptStartTime.getSelectionModel().getSelectedItem()));
        //System.out.println(start.toString());
        Timestamp end = Timestamp.valueOf(LocalDateTime.of(apptEndDate.getValue(),
                apptEndTime.getSelectionModel().getSelectedItem()));
        //Todo: Start/End are in local time and must convert to UTC
        //LocalDateTime does not contain Zone information. ZonedDatetime does.
        //
        //If you want to convert LocalDateTime to UTC, you need to wrap by ZonedDateTime fist.
        ZonedDateTime startUTC = start.toInstant().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endUTC= end.toInstant().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
        System.out.println(startUTC.toLocalTime());
        System.out.println(endUTC.toLocalTime());
        //
        //Todo: convert zone times back to UTC back to local time
        ZonedDateTime startLocalZoned = startUTC.toInstant().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime endLocalZoned = endUTC.toInstant().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
        System.out.println(startLocalZoned.toLocalTime());
        System.out.println(endLocalZoned.toLocalTime());
        //
        //
        //System.out.println(start.toString()+"\n");
        Customers custId = apptCustomer.getValue();
        Users userId = apptUser.getValue();
        Contacts contactId = apptContact.getValue();
        Appointments.setAllAppointments(DBAppointments.getEveryAppointments());
        ObservableList<Appointments> appointmentsList = Appointments.getAllAppointments();
        if (startUTC.equals(endUTC) || startUTC.isAfter(endUTC)) {
            alert = new Alert(Alert.AlertType.ERROR);
            //lambda checker for the data/time
            alert.setTitle("Review the Appointment Date and Time");
            alert.setHeaderText("Review the Appointment Date and Time");
            alert.setContentText("Appt. start time must be before end time.");
            alert.showAndWait();
        } else if (checkAppointmentOverlap(appointmentsList, custId, startUTC, endUTC)) { //verify customer doesn't have overlapping appt
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("A Overlapping Appointment Detected");
            alert.setHeaderText("A Overlapping Appointment Detected");
            alert.setContentText("Error: Customer " + custId.getCustomerName() + " has a appointment " +
                    "scheduled at this time. ");
            alert.showAndWait();
        } else {
            // The appointment is added
            DBAppointments.addAppointment(title, desc, loc, type, start, end, custId.getCustomerId(),
                    userId.getUserId(), contactId.getContactId());
            // The appointment table is updated
            Appointments.setAllAppointments(DBAppointments.getEveryAppointments());
            appointmentsTable.setItems(Appointments.getAllAppointments());
            appointmentsTable.getSortOrder().add(appointmentsStart);
            // The appointment form is cleared
            clearAppts();
        }
    }

    /**
     * Updates appointment while checking for missing fields. Lambda utilization occurs before inserting appointment ID
     * until appointments are removed from the list, and then can pass the overlap method check.
     */
    public void updateAppointmentButton() {
        if (apptId.getText() == null ||
                apptTitle.getText() == null ||
                apptDescription.getText() == null ||
                apptLocation.getText() == null ||
                apptType.getText() == null ||
                apptStartDate.getValue() == null ||
                apptStartTime.getValue() == null ||
                apptEndDate.getValue() == null ||
                apptEndTime.getValue() == null ||
                apptCustomer.getValue() == null ||
                apptUser.getValue() == null ||
                apptContact.getValue() == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Required fields are not filled");
            alert.setHeaderText("Required fields are not filled");
            alert.setContentText("Please fill all fields.");
            alert.showAndWait();
            return;
        }
        int id = Integer.parseInt(apptId.getText());
        String title = apptTitle.getText();
        String desc = apptDescription.getText();
        String loc = apptLocation.getText();
        String type = apptType.getText();
        Timestamp start = Timestamp.valueOf(LocalDateTime.of(apptStartDate.getValue(),
                apptStartTime.getSelectionModel().getSelectedItem()));
        Timestamp end = Timestamp.valueOf(LocalDateTime.of(apptEndDate.getValue(),
                apptEndTime.getSelectionModel().getSelectedItem()));
        //Todo: Start/End are in local time and must convert to UTC
        //LocalDateTime does not contain Zone information. ZonedDatetime does.
        //
        //If you want to convert LocalDateTime to UTC, you need to wrap by ZonedDateTime fist.
        ZonedDateTime startZoned = start.toInstant().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endZoned = end.toInstant().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
        System.out.println(startZoned.toLocalTime());
        System.out.println(endZoned.toLocalTime());
        //
        //Todo: convert zone times back to UTC back to local time
        ZonedDateTime startLocalZoned = startZoned.toInstant().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime endLocalZoned = endZoned.toInstant().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
        System.out.println(startLocalZoned.toLocalTime());
        System.out.println(endLocalZoned.toLocalTime());
        //
        //
        Customers custId = apptCustomer.getValue();
        Users userId = apptUser.getValue();
        Contacts contactId = apptContact.getValue();
        Appointments.setAllAppointments(DBAppointments.getEveryAppointments());
        ObservableList<Appointments> appointmentsList = Appointments.getAllAppointments();
        appointmentsList.removeIf(a -> a.getAppointmentId() == id);
        if (startZoned.equals(endZoned) || startZoned.isAfter(endLocalZoned)){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Review the Appointment Date and Time");
            alert.setHeaderText("Review the Appointment Date and Time");
            alert.setContentText("Appt. start time must be before end time.");
            alert.showAndWait();
      //  } else if (checkAppointmentOverlap(appointmentsList, custId, start, end)) {// verify customer doesn't have overlapping appt
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("A Overlapping Appointment Detected");
            alert.setHeaderText("A Overlapping Appointment Detected");
            alert.setContentText("Error: Customer " + custId.getCustomerName() + " has a appointment " +
                    "scheduled at this time.");
            alert.showAndWait();
        } else {
            // The appointment is updated
            DBAppointments.updateAppointment(id, title, desc, loc, type, start, end, custId.getCustomerId(),
                    userId.getUserId(), contactId.getContactId());
            // The appointment table is updated
            Appointments.setAllAppointments(DBAppointments.getEveryAppointments());
            appointmentsTable.setItems(Appointments.getAllAppointments());
            appointmentsTable.getSortOrder().add(appointmentsStart);
            clearAppts();
        }
    }

    /**
     * Allows editing of appointment while checking for missing fields.
     */
    public void editAppointmentButton() {
        if (!(appointmentsTable.getSelectionModel().getSelectedItem() == null)) {
            Appointments selectedAppt = appointmentsTable.getSelectionModel().getSelectedItem();
            Customers.setAllCustomers(DBCustomers.getAllCustomers());
            ObservableList<Customers> customersList = Customers.getAllCustomers();
            int selectedCustId = 0;
            for (int i = 0; i < customersList.size(); i++) {
                if (customersList.get(i).getCustomerId() == selectedAppt.getCustomerId()) {
                    selectedCustId = customersList.indexOf(customersList.get(i));
                }
            }
            // Receives the matching User ID for the index
            int selectedUserId = 0;
            for (int i = 0; i < usersList.size(); i++) {
                if (usersList.get(i).getUserId() == selectedAppt.getUserId()) {
                    selectedUserId = usersList.indexOf(usersList.get(i));
                }
            }
            // Receives the matching Contact ID for the index
            int selectedContactId = 0;
            for (int i = 0; i < contactsList.size(); i++) {
                if (contactsList.get(i).getContactId() == selectedAppt.getContactId()) {
                    selectedContactId = contactsList.indexOf(contactsList.get(i));
                }
            }
            Customers customer = customersList.get(selectedCustId);
            Users user = usersList.get(selectedUserId);
            Contacts contact = contactsList.get(selectedContactId);
            apptId.setText(String.valueOf(selectedAppt.getAppointmentId()));
            apptTitle.setText(selectedAppt.getTitle());
            apptDescription.setText(selectedAppt.getDescription());
            apptLocation.setText(selectedAppt.getLocation());
            apptType.setText(selectedAppt.getType());
            apptStartDate.setValue(selectedAppt.getStart().toLocalDateTime().toLocalDate());
            apptStartTime.setValue(selectedAppt.getStart().toLocalDateTime().toLocalTime());
            apptEndDate.setValue(selectedAppt.getEnd().toLocalDateTime().toLocalDate());
            apptEndTime.setValue(selectedAppt.getEnd().toLocalDateTime().toLocalTime());
            apptCustomer.setValue(customer);
            apptUser.setValue(user);
            apptContact.setValue(contact);
        }
    }

    /**
     * Loads the view customers
     * @param actionEvent Changes scene to view customers
     * @throws IOException Error for failed load operation
     */
    public void viewCustomersButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/StephanWardSchedulingApp/view/ViewCustomers.fxml"));
        Scene mainScene = new Scene(scene);
        stage.setScene(mainScene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Loads the view reports
     * @param actionEvent Changes scene to view reports
     * @throws IOException Error for failed load operation
     */
    public void viewReportsButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/StephanWardSchedulingApp/view/ViewReports.fxml"));
        Scene mainScene = new Scene(scene);
        stage.setScene(mainScene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Allows deleting of appointment; with confirmation display
     */
    public void deleteAppointmentButton() {
        if (!(appointmentsTable.getSelectionModel().getSelectedItem() == null)) {
            Appointments selectedAppt = appointmentsTable.getSelectionModel().getSelectedItem();
            int id = selectedAppt.getAppointmentId();
            String type = selectedAppt.getType();
            DBAppointments.deleteAppointment(id);
            Appointments.setAllAppointments(DBAppointments.getEveryAppointments());
            appointmentsTable.setItems(Appointments.getAllAppointments());
            appointmentsTable.getSortOrder().add(appointmentsStart);
            clearAppts();
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Removed");
            alert.setHeaderText("Appointment Removed");
            alert.setContentText("Appointment ID: " + id + " \nAppointment Type: " + type);
            alert.showAndWait();
        }
    }

    /**
     * Button is called to clear the editable appointment fields.
     */
    public void clearAppointmentButton() {
        clearAppts();
    }

    /**
     * Clears all editable appointment fields.
     */
    public void clearAppts() {
        apptId.clear();
        apptTitle.clear();
        apptDescription.clear();
        apptLocation.clear();
        apptContact.getSelectionModel().clearSelection();
        apptType.clear();
        apptStartDate.getEditor().clear();
        apptStartTime.getSelectionModel().clearSelection();
        apptEndDate.getEditor().clear();
        apptEndTime.getSelectionModel().clearSelection();
        apptCustomer.setValue(null);
        apptUser.getSelectionModel().clearSelection();
    }

}
