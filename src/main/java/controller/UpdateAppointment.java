package controller;

import DAO.AppointmentAccess;
import DAO.ContactsAccess;
import DAO.CustomersAccess;
import DAO.UserAccess;
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
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import static helper.Utility.convertTimeDateUTC;

public class UpdateAppointment implements Initializable {

    @FXML private TextField idTextFieldUpdateAppointment;
    @FXML private TextField titleTextFieldUpdateAppointment;
    @FXML private TextField typeTextFieldUpdateAppointment;
    @FXML private TextField descriptionTextFieldUpdateAppointment;
    @FXML private TextField locationTextFieldUpdateAppointment;
    @FXML private TextField customerIdTextFieldUpdateAppointment;
    @FXML private TextField userIdTextFieldUpdateAppointment;
    @FXML private ComboBox<String> startTimeCBUpdateAppointment;
    @FXML private ComboBox<String> endTimeCBUpdateAppointment;
    @FXML private ComboBox<String> contactCBUpdateAppointment;
    @FXML private DatePicker startDateFieldUpdateAppointment;
    @FXML private DatePicker endDateFieldUpdateAppointment;
    @FXML private Button saveButtonUpdateAppointment;
    @FXML private Button cancelButtonUpdateAppointment;

    private Appointments appointment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appointment = MainMenuController.getAppointment();

        idTextFieldUpdateAppointment.setText(String.valueOf(appointment.getAppointmentId()));
        titleTextFieldUpdateAppointment.setText(String.valueOf(appointment.getAppointmentTitle()));
        typeTextFieldUpdateAppointment.setText(String.valueOf(appointment.getAppointmentType()));
        descriptionTextFieldUpdateAppointment.setText(String.valueOf(appointment.getAppointmentDescription()));
        locationTextFieldUpdateAppointment.setText(String.valueOf(appointment.getAppointmentLocation()));
        customerIdTextFieldUpdateAppointment.setText(String.valueOf(appointment.getCustomerId()));
        userIdTextFieldUpdateAppointment.setText(String.valueOf(appointment.getUserId()));
        startTimeCBUpdateAppointment.getSelectionModel().select(String.valueOf(appointment.getStartDateTime().toLocalTime()));
        endTimeCBUpdateAppointment.getSelectionModel().select(String.valueOf(appointment.getEndDateTime().toLocalTime()));
        startDateFieldUpdateAppointment.setValue(appointment.getStartDateTime().toLocalDate());
        endDateFieldUpdateAppointment.setValue(appointment.getStartDateTime().toLocalDate());

        try {
            contactCBUpdateAppointment.getSelectionModel().select(String.valueOf(ContactsAccess.findNameByContactId(String.valueOf(appointment.getContactId()))));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<Contacts> appointmentsObservableList = null;
        try {appointmentsObservableList = ContactsAccess.getAllContacts();}
        catch (SQLException e) {throw new RuntimeException(e);}

        ObservableList<String> allContactNames = FXCollections.observableArrayList();
        appointmentsObservableList.forEach(contacts -> allContactNames.add(contacts.getContactName()));

        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();
        LocalTime firstAppointment = LocalTime.MIN.plusHours(8);
        LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45);

        if (!firstAppointment.equals(0) || !lastAppointment.equals(0)) {
            while (firstAppointment.isBefore(lastAppointment)) {
                appointmentTimes.add(String.valueOf(firstAppointment));
                firstAppointment = firstAppointment.plusMinutes(15);

        }
            }
                startTimeCBUpdateAppointment.setItems(appointmentTimes);
                endTimeCBUpdateAppointment.setItems(appointmentTimes);
                contactCBUpdateAppointment.setItems(allContactNames);
                idTextFieldUpdateAppointment.setDisable(true);
    }

