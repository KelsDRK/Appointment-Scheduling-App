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
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {


    @FXML private TextField updateCustomerId;
    @FXML private TextField updateCustomerName;
    @FXML private TextField updateCustomerAddress;
    @FXML private TextField updateCustomerPhone;
    @FXML private TextField updateCustomerPostal;
    @FXML private Button updateCustomerSave;
    @FXML private Button updateCustomerCancel;
    @FXML private ComboBox<String> updateCustomerCountry;
    @FXML private ComboBox<String> updateCustomerState;

    private Customers customers;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customers = MainMenuController.getCustomer();

        updateCustomerId.setText(String.valueOf(customers.getCustomerId()));
        updateCustomerName.setText(String.valueOf(customers.getCustomerName()));
        updateCustomerAddress.setText(String.valueOf(customers.getCustomerAddress()));
        updateCustomerPhone.setText(String.valueOf(customers.getCustomerPhoneNumber()));
        updateCustomerPostal.setText(String.valueOf(customers.getCustomerPostalCode()));

        try {
            updateCustomerState.getSelectionModel().select(String.valueOf(FirstLevelDivisionAccess.findStateById(String.valueOf(customers.getDivisionId()))));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            updateCustomerCountry.getSelectionModel().select(String.valueOf(CountriesAccess.findCountryById(String.valueOf
                    (FirstLevelDivisionAccess.findCountryByDivisionId(String.valueOf(customers.getDivisionId()))))));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<Countries> countriesObservableList = null;
        try {
            countriesObservableList = CountriesAccess.getAllCountries();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<String> selectedCountries = FXCollections.observableArrayList();
        countriesObservableList.forEach(countries -> selectedCountries.add(countries.getCountryName()));

        ObservableList<FirstLevelDivisions> firstLevelDivisionsObservableList = null;
        try {
            firstLevelDivisionsObservableList = FirstLevelDivisionAccess.getAllLevelDivision();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<String> selectedStates = FXCollections.observableArrayList();
        firstLevelDivisionsObservableList.forEach(firstLevelDivisions -> selectedStates.add(firstLevelDivisions.getDivisionName()));

        updateCustomerCountry.setItems(selectedCountries);
        updateCustomerState.setItems(selectedStates);
        updateCustomerId.setDisable(true);
    }

    public void onSaveAction(ActionEvent actionEvent) throws SQLException, IOException {


        try {
            Connection connection = JDBC.connection;
            FirstLevelDivisions f = new FirstLevelDivisions();

            if (!updateCustomerId.getText().isEmpty() && !updateCustomerName.getText().isEmpty() && !updateCustomerAddress.getText().isEmpty() && !updateCustomerPhone.getText().isEmpty()
                    && !updateCustomerPostal.getText().isEmpty() && !updateCustomerCountry.getValue().isEmpty() && !updateCustomerState.getValue().isEmpty()) {

                ObservableList<FirstLevelDivisions> getAllDivisions = FirstLevelDivisionAccess.getAllLevelDivision();
                ObservableList<String> divisionId = FXCollections.observableArrayList();

                String s = updateCustomerState.getValue();

                String sql = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);

                ps.setInt(1, Integer.parseInt(updateCustomerId.getText()));
                ps.setString(2, updateCustomerName.getText());
                ps.setString(3, updateCustomerAddress.getText());
                ps.setString(4, updateCustomerPostal.getText());
                ps.setString(5, updateCustomerPhone.getText());
                ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(8, "admin");
                ps.setString(9, FirstLevelDivisionAccess.findDivisionIdByState(String.valueOf(s)));
                ps.setInt(10, Integer.parseInt(updateCustomerId.getText()));
                ps.execute();

            }

            Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 600);
            stage.setTitle("Main Menu");
            stage.setScene(scene);

        } catch (SQLException throwables) {
        throwables.printStackTrace();
        } catch (IOException e) {
        throw new RuntimeException(e);
        }

    }

    public void onCancelAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Update Cancellation");
        alert.setContentText("Cancel updating customer? You will be returned to the main menu.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 600);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
        } else {
            System.out.println("You clicked cancel. Please complete update form.");
        }
    }
}
