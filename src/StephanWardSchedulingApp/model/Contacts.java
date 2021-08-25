package StephanWardSchedulingApp.model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * The model used for the contacts class.
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class Contacts {
    private int contactId;
    private String contactName;
    private static ObservableList<Contacts> allContacts = FXCollections.observableArrayList();

    /**
     * The getter for the contact's ID for the contacts model.
     * @return The contact's ID.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Assigning the contacts constructors for the contacts model.
     * @param contactId The contact's ID.
     * @param contactName The contact's name.
     */
    public Contacts(int contactId, String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * The getter for all contacts for the contacts model.
     * @return The observable list for all contacts.
     */
    public static ObservableList<Contacts> getAllContacts() {
        return allContacts;
    }

    /**
     * The getter for the contact's name for the contacts model.
     * @return The contact's name.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * The setter for all contacts for the contacts model; using a database query.
     * @param allContacts The observable list of contacts from the database query.
     */
    public static void setAllContacts(ObservableList<Contacts> allContacts) {
        Contacts.allContacts = allContacts;
        System.out.println("The query has successfully applied all contacts to the list");
    }

    /**
     * To display the contact's name and ID together in the combo box, a override method that uses a to-string method with concatenation is used.
     * @return The concatenated contact name and ID
     */
    //Override method tostring for contactName and ID
    @Override
    public String toString() {
        return contactName + " [" + contactId + "]";
    }
}
