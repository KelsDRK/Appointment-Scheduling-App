package com.sw2.c195;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 450);
        stage.setTitle("Appointment Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {

        JDBC.openConnection();
        launch();
        JDBC.closeConnection();

        /*ResourceBundle rb = ResourceBundle.getBundle("/language/login", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr")) {
            System.out.println(rb.getString("Login"));
        }*/

//        LocalDate myLocalDate = LocalDate.of(2024, 03, 23);
//        LocalTime myLocalTime = LocalTime.of(17, 0);
//        LocalDateTime myLocalDateTime = LocalDateTime.of(myLocalDate, myLocalTime);
//        ZoneId myZoneId = ZoneId.systemDefault();
//        ZonedDateTime myZonedDateTime = ZonedDateTime.of(myLocalDateTime, myZoneId);
//        System.out.println(myZonedDateTime);

    }
}