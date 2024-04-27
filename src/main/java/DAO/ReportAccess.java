package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Reports;
import model.ReportsCount;
import model.ReportsMonth;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ReportAccess extends Appointments {

    public ReportAccess() throws SQLException {
    }

    public static ObservableList<Reports> getCountries() throws SQLException {
        ObservableList<Reports> countriesObservableList = FXCollections.observableArrayList();
        String sql = "select countries.Country, count(*) as countryCount from customers inner join first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID inner join countries on countries.Country_ID = first_level_divisions.Country_ID where  customers.Division_ID = first_level_divisions.Division_ID group by first_level_divisions.Country_ID order by count(*) desc";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String countryName = rs.getString("Country");
            int countryCount = rs.getInt("countryCount");
            Reports report = new Reports(countryName, countryCount);
            countriesObservableList.add(report);
        }
        return countriesObservableList;
    }

    public static ObservableList<ReportsMonth> getAllReportMonthData() throws SQLException {

        ObservableList<ReportsMonth> reportsMonths = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            //LocalDateTime month = rs.getTimestamp("Start").toLocalDateTime();
            String tableMonth = String.valueOf(rs.getTimestamp("Start").toLocalDateTime().getMonth());
            String appointmentType = rs.getString("Type");

            int count = 0;
            String sql1 = "SELECT COUNT(*) AS monthCount FROM APPOINTMENTS WHERE Type = ?";
            PreparedStatement ps1 = JDBC.connection.prepareStatement(sql1);
            ps1.setString(1, appointmentType);
            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {

                count = rs1.getInt("monthCount");

                ReportsMonth rm = new ReportsMonth(tableMonth, appointmentType, count);
                reportsMonths.add(rm);
            }

        }
        return reportsMonths;
    }
}
