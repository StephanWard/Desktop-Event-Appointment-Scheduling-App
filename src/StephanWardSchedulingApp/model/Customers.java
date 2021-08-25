package StephanWardSchedulingApp.model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


/**
 * The model used for the customers class.
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class Customers {
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhone;
    private int divisionId;
    private String divisionName;
    private int countryId;
    private String countryName;
    private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    /**
     * Assigning the customers constructors for the customers model.
     * @param customerId The customer's ID.
     * @param customerName The customer's name.
     * @param customerAddress The customer's address.
     * @param customerPostalCode The customer's postal code.
     * @param customerPhone The customer's phone number.
     * @param divisionId The customer's first level division ID.
     * @param divisionName The customer's first level division name.
     * @param countryId The customer's country ID.
     * @param countryName The customer's country name.
     */
    public Customers(int customerId, String customerName, String customerAddress, String customerPostalCode,
                     String customerPhone, int divisionId, String divisionName, int countryId, String countryName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * The getter for the customer name for the customer's model.
     * @return The customer's name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * The getter for the customer ID for the customer's model.
     * @return The customer's ID.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * The getter for the customer address for the customer's model.
     * @return The customer's address.
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * The getter for the customer's phone number for the customer's model.
     * @return The customer's phone number.
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * The getter for the customer's postal code for the customer's model.
     * @return The customer's postal code.
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * The getter for the customer's first level division for the customer's model.
     * @return The customer's first level division name.
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * The getter for the customer's country ID for the customer's model.
     * @return The customer's country ID.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * The getter for the customer's division ID for the customer's model.
     * @return The customer's first level division ID.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * The getter for the customer's observable list for the customer's model.
     * @return The observable list for all customers.
     */
    public static ObservableList<Customers> getAllCustomers() {
        return allCustomers;
    }

    /**
     * The getter for the customer's country name for the customer's model.
     * @return The customer's country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * A database query that applies the setter for all customers for the customer's model.
     * @param allCustomers The observable list from the database query.
     */
    public static void setAllCustomers(ObservableList<Customers> allCustomers) {
        Customers.allCustomers = allCustomers;
        System.out.println("The query has successfully applied all customers to the list");
    }

    /**
     * To display the customer's name and ID together in the combo box, a override method that uses a to-string method with concatenation is used.
     * @return The concatenated customer's name and ID.
     */
    @Override
    //Override method to the to-string for the concatenation of the customer name and ID
    public String toString() {
        return customerName + " [" + customerId + "]";
    }
}
