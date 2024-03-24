package helper;

import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Objects;

public class Query {

    public Query() {
    }

    public static boolean logInVerification (String username, String password) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String usernameCheck = rs.getString("User_Name");
            String passwordCheck = rs.getString("Password");
            if (Objects.equals(usernameCheck, username) && Objects.equals(passwordCheck, password)) {
                //System.out.println("Login successful");
                return true;
            } else if (!Objects.equals(passwordCheck, password)) {
                //System.out.println("Incorrect password. Try again.");
                return false;
            }
        }
        return false;
    }

    public static void userTimeZone () throws SQLException {

        //FIGURE OUT USER ZONE ID
        ZoneId zId = ZoneId.systemDefault();
        String zoneId = zId.getId();

        //COMPARE TO SQL CHART
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, zoneId);

        // UPDATE MAIN FORM TO MATCH ZONEID

    }




}
