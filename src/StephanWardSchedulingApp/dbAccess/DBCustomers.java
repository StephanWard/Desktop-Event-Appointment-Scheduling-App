package StephanWardSchedulingApp.dbAccess;

import javafx.collections.ObservableList;
import StephanWardSchedulingApp.model.Customers;
import StephanWardSchedulingApp.utils.DBConnection;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * The database customers class containing CRUD operations
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class DBCustomers {

    /**
     * The getter query for all customers.
     * @return The observable list for all customers.
     */
    public static ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> customerList = FXCollections.observableArrayList();
        //A Try/While/Catch method for querying the database fields to get results to be displayed as a array list
        try {
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, " +
                    "Division, first_level_divisions.Country_ID, Country FROM customers, first_level_divisions, " +
                    "countries WHERE customers.Division_ID = first_level_divisions.Division_ID AND " +
                    "first_level_divisions.Country_ID = countries.Country_ID ORDER BY Customer_ID;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int customerId = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("Postal_Code");
                String phone = resultSet.getString("Phone");
                int divisionId = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                int countryId = resultSet.getInt("Country_ID");
                String countryName = resultSet.getString("Country");
                Customers customers = new Customers(customerId, customerName, address, postalCode, phone, divisionId,
                        divisionName, countryId, countryName);
                customerList.add(customers);
            }
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not get all customers error");
            e.printStackTrace();
        }

        return customerList;
    }

    /**
     * A query to add customers
     * @param name The customer's name.
     * @param address The customer's address.
     * @param postal The customer's postal code.
     * @param phone The customer's phone number.
     * @param divisionId The customer's first level division ID.
     */
    public static void addCustomer(String name, String address, String postal, String phone, int divisionId) {
        //A Try/Catch method for querying the database fields to get results to be displayed
        try {
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, " +
                    "Division_ID) VALUES (NULL,?,?,?,?,?);";

            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, postal);
            preparedStatement.setString(4, phone);
            preparedStatement.setInt(5, divisionId);
            preparedStatement.execute();

        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not add customer error");
            e.printStackTrace();
        }
    }

    /**
     * A query to update a customer's information.
     * @param id The customer's ID.
     * @param name The customer's name.
     * @param address The customer's address.
     * @param postal The customer's postal code.
     * @param phone The customer's phone number.
     * @param divisionId The customer's first level division ID.
     */
    public static void updateCustomer(int id, String name, String address, String postal, String phone, int divisionId) {
        try {
            //A Try Catch method for querying the database fields to get results to be displayed
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                    "Division_ID = ? WHERE Customer_ID = ?;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, postal);
            preparedStatement.setString(4, phone);
            preparedStatement.setInt(5, divisionId);
            preparedStatement.setInt(6, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not update customer error");
            e.printStackTrace();
        }
    }

    /**
     * A query to delete a customer; will delete appointments first because of foreign key dependency upon the customer.
     * @param id The customer's ID.
     */
    public static void deleteCustomer(int id) {
        try {
            //A Try Catch method for querying the database fields to get results to be displayed
            String sql = "DELETE FROM appointments WHERE Customer_ID = ?;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not delete appointment error");
            e.printStackTrace();
        }

        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?;";

            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not delete customer error");
            e.printStackTrace();
        }
    }
}
