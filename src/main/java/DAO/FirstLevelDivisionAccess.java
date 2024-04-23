package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.WeakHashMap;

public class FirstLevelDivisionAccess extends FirstLevelDivisions{

    public FirstLevelDivisionAccess(int divisionId, String divisionName, int countryId) {
        super(divisionId, divisionName, countryId);
    }

    public static ObservableList<FirstLevelDivisions> getAllLevelDivision () throws SQLException {
        ObservableList<FirstLevelDivisions> divisionsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
            FirstLevelDivisions f = new FirstLevelDivisions(divisionId, divisionName, countryId);
            divisionsList.add(f);
        }
        return divisionsList;
    }

    public static String findStateById(String divisionId) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, divisionId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            divisionId = rs.getString("Division");
        }
        return divisionId;
    }

    public static String findDivisionIdByState(String state) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, state);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            state = rs.getString("Division_ID");
        }
        return state;

    }

    public static String findCountryByDivisionId(String divisionId) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, divisionId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            divisionId = rs.getString("Country_ID");
        }
        return divisionId;
    }




}
