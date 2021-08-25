package StephanWardSchedulingApp.model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * The model used for the countries class.
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class Countries {
    private int countryId;
    private String country;
    private static ObservableList<Countries> allCountries = FXCollections.observableArrayList();

    /**
     * The getter for the country ID for the country model.
     * @return The country ID.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Assigning the country constructors for the country model.
     * @param countryId The country ID.
     * @param country The country name.
     */
    public Countries(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * The setter for all countries for the country model; using a database query.
     * @param allCountries The observable list from the database query.
     */
    public static void setAllCountries(ObservableList<Countries> allCountries) {
        Countries.allCountries = allCountries;
        System.out.println("The query has successfully applied all countries to the list");
    }

    /**
     * The getter for all countries for the country model.
     * @return The observable list for all countries.
     */
    public static ObservableList<Countries> getAllCountries() {
        return allCountries;
    }

    /**
     * The getter for the country name for the country model.
     * @return The country name.
     */
    public String getCountry() {
        return country;
    }

    /**
     * To display the country name and ID together in the combo box, a override method that uses a to-string method with concatenation is used.
     * @return The concatenated country name and ID
     */
    @Override
    //Override to string method to concatenate country and ID
    public String toString() {
        return country + " [" + countryId + "]";
    }
}
