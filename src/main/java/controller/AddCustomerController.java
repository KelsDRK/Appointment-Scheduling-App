package controller;

import DAO.CountriesAccess;
import DAO.FirstLevelDivisionAccess;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AddCustomerController implements Initializable {

    @FXML private TextField addCustomerId;
    @FXML private TextField addCustomerName;
    @FXML private TextField addCustomerAddress;
    @FXML private TextField addCustomerPhone;
    @FXML private TextField addCustomerPostalCode;
    @FXML private Button addCustomerSave;
    @FXML private Button addCustomerCancel;
    @FXML private ComboBox<String> addCustomerCountry;
    @FXML private ComboBox<String> addCustomerState;

    Random random = new Random();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addCustomerId.setText(getNewCustomerID());
        addCustomerId.setDisable(true);

        ObservableList<Countries> countriesObservableList = null;
        try {countriesObservableList = CountriesAccess.getAllCountries();
        } catch (SQLException e) {throw new RuntimeException(e);}

        ObservableList<String> selectedCountries = FXCollections.observableArrayList();
        countriesObservableList.forEach(countries -> selectedCountries.add(countries.getCountryName()));

        ObservableList<FirstLevelDivisions> firstLevelDivisionsObservableList = null;
        try {firstLevelDivisionsObservableList = FirstLevelDivisionAccess.getAllLevelDivision();
        } catch (SQLException e) {throw new RuntimeException(e);}

        ObservableList<String> selectedStates = FXCollections.observableArrayList();
        firstLevelDivisionsObservableList.forEach(firstLevelDivisions -> selectedStates.add(firstLevelDivisions.getDivisionName()));

        addCustomerCountry.setItems(selectedCountries);
        addCustomerState.setItems(selectedStates);


    }
    public void onSaveAction(ActionEvent actionEvent) {

        try {
            Connection connection = JDBC.connection;

            if (!addCustomerId.getText().isEmpty() && !addCustomerName.getText().isEmpty() && !addCustomerAddress.getText().isEmpty() && !addCustomerPhone.getText().isEmpty()
                    && !addCustomerPostalCode.getText().isEmpty() && !addCustomerCountry.getValue().isEmpty() && !addCustomerState.getValue().isEmpty()) {

                String sql = "INSERT INTO customers VALUES (customer_id, customer_name, customer_address, phone, postal_code, create_date, created_by, last_updated, last_updated_by, division_id)" +
                        " VALUES (?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);

                ps.setInt(1, Integer.parseInt(addCustomerId.getText()));
                ps.setString(2, addCustomerName.getText());
                ps.setString(3, addCustomerAddress.getText());
                ps.setString(4, addCustomerPhone.getText());
                ps.setString(5, addCustomerPostalCode.getText());
                ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(9, "admin");
                ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(11,  "admin");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onCancelAction(ActionEvent actionEvent) throws IOException {
        System.out.println("Login Successful");
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
    }

    public String getNewCustomerID() {
        int productId = random.nextInt();
        boolean partFound = false;
        // the new ID will be 4 digits or fewer
        while (productId < 1000 || productId > 9999) {
            productId = random.nextInt();
        }
        return Integer.toString(productId);
    }
}
