package StephanWardSchedulingApp.model;

import java.time.ZonedDateTime;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * The model used for the appointments class.
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class Appointments {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private ZonedDateTime start;
    private ZonedDateTime end;
    //Todo: Add the Zoned Date Time
    //private ZonedDateTime start;
    //private ZonedDateTime end;
    private int customerId;
    private String customerName;
    private int userId;
    private String userName;
    private int contactId;
    private String contactName;
    private static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

    /**
     * Assigning the appointment constructors for the appointment model
     * @param appointmentId The appointment ID.
     * @param title The appointment title.
     * @param description The appointment description.
     * @param location The appointment location.
     * @param type The appointment type.
     * @param customerId The appointment customer ID.
     * @param customerName The appointment's customer name.
     * @param userId The appointment's user ID.
     * @param userName The appointment's username.
     * @param contactId The appointment's contact ID.
     * @param contactName The appointment's contact name.
     */
    public Appointments(int appointmentId, String title, String description, String location, String type,
                        ZonedDateTime startZoned, ZonedDateTime endZoned, int customerId, String customerName, int userId,
                        String userName, int contactId, String contactName) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = startZoned;
        this.end = endZoned;
        this.customerId = customerId;
        this.customerName = customerName;
        this.userId = userId;
        this.userName = userName;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * The getter for the appointment title for the appointment model.
     * @return The appointment title.
     */
    public String getTitle() {
        return title;
    }


    /**
     * The getter for the appointment ID for the appointment model.
     * @return The appointment ID.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * The getter for the appointment location for the appointment model.
     * @return The appointment location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * The getter for the appointment description for the appointment model.
     * @return The appointment description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * The getter for the appointment start timestamp for the appointment model.
     * @return The appointment start time.
     */
    public ZonedDateTime getStart() {
        return start;
    }

    /**
     * The getter for the appointment end timestamp for the appointment model.
     * @return The appointment end time.
     */
    public ZonedDateTime getEnd() {
        return end;
    }

    /**
     * The getter for the appointment type for the appointment model.
     * @return The appointment type.
     */
    public String getType() {
        return type;
    }

    /**
     * The getter for the appointment customer ID for the appointment model.
     * @return The appointment customer's ID.
     */
    public int getCustomerId() {
        return customerId;
    }


    /**
     * The getter for the appointment user ID for the appointment model.
     * @return The appointment user's ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * The getter for the appointment customer name for the appointment model.
     * @return The appointment customer's name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * The getter for the appointment contact ID for the appointment model.
     * @return The appointment contact's ID.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * The getter for the appointment username for the appointment model.
     * @return The appointment's username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * The getter for all appointment list for the appointment model.
     * @return The observable list of all appointments.
     */
    public static ObservableList<Appointments> getAllAppointments() {
        return allAppointments;
    }

    /**
     * The setter for all appointment list for the appointment model; using the database query.
     * @param allAppointments The observable list from the database query.
     */
    public static void setAllAppointments(ObservableList<Appointments> allAppointments) {
        Appointments.allAppointments = allAppointments;
        System.out.println("The query has successfully applied all appointments to the list");
    }

    /**
     * The getter for the appointment contact name for the appointment model.
     * @return The appointment contact's name.
     */
    public String getContactName() {
        return contactName;
    }

}
