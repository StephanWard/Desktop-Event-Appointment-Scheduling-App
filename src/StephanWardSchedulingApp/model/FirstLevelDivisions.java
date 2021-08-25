package StephanWardSchedulingApp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The model for the first level divisions class.
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class FirstLevelDivisions {
    private int divisionId;
    private String division;
    private int countryId;
    private String countryName;
    private static ObservableList<FirstLevelDivisions> allDivisions = FXCollections.observableArrayList();

    /**
     * Assigning the first level divisions constructors for the first level divisions model.
     * @param divisionId The first level division ID.
     * @param division The first level division name.
     * @param countryId The first level division for the country ID.
     * @param countryName The first level division for the country name.
     */
    public FirstLevelDivisions(int divisionId, String division, int countryId, String countryName) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * The getter for the first level division name for the first level divisions model.
     * @return The first level division name.
     */
    public String getDivision() {
        return division;
    }

    /**
     * The getter for the first level division ID for the first level divisions model.
     * @return The first level division ID.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * The getter for the first level country name for the first level divisions model.
     * @return The first level country name.
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * The getter for the first level country ID for the first level divisions model.
     * @return The first level country ID.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * The getter for all first level divisions for the first level divisions model.
     * @return The observable list of all first level divisions.
     */
    public static ObservableList<FirstLevelDivisions> getAllDivisions() {
        return allDivisions;
    }

    /**
     * The setter for all first level divisions that were used by a query from the database for the first level divisions model.
     * @param allDivisions All observable divisions list.
     */
    public static void setAllDivisions(ObservableList<FirstLevelDivisions> allDivisions) {
        //Obtains all divisions
        FirstLevelDivisions.allDivisions = allDivisions;
        //System print out of obtaining all divisions
        System.out.println("The query has successfully applied all first level divisions to the list");
    }

    /**
     * To display the division's name and ID together in the combo box, a override method that uses a to-string method with concatenation is used.
     * @return The concatenated division and division ID.
     */
    @Override
    //Public override method of the to-string to concatenate the division and ID
    public String toString() {
        return division + " [" + divisionId + "]";
    }
}
