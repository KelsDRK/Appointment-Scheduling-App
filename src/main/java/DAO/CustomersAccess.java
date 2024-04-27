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
        String sql = "SELECT "
                +"c.customer_id, c.customer_name, c.address, c.postal_code, c.phone, c.division_id, fld.division"+
                " from customers AS c JOIN first_level_divisions AS fld ON fld.division_id = c.division_id";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String customerPhoneNumber = rs.getString("Phone");
            String divisionName = rs.getString("Division");
            int divisionId = rs.getInt("Division_ID");
            Customers customer = new Customers(customerId, customerName, customerAddress, postalCode, customerPhoneNumber,
                    divisionName, divisionId);
            customerList.add(customer);
        }
        return customerList;
    }

    public static void deleteCustomer (int customerId, Connection c) throws SQLException {
        String sql= "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ps.executeUpdate();
        ps.close();
    }
}
