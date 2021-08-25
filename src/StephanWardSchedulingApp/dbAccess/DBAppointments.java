package StephanWardSchedulingApp.dbAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import StephanWardSchedulingApp.model.*;
import StephanWardSchedulingApp.utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The database appointments class containing CRUD operations
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class DBAppointments {

    /**
     * A query for all appointments.
     * @return The observable list for all appointments.
     */
    public static ObservableList<Appointments> getEveryAppointments() {
        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();
        //A Try/While/Catch method for querying the database fields to get results to be displayed as a array list
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, " +
                    "appointments.Customer_ID, Customer_Name, appointments.User_ID, User_Name, appointments.Contact_ID, " +
                    "Contact_Name FROM appointments, users, customers, contacts WHERE appointments.Customer_ID = " +
                    "customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = " +
                    "contacts.Contact_ID ORDER BY appointments.Appointment_ID;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int apptId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String desc = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp start = resultSet.getTimestamp("Start");
                Timestamp end = resultSet.getTimestamp("End");
                ZonedDateTime startZoned = start.toInstant().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
                ZonedDateTime endZoned = end.toInstant().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
                int customerId = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                int userId = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                int contactId = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");

                Appointments appointments = new Appointments(apptId, title, desc, location, type, startZoned, endZoned,
                        customerId, customerName, userId, userName, contactId, contactName);
                appointmentList.add(appointments);
            }
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not get all appointments error");
            e.printStackTrace();
        }

        return appointmentList;
    }

    /**
     * The query to get appointments based upon Week.
     * @param week The week parameter based upon week
     * @return The observable list of appointments based upon current week.
     */
    public static ObservableList<Appointments> getAppointmentsByWeek(int week) {
        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();
        //A Try/While/Catch method for querying the database fields to get results to be displayed as a array list
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, " +
                    "appointments.Customer_ID, Customer_Name, appointments.User_ID, User_Name, appointments.Contact_ID, " +
                    "Contact_Name FROM appointments, users, customers, contacts WHERE appointments.Customer_ID = " +
                    "customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = " +
                    "contacts.Contact_ID AND WEEK(Start) = WEEK(now(), 0) + ? ORDER BY " +
                    "appointments.Appointment_ID;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, week);
            ResultSet resultSet = preparedStatement.executeQuery();
            //Obtains the results from the database query
            while (resultSet.next()) {
                int apptId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String desc = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp start = resultSet.getTimestamp("Start");
                Timestamp end = resultSet.getTimestamp("End");
                ZonedDateTime startZoned = start.toInstant().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
                ZonedDateTime endZoned = end.toInstant().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
                int customerId = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                int userId = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                int contactId = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");

                Appointments appointments = new Appointments(apptId, title, desc, location, type, startZoned, endZoned,
                        customerId, customerName, userId, userName, contactId, contactName);
                appointmentList.add(appointments);
            }
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not update appointment time error");
            e.printStackTrace();
        }
        //Returns the list of items
        return appointmentList;
    }


    /**
     * The query to get appointments based upon current month.
     * @param month The month parameter based upon month.
     * @return The observable list of appointments based upon month.
     */
    public static ObservableList<Appointments> getAppointmentsByMonth(int month) {
        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();
        //A Try/While/Catch method for querying the database fields to get results to be displayed as a array list
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, " +
                    "appointments.Customer_ID, Customer_Name, appointments.User_ID, User_Name, appointments.Contact_ID, " +
                    "Contact_Name FROM appointments, users, customers, contacts WHERE appointments.Customer_ID = " +
                    "customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = " +
                    "contacts.Contact_ID AND MONTH(Start) = MONTH(now()) + ? ORDER BY " +
                    "appointments.Appointment_ID;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, month);
            ResultSet resultSet = preparedStatement.executeQuery();
            //Obtains the results from the database query
            while (resultSet.next()) {
                int apptId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String desc = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                Timestamp start = resultSet.getTimestamp("Start");
                Timestamp end = resultSet.getTimestamp("End");
                ZonedDateTime startZoned = start.toInstant().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
                ZonedDateTime endZoned = end.toInstant().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
                int customerId = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                int userId = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                int contactId = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");

                Appointments appointments = new Appointments(apptId, title, desc, location, type, startZoned, endZoned,
                        customerId, customerName, userId, userName, contactId, contactName);
                appointmentList.add(appointments);
            }
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not update appointment time error");
            e.printStackTrace();
        }
        //Returns the list of items
        return appointmentList;
    }


    /**
     * The query to add appointments
     * @param title The appointment title.
     * @param description The appointment description.
     * @param location The appointment location.
     * @param type The appointment type.
     * @param customerId The appointment customer ID.
     * @param userId The appointment user ID.
     * @param contactId The appointment contact ID.
     */
    public static void addAppointment(String title, String description, String location, String type, Timestamp start,
                                      Timestamp end, int customerId, int userId, int contactId) {
        //A Try/While/Catch method for querying the database fields to get results to be displayed as a array list
        try {
            String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, " +
                    "Customer_ID, User_ID, Contact_ID) VALUES (NULL,?,?,?,?,?,?,?,?,?);";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, type);
            preparedStatement.setTimestamp(5, start);
            preparedStatement.setTimestamp(6, end);
            preparedStatement.setInt(7, customerId);
            preparedStatement.setInt(8, userId);
            preparedStatement.setInt(9, contactId);
            preparedStatement.execute();
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not add appointment error");
            e.printStackTrace();
        }
    }

    /**
     * The query to update appointments
     * @param id The appointment ID.
     * @param title The appointment title.
     * @param description The appointment description.
     * @param location The appointment location.
     * @param type The appointment type.
     * @param customerId The appointment customer's ID.
     * @param userId The appointment user's ID.
     * @param contactId The appointment contact's ID.
     */
    public static void updateAppointment(int id, String title, String description, String location, String type,
                                         Timestamp start, Timestamp end, int customerId, int userId, int contactId) {
        //A Try Catch method for querying the database fields to get results to be displayed as a array list
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, " +
                    "End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, type);
            preparedStatement.setTimestamp(5, start);
            preparedStatement.setTimestamp(6, end);
            preparedStatement.setInt(7, customerId);
            preparedStatement.setInt(8, userId);
            preparedStatement.setInt(9, contactId);
            preparedStatement.setInt(10, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not update appointment error");
            e.printStackTrace();
        }
    }

    /**
     * The query to delete appointments
     * @param id The appointment ID.
     */
    public static void deleteAppointment(int id) {
        //A Try Catch method for querying the database fields to get results to be displayed
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not delete appointment error");
            e.printStackTrace();
        }
    }
}
