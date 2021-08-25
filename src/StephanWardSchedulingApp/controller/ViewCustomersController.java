package StephanWardSchedulingApp.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import StephanWardSchedulingApp.dbAccess.*;
import StephanWardSchedulingApp.model.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The view customers controller class.
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class ViewCustomersController implements Initializable {
    //Calls the FXML Initializer to load the scene data information
    @FXML private TableView<Customers> customersTable;
    @FXML private TableColumn<Customers, Integer> customersTableId;
    @FXML private TableColumn<Customers, String> customersTableName;
    @FXML private TableColumn<Customers, String> customersTableAddress;
    @FXML private TableColumn<Customers, String> customersTablePostalCode;
    @FXML private TableColumn<Customers, String> customersTablePhone;
    @FXML private TableColumn<FirstLevelDivisions, String> customersTableDivisionName;
    @FXML private TableColumn<Countries, String> customersTableCountryName;
    //Calls the FXML Initializer to load the scene data information
    @FXML private TextField customerId;
    @FXML private TextField customerName;
    @FXML private TextField customerAddress;
    @FXML private TextField customerPostalCode;
    @FXML private TextField customerPhone;
    @FXML private ComboBox<FirstLevelDivisions> customerDivision;
    @FXML private ComboBox<Countries> customerCountry;

    Alert alert;
    ObservableList<Countries> countryList = Countries.getAllCountries();

    /**
     * The tableview of customers is loaded, utilizing lambda, to prevent selecting a incorrect division for the
     * selected country.
     * @param url The url location for the root object or null
     * @param resourceBundle The resource bundle for the localized root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initializes the cell values
        customersTableId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customersTableName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customersTableAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customersTablePostalCode.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
        customersTablePhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customersTableDivisionName.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        customersTableCountryName.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        //Sets all Customers
        customersTable.setItems(DBCustomers.getAllCustomers());
        //Orders all customers
        customersTable.getSortOrder().add(customersTableId);
        customerCountry.setItems(Countries.getAllCountries());
        customerCountry.getSelectionModel().selectedItemProperty().addListener((observableValue, countries, t1) -> {
            if (!customerCountry.getSelectionModel().isEmpty()) {
                customerDivision.setItems(DBFirstLevelDivisions.getDivisionsByCountry(t1.getCountryId()));
            }
        });
    }

    /**
     * Adds customer information to the list when a new customer is added.
     */
    public void addCustomerButton() {
        if (customerName.getText() == null ||
                customerAddress.getText() == null ||
                customerPostalCode.getText() == null ||
                customerPhone.getText() == null ||
                customerDivision.getValue() == null ||
                customerCountry.getValue() == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Required fields are not filled");
            alert.setHeaderText("Required fields are not filled");
            alert.setContentText("Please complete all form fields to add a customer.");
            alert.showAndWait();
            return;
        }
        String name = customerName.getText();
        String address = customerAddress.getText();
        String postal = customerPostalCode.getText();
        String phone = customerPhone.getText();
        FirstLevelDivisions division = customerDivision.getValue();
        //The Customer's name, address, postal, phone, and division information is added.
        DBCustomers.addCustomer(name, address, postal, phone, division.getDivisionId());
        Customers.setAllCustomers(DBCustomers.getAllCustomers());
        customersTable.setItems(Customers.getAllCustomers());
        customersTable.getSortOrder().add(customersTableId);
        clearCustomers();
    }

    /**
     * Adds customer information to the list when the customer is updated.
     */
    public void updateCustomerButton() {
        if (customerId.getText() == null ||
                customerName.getText() == null ||
                customerAddress.getText() == null ||
                customerPostalCode.getText() == null ||
                customerPhone.getText() == null ||
                customerDivision.getValue() == null ||
                customerCountry.getValue() == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Required fields are not filled");
            alert.setHeaderText("Required fields are not filled");
            alert.setContentText("Please complete all form fields to update a customer.");
            alert.showAndWait();
            return;
        }
        int id = Integer.parseInt(customerId.getText());
        String name = customerName.getText();
        String address = customerAddress.getText();
        String postal = customerPostalCode.getText();
        String phone = customerPhone.getText();
        FirstLevelDivisions division = customerDivision.getValue();
        //The Customer's name, address, postal, phone, and division information is updated.
        DBCustomers.updateCustomer(id, name, address, postal, phone, division.getDivisionId());
        Customers.setAllCustomers(DBCustomers.getAllCustomers());
        customersTable.setItems(Customers.getAllCustomers());
        customersTable.getSortOrder().add(customersTableId);
        clearCustomers();
    }

    /**
     * Adds customer information to the list when the customer needs to be edited.
     */
    public void editCustomerButton() {
        if (!(customersTable.getSelectionModel().getSelectedItem() == null)) {
            Customers selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
            FirstLevelDivisions div = DBFirstLevelDivisions.getDivisionById(selectedCustomer.getDivisionId());
            int selectedCountryId = 0;
            for (int i = 0; i < countryList.size(); i++) {
                if (countryList.get(i).getCountryId() == selectedCustomer.getCountryId()) {
                    selectedCountryId = countryList.indexOf(countryList.get(i));
                }
            }
            customerId.setText(String.valueOf(selectedCustomer.getCustomerId()));
            customerName.setText(selectedCustomer.getCustomerName());
            customerAddress.setText(selectedCustomer.getCustomerAddress());
            customerPostalCode.setText(selectedCustomer.getCustomerPostalCode());
            customerPhone.setText(selectedCustomer.getCustomerPhone());
            customerCountry.setValue(countryList.get(selectedCountryId));
            customerDivision.setValue(div);
        }
    }

    /**
     * Deletes customer information to the list when the customer needs to be deletes; with confirmation message.
     */
    public void deleteCustomerButton() {
        if (!(customersTable.getSelectionModel().getSelectedItem() == null)) {
            Customers selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
            int id = selectedCustomer.getCustomerId();
            String name = selectedCustomer.getCustomerName();
            // The customer is deleted based upon the customer ID
            DBCustomers.deleteCustomer(id);
            Customers.setAllCustomers(DBCustomers.getAllCustomers());
            customersTable.setItems(Customers.getAllCustomers());
            customersTable.getSortOrder().add(customersTableId);
            clearCustomers();
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer Removed");
            alert.setHeaderText("Customer Removed");
            alert.setContentText("The respective customer and appointments have been removed. \nCustomer ID: " +
                    id + "\nCustomer Name: " + name);
            alert.showAndWait();
        }
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
     * Clears customer fields when button pressed.
     */
    public void clearCustomerButton() {
        clearCustomers();
    }

    /**
     * Clears combo boxes and fields when editing customer
     */
    public void clearCustomers() {
        customerId.clear();
        customerName.clear();
        customerAddress.clear();
        customerPostalCode.clear();
        customerPhone.clear();
        customerCountry.getSelectionModel().clearSelection();
        customerDivision.setValue(null);
    }

    /**
     * Loads the view appointments
     * @param actionEvent Changes scene to view appointments
     * @throws IOException Error for failed load operation
     */
    public void viewAppointmentsButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/StephanWardSchedulingApp/view/ViewAppointments.fxml"));
        Scene mainScene = new Scene(scene);
        stage.setScene(mainScene);
        stage.centerOnScreen();
        stage.show();
    }
}
