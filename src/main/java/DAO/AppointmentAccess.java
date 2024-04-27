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
        String sql = "SELECT "
                +"a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Start, a.End, a.Customer_ID, c.Contact_Name, a.user_id, a.contact_id"
                +" from appointments AS a JOIN CONTACTS AS c ON c.contact_id = a.contact_id";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID");
            String appointmentTitle = rs.getString("Title");
            String appointmentDescription = rs.getString("Description");
            String appointmentLocation = rs.getString("Location");
            String appointmentType = rs.getString("Type");
            LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
            int customerId = rs.getInt("customer_id");
            String contactName = rs.getString("Contact_Name");
            System.out.println(contactName);
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");
            Appointments appointment = new Appointments(appointmentId, appointmentTitle, appointmentType, appointmentDescription,
                    appointmentLocation, customerId, contactName, userId, contactId, startDateTime, endDateTime);
            appointmentsObservableList.add(appointment);
        }
        return appointmentsObservableList;
    }

    public static int deleteAppointment (int appointmentId, Connection c) throws SQLException {
        String sql= "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int result = ps.executeUpdate();
        ps.close();
        return result;
    }



}
