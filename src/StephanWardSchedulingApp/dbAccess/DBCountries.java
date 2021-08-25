package StephanWardSchedulingApp.dbAccess;

import StephanWardSchedulingApp.model.Countries;
import StephanWardSchedulingApp.utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.SQLException;

/**
 * The database countries class that uses a R query for the database.
 * @author Stephan Ward
 * @since 07/06/2021
 */
public class DBCountries {

    /**
     * The getter query for all countries.
     * @return The observable list for all countries.
     */
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> countryList = FXCollections.observableArrayList();
        //A Try/While/Catch method for querying the database fields to get results to be displayed
        try {
            String sql = "SELECT Country_ID, Country FROM countries ORDER BY Country_ID;";
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            //Obtains the results from the database query
            while (resultSet.next()) {
                int countryId = resultSet.getInt("Country_ID");
                String country = resultSet.getString("Country");
                Countries countries = new Countries(countryId, country);
                countryList.add(countries);
            }
        } catch (SQLException e) {
            //Prints Error to system console
            System.out.println("Could not get all countries error");
            e.printStackTrace();
        }
        //Returns the list of items
        return countryList;
    }
}
