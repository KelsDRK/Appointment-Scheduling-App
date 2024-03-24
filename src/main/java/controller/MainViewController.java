package controller;

import com.sw2.c195.Main;
import helper.GlobalFunctions;
import helper.Query;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML private Button loginButtonMain;
    @FXML private Button resetButtonMain;
    @FXML private Button exitButtonMainMenu;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Text timeZoneMenuText;
    @FXML private Text usernameText;
    @FXML private Text passwordText;
    @FXML private Text timeZoneLabel;


    @FXML private ChoiceBox<String> languageChoiceBox;
    private String[] languageOptions = {"English", "French"};


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        languageChoiceBox.getItems().addAll(languageOptions);
        languageChoiceBox.setOnAction(this::languageSelect);

        ResourceBundle english = ResourceBundle.getBundle("/language/login", Locale.getDefault());
        ResourceBundle french = ResourceBundle.getBundle("/language/login_FR", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr")) {
            usernameText.setText(french.getString("Username"));
            passwordText.setText(french.getString("Password"));
            timeZoneLabel.setText(french.getString("Location"));
            exitButtonMainMenu.setText(french.getString("Exit"));
            loginButtonMain.setText(french.getString("Login"));
            resetButtonMain.setText(french.getString("Reset"));
        } else {
            return;
        }

        timeZoneMenuText.setText(MainViewController.userTimeZone());
    }

    public void languageSelect(ActionEvent e) {
        String selectedLanguage = languageChoiceBox.getValue();

        ResourceBundle english = ResourceBundle.getBundle("/language/login", Locale.getDefault());
        ResourceBundle french = ResourceBundle.getBundle("/language/login_FR", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr") || selectedLanguage.equals("French")) {
            usernameText.setText(french.getString("Username"));
            passwordText.setText(french.getString("Password"));
            timeZoneLabel.setText(french.getString("Time_Zone"));
            exitButtonMainMenu.setText(french.getString("Exit"));
            loginButtonMain.setText(french.getString("Login"));
            resetButtonMain.setText(french.getString("Reset"));
        } else if (Locale.getDefault().getLanguage().equals("en") || selectedLanguage.equals("English")) {
            usernameText.setText(english.getString("Username"));
            passwordText.setText(english.getString("Password"));
            timeZoneLabel.setText(english.getString("Time_Zone"));
            exitButtonMainMenu.setText(english.getString("Exit"));
            loginButtonMain.setText(english.getString("Login"));
            resetButtonMain.setText(english.getString("Reset"));
        }



    }

    public void OnLoginButtonClicked(ActionEvent actionEvent) throws SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean loginAttempt = Query.logInVerification(username, password);
        if (loginAttempt) {
            System.out.println("Login Successful");
        } else {
            System.out.println("Login Attempt failed");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Username or password incorrect. Try again.");
            alert.showAndWait();
        }
        usernameField.clear();
        passwordField.clear();
    }

    public void OnExitButtonClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation ");
        alert.setHeaderText("Confirm Exit!");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
        } else {
            System.out.println("You clicked cancel. Please complete form.");
        }
    }

    public void OnResetButtonClicked(ActionEvent actionEvent) {
        usernameField.clear();
        passwordField.clear();
    }

    public static String userTimeZone () {
        ZoneId myZoneId = ZoneId.systemDefault();
        return myZoneId.toString();
    }

}