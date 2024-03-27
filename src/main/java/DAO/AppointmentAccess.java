package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AppointmentAccess {

    public static ObservableList<Appointments> getAllAppointments() throws SQLException, SQLException {
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            //LocalDateTime start = convertTimeDateLocal(rs.getTimestamp("Start").toLocalDateTime());
            LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
            //LocalDateTime endDateTime = convertTimeDateLocal(rs.getTimestamp("End").toLocalDateTime());
            LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(appointmentId, appointmentTitle, appointmentType, appointmentDescription,
                    appointmentLocation, customerId, userId, contactId, startDateTime, endDateTime);
            appointmentsObservableList.add(appointment);
        }

        return appointmentsObservableList;
    }

    public static int deleteAppointment(int customerId, Connection c) throws SQLException {
        String sql= "DELETE FROM APPOINTMENT WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int result = ps.executeUpdate();
        ps.close();
        return result;
    }



}
