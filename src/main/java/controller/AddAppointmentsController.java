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
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import static helper.Utility.convertTimeDateUTC;

public class AddAppointmentsController implements Initializable {

    @FXML private TextField idTextFieldAddAppointment;
    @FXML private TextField titleTextFieldAddAppointment;
    @FXML private TextField typeTextFieldAddAppointment;
    @FXML private TextField descriptionTextFieldAddAppointment;
    @FXML private TextField locationTextFieldAddAppointment;
    @FXML private TextField customerIdTextFieldAddAppointment;
    @FXML private TextField userIdTextFieldAddAppointment;
    @FXML private ComboBox<String> startTimeCBAddAppointment;
    @FXML private ComboBox<String> endTimeCBAddAppointment;
    @FXML private ComboBox<String> contactCBAddAppointment;
    @FXML private DatePicker startDateFieldAddAppointment;
    @FXML private DatePicker endDateFieldAddAppointment;
    @FXML private Button saveButtonAddAppointment;
    @FXML private Button cancelButtonAddAppointment;

    Random random = new Random();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idTextFieldAddAppointment.setDisable(true);
        idTextFieldAddAppointment.setText(getNewAppointmentID());

        ObservableList<Contacts> contactsObservableList = null;
        try {
            contactsObservableList = ContactsAccess.getAllContacts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList<String> allContactNames = FXCollections.observableArrayList();

        //lambda #2
        contactsObservableList.forEach(contacts -> allContactNames.add(contacts.getContactName()));

        ObservableList<String> appointmentTimes = FXCollections.observableArrayList();

        LocalTime firstAppointment = LocalTime.MIN.plusHours(8);
        LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45);

        if (!firstAppointment.equals(0) || !lastAppointment.equals(0)) {
            while (firstAppointment.isBefore(lastAppointment)) {
                appointmentTimes.add(String.valueOf(firstAppointment));
                firstAppointment = firstAppointment.plusMinutes(15);
            }
        }
        startTimeCBAddAppointment.setItems(appointmentTimes);
        endTimeCBAddAppointment.setItems(appointmentTimes);
        contactCBAddAppointment.setItems(allContactNames);
    }

    /**
     *
     * @param actionEvent
     * @throws SQLException
     * Checks that required fields are non-null
     * Parses data from form and converts times
     * Inserts into DB using parsed values
     */
    public void onSaveActionAddAppointment(ActionEvent actionEvent) throws SQLException {

        try {

            Connection connection = JDBC.connection;

            if (!titleTextFieldAddAppointment.getText().isEmpty() && !descriptionTextFieldAddAppointment.getText().isEmpty() && !locationTextFieldAddAppointment.getText().isEmpty() && !typeTextFieldAddAppointment.getText().isEmpty()
                    && startDateFieldAddAppointment.getValue() != null && endDateFieldAddAppointment.getValue() != null && !startTimeCBAddAppointment.getValue().isEmpty() && !endTimeCBAddAppointment.getValue().isEmpty()
                    && !customerIdTextFieldAddAppointment.getText().isEmpty()) {

                ObservableList<Customers> getAllCustomers = CustomersAccess.getAllCustomers(connection);
                ObservableList<Integer> storeCustomerIDs = FXCollections.observableArrayList();
                ObservableList<UserAccess> getAllUsers = UserAccess.getAllUsers();
                ObservableList<Integer> storeUserIDs = FXCollections.observableArrayList();
                ObservableList<Appointments> getAllAppointments = AppointmentAccess.getAllAppointments();

                getAllCustomers.stream().map(Customers::getCustomerId).forEach(storeCustomerIDs::add);
                getAllUsers.stream().map(User::getUserId).forEach(storeUserIDs::add);

                LocalDate localDateEnd = endDateFieldAddAppointment.getValue();
                LocalDate localDateStart = startDateFieldAddAppointment.getValue();

                DateTimeFormatter minHourFormat = DateTimeFormatter.ofPattern("HH:mm");
                String appointmentStartDate = startDateFieldAddAppointment.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String appointmentStartTime = String.valueOf(startTimeCBAddAppointment.getValue());

                String endDate = endDateFieldAddAppointment.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String endTime = endTimeCBAddAppointment.getValue();

                System.out.println("thisDate + thisStart " + appointmentStartDate + " " + appointmentStartTime + ":00");
                String startUTC = convertTimeDateUTC(appointmentStartDate + " " + appointmentStartTime + ":00");
                String endUTC = convertTimeDateUTC(endDate + " " + endTime + ":00");

                LocalTime localTimeStart = LocalTime.parse(startTimeCBAddAppointment.getValue(), minHourFormat);
                LocalTime LocalTimeEnd = LocalTime.parse(endTimeCBAddAppointment.getValue(), minHourFormat);

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
                int customerID = Integer.parseInt(customerIdTextFieldAddAppointment.getText());

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

                String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, " +
                        "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES " +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, newAppointmentID);
                ps.setString(2, titleTextFieldAddAppointment.getText());
                ps.setString(3, descriptionTextFieldAddAppointment.getText());
                ps.setString(4, locationTextFieldAddAppointment.getText());
                ps.setString(5, typeTextFieldAddAppointment.getText());
                ps.setTimestamp(6, Timestamp.valueOf(startUTC));
                ps.setTimestamp(7, Timestamp.valueOf(endUTC));
                ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(9, "admin");
                ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(11,  "admin");
                ps.setInt(12, Integer.parseInt(customerIdTextFieldAddAppointment.getText()));
                ps.setInt(13, Integer.parseInt(ContactsAccess.findByContactId(contactCBAddAppointment.getValue())));
                ps.setInt(14, Integer.parseInt(ContactsAccess.findByContactId(userIdTextFieldAddAppointment.getText())));
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

    public void onCancelActionAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
    }

    /**
     * Gets random int to set as ID
     * @return
     */
    public String getNewAppointmentID() {
        int productId = random.nextInt();
        boolean partFound = false;
        // the new ID will be 4 digits or fewer
        while (productId < 1000 || productId > 9999) {
            productId = random.nextInt();
        }
        return Integer.toString(productId);
    }

}
