package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DivisionCount;
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

    public static ObservableList<DivisionCount> getDivisionCount() throws SQLException {

        ObservableList<DivisionCount> divisionCount = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String divisionID = rs.getString("Division_ID");

            String divisionNameConverted = "";
            String sql2 = "SELECT DIVISION FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = ?";
            PreparedStatement ps2 = JDBC.connection.prepareStatement(sql2);
            ps2.setString(1, divisionID);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {

                divisionNameConverted = rs2.getString("Division");


            }


            int count = 0;
            String sql1 = "SELECT COUNT(*) AS divisionCount FROM CUSTOMERS WHERE Division_ID = ?";
            PreparedStatement ps1 = JDBC.connection.prepareStatement(sql1);
            ps1.setString(1, divisionID);
            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {

                count = rs1.getInt("divisionCount");

                DivisionCount monthCount = new DivisionCount(divisionNameConverted, count);
                divisionCount.add(monthCount);
            }
        }
        return divisionCount;
    }




}
