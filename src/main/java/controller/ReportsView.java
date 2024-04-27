package controller;

import DAO.AppointmentAccess;
import DAO.ContactsAccess;
import DAO.FirstLevelDivisionAccess;
import DAO.ReportAccess;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReportsView implements Initializable {

    @FXML public TableView<Appointments> reportsTable;
    @FXML public TableColumn<Appointments, Integer> idColumn;
    @FXML public TableColumn<Appointments, String> titleColumn;
    @FXML public TableColumn<Appointments, String>typeColumn;
    @FXML public TableColumn<Appointments, String>descriptionColumn;
    @FXML public TableColumn<Appointments, String>locationColumn;
    @FXML public TableColumn<Appointments, String>startDateTimeColumn;
    @FXML public TableColumn<Appointments, String>endDateTimeColumn;
    @FXML public TableColumn<Appointments, String>customerIdColumn;

    @FXML public TableView<ReportsMonth> appointmentsTable;
    @FXML public TableColumn<Reports, Integer> appointmentMonthColumn;
    @FXML public TableColumn<Reports, String> appointmentTypeColumn;
    @FXML public TableColumn<Reports, String>totalAppointmentsColumn;

    @FXML public TableView<DivisionCount> divisionTable;
    @FXML public TableColumn<DivisionCount, String> divisionNameColumn;
    @FXML public TableColumn<DivisionCount, Integer> totalCustomersColumn;

    @FXML private Button reportsBackButton;
    @FXML private Button reportsLogoutButton;
    @FXML private ComboBox<String> reportsContactsBox;

    //private Appointments appointment = new Appointments();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Appointments> allAppointmentsObservableList = null;
        try {allAppointmentsObservableList = AppointmentAccess.getAllAppointments();
        } catch (SQLException e) {throw new RuntimeException(e);}

        ObservableList<Contacts> getAllContacts = null;
        try {getAllContacts = ContactsAccess.getAllContacts();
        } catch (SQLException e) {throw new RuntimeException(e);}


        ObservableList<String> allContacts = FXCollections.observableArrayList();
        getAllContacts.forEach(contacts -> allContacts.add(contacts.getContactName()));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        startDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        ObservableList<ReportsMonth> rm = null;
        try {rm = ReportAccess.getAllReportMonthData();
        } catch (SQLException e) {throw new RuntimeException(e);}

        appointmentMonthColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentMonth"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        totalAppointmentsColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentCount"));

        ObservableList<DivisionCount> divisionCounts = null;
        try {divisionCounts = FirstLevelDivisionAccess.getDivisionCount();
        } catch (SQLException e) {throw new RuntimeException(e);}

        divisionNameColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        totalCustomersColumn.setCellValueFactory(new PropertyValueFactory<>("customerCount"));

        divisionTable.setItems(divisionCounts);
        appointmentsTable.setItems(rm);
        reportsTable.setItems(allAppointmentsObservableList);
        reportsContactsBox.setItems(allContacts);
    }

    public void onBackAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Main Menu");
        stage.setScene(scene);

    }

    public void onLogoutAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation ");
        alert.setHeaderText("Confirm Logout");
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/loginPage.fxml"));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 450);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
        }
    }

    public void onReportsContactAction(ActionEvent actionEvent) throws SQLException {

        System.out.println(reportsContactsBox.getValue());
        String contactName = reportsContactsBox.getValue();
        Integer contactId = 0;

        String sql = "SELECT * FROM CONTACTS WHERE CONTACT_NAME = '" + contactName + "'";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs2 = ps.executeQuery();
        while (rs2.next()) {
            contactId = rs2.getInt("Contact_ID");
        }
        String sql2 = "SELECT * FROM APPOINTMENTS WHERE CONTACT_ID = '" + contactId + "'";
        PreparedStatement ps2 = JDBC.connection.prepareStatement(sql2);
        rs2 = ps2.executeQuery();
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();
        Appointments appointments = null;
        while (rs2.next()) {



            int appointmentId = rs2.getInt("Appointment_ID");
            String appointmentTitle = rs2.getString("Title");
            String appointmentDescription = rs2.getString("Description");
            String appointmentLocation = rs2.getString("Location");
            String appointmentType = rs2.getString("Type");
            LocalDateTime startDateTime = rs2.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDateTime = rs2.getTimestamp("End").toLocalDateTime();
            int customerId = rs2.getInt("Customer_ID");

            appointments = new Appointments(appointmentId, appointmentTitle, appointmentLocation, appointmentType,
                    appointmentDescription, customerId, startDateTime, endDateTime);


        }

        appointmentsObservableList.add(appointments);
        reportsTable.setItems(appointmentsObservableList);

    }

}
