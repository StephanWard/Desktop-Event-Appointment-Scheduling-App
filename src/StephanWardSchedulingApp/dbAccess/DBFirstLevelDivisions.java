package StephanWardSchedulingApp.dbAccess;

import javafx.collections.ObservableList;
import StephanWardSchedulingApp.model.FirstLevelDivisions;
import StephanWardSchedulingApp.utils.DBConnection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import java.sql.SQLException;

/**
 * The database first level division class that uses R queries for the database.
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class DBFirstLevelDivisions {

    /**
     * The getter query for all first level divisions.
     * @return The observable list of all the first level divisions.
     */
    public static ObservableList<FirstLevelDivisions> getAllDivisions() {
        ObservableList<FirstLevelDivisions> divisionList = FXCollections.observableArrayList();
        //A Try Catch method for querying the database fields to get results to be displayed
        try {
            String sql = "SELECT Division_ID, Division, first_level_divisions.Country_ID, countries.Country FROM " +
                    "first_level_divisions, countries WHERE countries.Country_ID = first_level_divisions.Country_ID;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            //Obtains the results from the database query
            while (resultSet.next()) {
                int divisionId = resultSet.getInt("Division_ID");
                String division = resultSet.getString("Division");
                int countryId = resultSet.getInt("Country_ID");
                String country = resultSet.getString("Country");
                FirstLevelDivisions divisions = new FirstLevelDivisions(divisionId, division, countryId, country);
                divisionList.add(divisions);
            }
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not get all first level divisions error");
            e.printStackTrace();
        }
        //Returns the list of items
        return divisionList;
    }

    /**
     * The getter query for divisions by country ID.
     * @param num Country ID.
     * @return The observable list for all first level divisions when country ID is matched.
     */
    public static ObservableList<FirstLevelDivisions> getDivisionsByCountry(int num) {
        ObservableList<FirstLevelDivisions> divisionList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Division_ID, Division, first_level_divisions.Country_ID, countries.Country FROM " +
                    "first_level_divisions, countries WHERE first_level_divisions.Country_ID = countries.Country_ID " +
                    "AND first_level_divisions.Country_ID = ?;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, num);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int divisionId = resultSet.getInt("Division_ID");
                String division = resultSet.getString("Division");
                int countryId = resultSet.getInt("Country_ID");
                String country = resultSet.getString("Country");
                FirstLevelDivisions divisions = new FirstLevelDivisions(divisionId, division, countryId, country);
                divisionList.add(divisions);
            }
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not match country ID with division error");
            e.printStackTrace();
        }
        return divisionList;
    }

    /**
     * The getter query for division ID to be allowed in editing
     * @param num The first level division ID.
     * @return The first level division object matching division ID.
     */
    public static FirstLevelDivisions getDivisionById(int num) {
        FirstLevelDivisions divisions = null;
        try {
            String sql = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions WHERE Division_ID = ?;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, num);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int divisionId = resultSet.getInt("Division_ID");
                String division = resultSet.getString("Division");
                int countryId = resultSet.getInt("Country_ID");
                String country = null;
                divisions = new FirstLevelDivisions(divisionId, division, countryId, country);
            }
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not get country by division id");
            e.printStackTrace();
        }
        return divisions;
    }
}
