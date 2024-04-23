package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomersAccess {

    public static ObservableList<Customers> getAllCustomers (Connection connection) throws SQLException {
        ObservableList<Customers> customerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String customerPhoneNumber = rs.getString("Phone");
            //String divisionName = rs.getString("Division");
            int divisionId = rs.getInt("Division_ID");
            Customers customer = new Customers(customerId, customerName, customerAddress, postalCode, customerPhoneNumber,
                    divisionId);
            customerList.add(customer);
        }
        return customerList;
    }

    public static int deleteAppointment (int customerId, Connection c) throws SQLException {
        String sql= "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int result = ps.executeUpdate();
        ps.close();
        return result;
    }
}
