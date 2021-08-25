package StephanWardSchedulingApp.model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * The model used for the reports class
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class Reports {

    private int userId;
    private String userName;
    private String customerName;
    private int customerId;
    private int contactId;
    private String contactName;
    private int appointmentId;
    private String title;
    private String type;
    private String description;
    private int appointmentCount;
    private int customerCount;
    private int monthNum;
    private String monthName;
    //private Timestamp start;
    //private Timestamp end;
    private ZonedDateTime start;
    private ZonedDateTime end;

    /**
     * Assigning the report constructors for the report model
     * @param appointmentId The appointment ID.
     * @param title The appointment title.
     * @param type The appointment type.
     * @param description The appointment description.
     * @param start The appointment start timestamp.
     * @param end The appointment end timestamp.
     * @param customerId The appointment customer ID.
     * @param customerName The customer name.
     * @param contactId The appointment contact ID.
     * @param contactName The contact name.
     */
    public Reports(int appointmentId, String title, String type, String description, ZonedDateTime startZoned, ZonedDateTime endZoned,
                   int customerId, String customerName, int contactId, String contactName) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.customerName = customerName;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * Assigning the customer constructors for the database query of appointments.
     * @param monthNum Number of month.
     * @param monthName Month name.
     * @param type Appointment type.
     * @param apptCount Appointment count.
     */
    public Reports(int monthNum, String monthName, String type, int apptCount) {
        this.monthNum = monthNum;
        this.monthName = monthName;
        this.type = type;
        this.appointmentCount = apptCount;
    }

    /**
     * Assigning the User constructors for the database query of appointments.
     * @param userId Appointment user ID.
     * @param userName Username.
     * @param appointmentCount Appointment count.
     * @param customerCount Customer count.
     */
    public Reports(int userId, String userName, int appointmentCount, int customerCount) {
        this.userId = userId;
        this.userName = userName;
        this.appointmentCount = appointmentCount;
        this.customerCount = customerCount;
    }

    /**
     * The appointment title getter that is used in the report model
     * @return The appointment title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * The appointment ID getter that is used in the report model
     * @return Appointment ID.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * The appointment type getter that is used in the report model
     * @return The Appointment type.
     */
    public String getType() {
        return type;
    }

 //   /**
  //   * The appointment start timestamp getter that is used in the report model
 //   * @return The appointment start timestamp.
  //   */
 //   public Timestamp getStart() {
  //      return start
  //  }
    /**
     * The appointment start zoned timestamp getter that is used in the report model
     *
     */
    public ZonedDateTime getStart() {
        return start;
    }

    /**
     * The Appointment description getter that is used in the report model
     * @return The Appointment description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * The customer ID getter that is used in the report model
     * @return The appointment customer ID.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * The appointment end timestamp getter that is used in the report model
     * @return The appointment end timestamp.
     */
    //public Timestamp getEnd() {
     //   return end;
    //}

    /**
     * The appointment zoned end timestamp getter that is used in the report model
     */
    public ZonedDateTime getEnd() {
        return end;
    }

    /**
     * The appointment contact ID getter that is used in the report model
     * @return The appointment contact ID.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * The customer name getter that is used in the report model
     * @return The appointment customer name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * The appointment user ID getter that is used in the report model
     * @return The appointment user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * The contact name getter that is used in the report model
     * @return The contact name.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * The appointment count getter that is used in the report model
     * @return The appointment count.
     */
    public int getAppointmentCount() {
        return appointmentCount;
    }

    /**
     * The username getter that is used in the report model
     * @return The username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * The appointment month number getter that is used in the report model
     * @return The month number.
     */
    public int getMonthNum() {
        return monthNum;
    }

    /**
     * The appointment month name getter that is used in the report model
     * @return The month name.
     */
    public String getMonthName() {
        return monthName;
    }

    /**
     * The customer count getter that is used in the report model
     * @return The customer count.
     */
    public int getCustomerCount() {
        return customerCount;
    }
}
