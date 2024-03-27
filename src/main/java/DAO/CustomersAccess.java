package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomersAccess {

    public static ObservableList<Customers> getAllCustomers () throws SQLException {
        ObservableList<Customers> customerList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            int customerPhoneNumber = rs.getInt("Phone");
            String divisionName = rs.getString("Division");
            int divisionId = rs.getInt("Division_ID");
            Customers customer = new Customers(customerId, customerName, customerAddress, postalCode, customerPhoneNumber,
                    divisionId, divisionName);
            customerList.add(customer);
        }
        return customerList;
    }
}
