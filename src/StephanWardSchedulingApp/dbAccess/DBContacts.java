package StephanWardSchedulingApp.dbAccess;


import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import StephanWardSchedulingApp.utils.DBConnection;
import StephanWardSchedulingApp.model.Contacts;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 * The database contacts class that uses a R query for the database.
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class DBContacts {

    /**
     * The getter query for all contacts
     * @return The observable list for all contacts.
     */
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> contactList = FXCollections.observableArrayList();
        //A Try/While/Catch method for querying the database fields to get results to be displayed
        try {
            String sql = "SELECT Contact_ID, Contact_Name FROM contacts ORDER BY Contact_ID;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            //Obtains the results from the database query
            while (resultSet.next()) {
                int contactId = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                Contacts contacts = new Contacts(contactId, contactName);
                contactList.add(contacts);
            }
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not get all contacts error, please check for correct information");
            e.printStackTrace();
        }
        //Returns the list of items
        return contactList;
    }
}