public void onSaveActionUpdateAppointment(ActionEvent actionEvent) {

        try {

            Connection connection = JDBC.connection;

            if (!titleTextFieldUpdateAppointment.getText().isEmpty() && !descriptionTextFieldUpdateAppointment.getText().isEmpty() && !locationTextFieldUpdateAppointment.getText().isEmpty() && !typeTextFieldUpdateAppointment.getText().isEmpty()
                    && startDateFieldUpdateAppointment.getValue() != null && endDateFieldUpdateAppointment.getValue() != null && !startTimeCBUpdateAppointment.getValue().isEmpty() && !endTimeCBUpdateAppointment.getValue().isEmpty()
                    && !customerIdTextFieldUpdateAppointment.getText().isEmpty()) {

                ObservableList<Customers> getAllCustomers = CustomersAccess.getAllCustomers(connection);
                ObservableList<Integer> storeCustomerIDs = FXCollections.observableArrayList();
                ObservableList<UserAccess> getAllUsers = UserAccess.getAllUsers();
                ObservableList<Integer> storeUserIDs = FXCollections.observableArrayList();
                ObservableList<Appointments> getAllAppointments = AppointmentAccess.getAllAppointments();

                //IDE converted
                getAllCustomers.stream().map(Customers::getCustomerId).forEach(storeCustomerIDs::add);
                getAllUsers.stream().map(User::getUserId).forEach(storeUserIDs::add);

                LocalDate localDateEnd = endDateFieldUpdateAppointment.getValue();
                LocalDate localDateStart = startDateFieldUpdateAppointment.getValue();

                DateTimeFormatter minHourFormat = DateTimeFormatter.ofPattern("HH:mm");
                String appointmentStartDate = startDateFieldUpdateAppointment.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String appointmentStartTime = String.valueOf(startTimeCBUpdateAppointment.getValue());

                String endDate = endDateFieldUpdateAppointment.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String endTime = endTimeCBUpdateAppointment.getValue();

                System.out.println("thisDate + thisStart " + appointmentStartDate + " " + appointmentStartTime + ":00");
                String startUTC = convertTimeDateUTC(appointmentStartDate + " " + appointmentStartTime + ":00");
                String endUTC = convertTimeDateUTC(endDate + " " + endTime + ":00");

                LocalTime localTimeStart = LocalTime.parse(startTimeCBUpdateAppointment.getValue(), minHourFormat);
                LocalTime LocalTimeEnd = LocalTime.parse(endTimeCBUpdateAppointment.getValue(), minHourFormat);

                LocalDateTime dateTimeStart = LocalDateTime.of(localDateStart, localTimeStart);
                LocalDateTime dateTimeEnd = LocalDateTime.of(localDateEnd, LocalTimeEnd);

                ZonedDateTime zoneDtStart = ZonedDateTime.of(dateTimeStart, ZoneId.systemDefault());
                ZonedDateTime zoneDtEnd = ZonedDateTime.of(dateTimeEnd, ZoneId.systemDefault());

                ZonedDateTime convertStartEST = zoneDtStart.withZoneSameInstant(ZoneId.of("America/New_York"));
                ZonedDateTime convertEndEST = zoneDtEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

                LocalTime startAppointmentTimeToCheck = convertStartEST.toLocalTime();
                LocalTime endAppointmentTimeToCheck = convertEndEST.toLocalTime();

                DayOfWeek startAppointmentDayToCheck = convertStartEST.toLocalDate().getDayOfWeek();
                DayOfWeek endAppointmentDayToCheck = convertEndEST.toLocalDate().getDayOfWeek();

                int startAppointmentDayToCheckInt = startAppointmentDayToCheck.getValue();
                int endAppointmentDayToCheckInt = endAppointmentDayToCheck.getValue();

                int workWeekStart = DayOfWeek.MONDAY.getValue();
                int workWeekEnd = DayOfWeek.FRIDAY.getValue();

                LocalTime estBusinessStart = LocalTime.of(8, 0, 0);
                LocalTime estBusinessEnd = LocalTime.of(22, 0, 0);

                if (startAppointmentDayToCheckInt < workWeekStart || startAppointmentDayToCheckInt > workWeekEnd || endAppointmentDayToCheckInt < workWeekStart || endAppointmentDayToCheckInt > workWeekEnd) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Day is outside of business operations (Monday-Friday)");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("day is outside of business hours");
                    return;
                }

                if (startAppointmentTimeToCheck.isBefore(estBusinessStart) || startAppointmentTimeToCheck.isAfter(estBusinessEnd) || endAppointmentTimeToCheck.isBefore(estBusinessStart) || endAppointmentTimeToCheck.isAfter(estBusinessEnd)) {
                    System.out.println("time is outside of business hours");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Time is outside of business hours (8am-10pm EST): " + startAppointmentTimeToCheck + " - " + endAppointmentTimeToCheck + " EST");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    return;
                }

                int newAppointmentID = Integer.parseInt(String.valueOf((int) (Math.random() * 100)));
                int customerID = Integer.parseInt(customerIdTextFieldUpdateAppointment.getText());

                if (dateTimeStart.isAfter(dateTimeEnd)) {
                    System.out.println("Appointment has start time after end time");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has start time after end time");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    return;
                }

                if (dateTimeStart.isEqual(dateTimeEnd)) {
                    System.out.println("Appointment has same start and end time");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has same start and end time");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    return;
                }
                for (Appointments appointment : getAllAppointments) {
                    LocalDateTime checkStart = appointment.getStartDateTime();
                    LocalDateTime checkEnd = appointment.getEndDateTime();

                    //"outer verify" meaning check to see if an appointment exists between start and end.
                    if ((customerID == appointment.getCustomerId()) && (newAppointmentID != appointment.getAppointmentId()) &&
                            (dateTimeStart.isBefore(checkStart)) && (dateTimeEnd.isAfter(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment overlaps with existing appointment.");
                        return;
                    }

                    if ((customerID == appointment.getCustomerId()) && (newAppointmentID != appointment.getAppointmentId()) &&
//                            Clarification on isEqual is that this does not count as an overlapping appointment
//                            (dateTimeStart.isEqual(checkStart) || dateTimeStart.isAfter(checkStart)) &&
//                            (dateTimeStart.isEqual(checkEnd) || dateTimeStart.isBefore(checkEnd))) {
                            (dateTimeStart.isAfter(checkStart)) && (dateTimeStart.isBefore(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Start time overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Start time overlaps with existing appointment.");
                        return;
                    }


                    if (customerID == appointment.getCustomerId() && (newAppointmentID != appointment.getAppointmentId()) &&
//                            Clarification on isEqual is that this does not count as an overlapping appointment
//                            (dateTimeEnd.isEqual(checkStart) || dateTimeEnd.isAfter(checkStart)) &&
//                            (dateTimeEnd.isEqual(checkEnd) || dateTimeEnd.isBefore(checkEnd)))
                            (dateTimeEnd.isAfter(checkStart)) && (dateTimeEnd.isBefore(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "End time overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("End time overlaps with existing appointment.");
                        return;
                    }
                }

                String sql = "UPDATE appointments SET Appointment_ID = ?, Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

                PreparedStatement ps = JDBC.connection.prepareStatement(sql);

                ps.setInt(1, Integer.parseInt(idTextFieldUpdateAppointment.getText()));
                ps.setString(2, titleTextFieldUpdateAppointment.getText());
                ps.setString(3, descriptionTextFieldUpdateAppointment.getText());
                ps.setString(4, locationTextFieldUpdateAppointment.getText());
                ps.setString(5, typeTextFieldUpdateAppointment.getText());
                ps.setTimestamp(6, Timestamp.valueOf(startUTC));
                ps.setTimestamp(7, Timestamp.valueOf(endUTC));
                ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(9, "admin");
                ps.setInt(10, Integer.parseInt(customerIdTextFieldUpdateAppointment.getText()));
                ps.setInt(11, Integer.parseInt(userIdTextFieldUpdateAppointment.getText()));
                ps.setInt(12, Integer.parseInt(ContactsAccess.findByContactId(contactCBUpdateAppointment.getValue())));
                ps.setInt(13, Integer.parseInt(idTextFieldUpdateAppointment.getText()));
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

    public void onCancelActionUpdateAppointment(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Update Cancellation");
        alert.setContentText("Cancel updating appointment? You will be returned to the main menu.");
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
