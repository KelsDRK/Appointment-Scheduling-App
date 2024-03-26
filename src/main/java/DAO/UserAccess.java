package DAO;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class UserAccess extends User {
    public UserAccess(int userId, String username, String password) {
        super(userId, username, password);
    }


    public static int userVerification(String username, String password) {
        try
        {
            String sql = "SELECT * FROM USERS WHERE User_Name = ? AND Password = ?";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getString("User_Name").equals(username))
            {
                if (rs.getString("Password").equals(password))
                {
                    return rs.getInt("User_ID");

                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public static ObservableList<UserAccess> getAllUsers() throws SQLException {
        ObservableList<UserAccess> allUsers = FXCollections.observableArrayList();

        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("User_ID");
            String name = rs.getString("User_Name");
            String password = rs.getString("Password");
            UserAccess user = new UserAccess(id, name, password);
            allUsers.add(user);
        }
        return allUsers;
    }


}
