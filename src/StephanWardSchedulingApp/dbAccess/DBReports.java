package StephanWardSchedulingApp.dbAccess;

import StephanWardSchedulingApp.model.Reports;
import StephanWardSchedulingApp.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * The database report class that uses R queries for the database.
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class DBReports {

    /**
     * The getter query for all customer appointments of the database report.
     * @return The observable list for all customer appointments by type and month.
     */
    public static ObservableList<Reports> getCustomerAppointments() {
        ObservableList<Reports> reportsList = FXCollections.observableArrayList();
        //A Try/While/Catch method for querying the database fields to get results to be displayed
        try {
            String sql = "SELECT MONTH(Start), MONTHNAME(Start), Type, COUNT(*) FROM appointments GROUP BY " +
                    "MONTH(Start), MONTHNAME(Start), Type ORDER BY MONTH(Start);";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // To order cust. appt. non-alphabetically; monthNum is used in the query
                int monthNum = resultSet.getInt("MONTH(Start)");
                String monthName = resultSet.getString("MONTHNAME(Start)");
                String type = resultSet.getString("Type");
                int apptCount = resultSet.getInt("COUNT(*)");
                Reports report = new Reports(monthNum, monthName, type, apptCount);
                reportsList.add(report);
            }
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not receive customer appointments");
            e.printStackTrace();
        }
        return reportsList;
    }

    /**
     * The getter query for the contact schedule for the database report.
     * @return The observable list for all appointments with respect to each contact.
     */
    public static ObservableList<Reports> getContactSchedule() {
        ObservableList<Reports> reportsList = FXCollections.observableArrayList();
        //A Try/While/Catch method for querying the database fields to get results to be displayed
        try {
            String sql = "SELECT Appointment_ID, Title, Type, Description, Start, End, appointments.Customer_ID, " +
                    "customers.Customer_Name, appointments.Contact_ID, contacts.Contact_Name FROM appointments, " +
                    "contacts, customers WHERE appointments.Contact_ID = contacts.Contact_ID AND " +
                    "appointments.Customer_ID = customers.Customer_ID ORDER BY appointments.Contact_ID;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int apptId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String type = resultSet.getString("Type");
                String desc = resultSet.getString("Description");
                Timestamp start = resultSet.getTimestamp("Start");
                Timestamp end = resultSet.getTimestamp("End");
                ZonedDateTime startZoned = start.toInstant().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
                ZonedDateTime endZoned = end.toInstant().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"));
                int customerId = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                int contactId = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                Reports report = new Reports(apptId, title, type, desc, startZoned, endZoned, customerId, customerName,
                        contactId, contactName);
                reportsList.add(report);
            }
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("error get contact schedule");
            e.printStackTrace();
        }
        return reportsList;
    }

    /**
     * The getter query for the user appointment for the database report.
     * @return The observable list for appointment counts and is added for the number of customers by every respective
     * user.
     */
    public static ObservableList<Reports> getUserAppointments() {
        ObservableList<Reports> reportsList = FXCollections.observableArrayList();
        //A Try/While/Catch method for querying the database fields to get results to be displayed
        try {
            String sql = "SELECT appointments.User_ID, users.User_Name, COUNT(DISTINCT Appointment_ID), " +
                    "COUNT(DISTINCT Customer_ID) FROM appointments, users WHERE appointments.User_ID = users.User_ID " +
                    "GROUP BY appointments.User_ID, users.User_Name ORDER BY appointments.User_ID;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            //Obtains the results from the database query
            while (resultSet.next()) {
                int userId = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                int apptCount = resultSet.getInt("COUNT(DISTINCT Appointment_ID)");
                int customerCount = resultSet.getInt("COUNT(DISTINCT Customer_ID)");
                Reports report = new Reports(userId, userName, apptCount, customerCount);
                reportsList.add(report);
            }
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not receive user appt. reports");
            e.printStackTrace();
        }
        //Returns the list of items
        return reportsList;
    }
}
