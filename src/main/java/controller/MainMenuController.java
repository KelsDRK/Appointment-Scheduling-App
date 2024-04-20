package controller;

import DAO.*;
import helper.JDBC;
import javafx.beans.binding.ObjectExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainMenuController implements Initializable {

    @FXML public TableView<Customers> customersTable;
    @FXML public TableColumn<Customers, Integer> idCustomerColumn;
    @FXML public TableColumn<Customers, String> phoneNumberCustomerColumn;
    @FXML public TableColumn<Customers, String>nameCustomerColumn;
    @FXML public TableColumn<Customers, String>addressCustomerColumn;
    @FXML public TableColumn<Customers, String>stateProvinceCustomerColumn;
    @FXML public TableColumn<Customers, String>postalCodeCustomerColumn;


    @FXML public TableView<Appointments> appointmentsTable;
    @FXML public TableColumn<Appointments, Integer> idAppointmentColumn;
    @FXML public TableColumn<Appointments, String> titleAppointmentColumn;
    @FXML public TableColumn<Appointments, String> typeAppointmentColumn;
    @FXML public TableColumn<Appointments, String> descriptionAppointmentColumn;
    @FXML public TableColumn<Appointments, String> locationAppointmentColumn;
    @FXML public TableColumn<Appointments, LocalDateTime> endDateTimeAppointmentColumn;
    @FXML public TableColumn<Appointments, LocalDateTime> startDateTimeAppointmentColumn;
    @FXML public TableColumn<Contacts, String> contactAppointmentColumn;
    @FXML public TableColumn<Appointments, Integer> customerIdAppointmentColumn;
    @FXML public TableColumn<Appointments, Integer> userIdAppointmentColumn;

    @FXML private Button reportsButton;
    @FXML private Button logoutButton;
    @FXML private Button addCustomersButton;
    @FXML private Button updateCustomersButton;
    @FXML private Button addAppointmentButton;
    @FXML private Button deleteCustomersButton;
    @FXML private Button updateAppointmentButton;
    @FXML private Button deleteAppointmentButton;
    @FXML private RadioButton allAppointmentsRadioButton;
    @FXML private RadioButton currentMonthRadioButton;
    @FXML private RadioButton currentWeekRadioButton;

    private static Appointments appointment;
    public static Appointments getAppointment() {return appointment;}

    private static Customers customer;
    public static Customers getCustomer() {return customer;}




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection connection = JDBC.connection;

            ObservableList<Appointments> allApoointmentsObservableList = AppointmentAccess.getAllAppointments();
            ObservableList<Customers> allCustomersObservableList = CustomersAccess.getAllCustomers(connection);

            idCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            phoneNumberCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
            nameCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            addressCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            stateProvinceCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
            postalCodeCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));

            idAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            typeAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            descriptionAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
            locationAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
            endDateTimeAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            startDateTimeAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));

            contactAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));

            customerIdAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            userIdAppointmentColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

            customersTable.setItems(allCustomersObservableList);
            appointmentsTable.setItems(allApoointmentsObservableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void onReportsButtonAction(ActionEvent actionEvent) {
    }

    public void onLogoutButtonAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation ");
        alert.setHeaderText("Confirm Logout!");
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/loginPage.fxml"));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 800);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
        }
    }

    public void onAddAppointmentButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addAppointments.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 475, 650);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
    }

    public void onAddCustomersButtonAction(ActionEvent actionEvent) {
    }

    public void onUpdateCustomersButtonAction(ActionEvent actionEvent) {
    }

    public void onDeleteCustomersButtonAction(ActionEvent actionEvent) {
    }

    public void onUpdateAppointmentButton(ActionEvent actionEvent) throws IOException {

        appointment = appointmentsTable.getSelectionModel().getSelectedItem();

        if (appointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No appointment selected, Choose a appointment from the table to update.");
            alert.showAndWait();
        } else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/updateAppointment.fxml")));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 475, 650);
            stage.setTitle("Update Appointment");
            stage.setScene(scene);
        }

    }

    public void onDeleteAppointmentButton(ActionEvent actionEvent) throws SQLException, IOException {

        if (appointmentsTable.getSelectionModel().getSelectedItem() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.NONE);
            alert.setTitle("Error");
            alert.setContentText("Select appointment to be deleted.");
            Optional<ButtonType> result = alert.showAndWait();

            System.out.println("working");
        } else {

            Connection connection = JDBC.connection;
            int appointmentId = appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Delete Appointment with the ID: " + appointmentId);
            alert.setContentText("Confirmation: Delete appointment?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

                AppointmentAccess.deleteAppointment(appointmentId, connection);
                ObservableList<Appointments> updatedAppointment = AppointmentAccess.getAllAppointments();
                appointmentsTable.setItems(updatedAppointment);

                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Appointment Deleted");
                alert.setContentText("Appointment was successfully deleted");
                Optional<ButtonType> result1 = alert.showAndWait();
            } else {
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cancelled");
                alert.setContentText("You clicked cancel. No appointment was deleted");
                Optional<ButtonType> result1 = alert.showAndWait();
            }
        }

    }

    public void onAllAppointmentsRadioButtonAction(ActionEvent actionEvent) {
        try {
            ObservableList<Appointments> allAppointments = AppointmentAccess.getAllAppointments();
            appointmentsTable.setItems(allAppointments);

            appointmentsTable.setItems(allAppointments);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onCurrentMonthRadioButtonAction(ActionEvent actionEvent) {
        try {
            ObservableList<Appointments> allAppointments = AppointmentAccess.getAllAppointments();
            ObservableList<Appointments> monthAppointments = FXCollections.observableArrayList();

            LocalDateTime startMonth = LocalDateTime.now().minusMonths(1);
            LocalDateTime endMonth = LocalDateTime.now().plusMonths(1);

            allAppointments.forEach(appointment -> {
                if (appointment.getEndDateTime().isAfter(startMonth) && appointment.getEndDateTime().isBefore(endMonth)) {
                    monthAppointments.add(appointment);
                }
                appointmentsTable.setItems(monthAppointments);
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void onCurrentWeekRadioButtonAction(ActionEvent actionEvent) {
        try {
            ObservableList<Appointments> allAppointments = AppointmentAccess.getAllAppointments();
            ObservableList<Appointments> weekAppointments = FXCollections.observableArrayList();

            LocalDateTime startWeek = LocalDateTime.now().minusWeeks(1);
            LocalDateTime endWeek = LocalDateTime.now().plusWeeks(1);

            allAppointments.forEach(appointment -> {
                if (appointment.getEndDateTime().isAfter(startWeek) && appointment.getEndDateTime().isBefore(endWeek)) {
                    weekAppointments.add(appointment);
                }
                appointmentsTable.setItems(weekAppointments);
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
