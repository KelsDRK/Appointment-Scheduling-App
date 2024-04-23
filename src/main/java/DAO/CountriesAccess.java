package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesAccess extends Countries{

    public CountriesAccess(int countryId, String countryName) {
        super(countryId, countryName);
    }

    public static ObservableList<Countries> getAllCountries() throws SQLException {
        ObservableList<Countries> countriesList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Countries country = new Countries(countryId, countryName);
            countriesList.add(country);
        }
        return countriesList;
    }

    public static String findCountryById(String countryId) throws SQLException {
        String sql = "SELECT * FROM COUNTRIES WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, countryId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            countryId = rs.getString("Country");
        }
        return countryId;
    }



}
