package controller;

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
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCustomer implements Initializable {

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

    }

    public void onSaveAction(ActionEvent actionEvent) {

    }

    public void onCancelAction(ActionEvent actionEvent) throws IOException {
        System.out.println("Login Successful");
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
    }
}
