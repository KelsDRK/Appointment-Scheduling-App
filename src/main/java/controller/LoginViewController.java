package controller;

import DAO.UserAccess;
import helper.Query;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML private Button loginButtonMain;
    @FXML private Button resetButtonMain;
    @FXML private Button exitButtonMainMenu;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Text timeZoneMenuText;
    @FXML private Text usernameText;
    @FXML private Text passwordText;
    @FXML private Text timeZoneLabel;
    @FXML private Text languageText;



    @FXML private ChoiceBox<String> languageChoiceBox;
    private String[] languageOptions = {"English", "French"};


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        languageChoiceBox.getItems().addAll(languageOptions);
        languageChoiceBox.setOnAction(this::languageSelect);
        ZoneId myZoneId = ZoneId.systemDefault();
        timeZoneMenuText.setText(myZoneId.toString());

        ResourceBundle english = ResourceBundle.getBundle("/language/login", Locale.getDefault());
        ResourceBundle french = ResourceBundle.getBundle("/language/login_FR", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr")) {
            usernameText.setText(french.getString("Username"));
            passwordText.setText(french.getString("Password"));
            timeZoneLabel.setText(french.getString("Location"));
            exitButtonMainMenu.setText(french.getString("Exit"));
            loginButtonMain.setText(french.getString("Login"));
            resetButtonMain.setText(french.getString("Reset"));
            languageChoiceBox.setValue("French");
            languageText.setText("Language");
        } else {
            languageChoiceBox.setValue("English");
        }

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
            languageText.setText(french.getString("Language"));
        } else if (Locale.getDefault().getLanguage().equals("en") || selectedLanguage.equals("English")) {
            usernameText.setText(english.getString("Username"));
            passwordText.setText(english.getString("Password"));
            timeZoneLabel.setText(english.getString("Time_Zone"));
            exitButtonMainMenu.setText(english.getString("Exit"));
            loginButtonMain.setText(english.getString("Login"));
            resetButtonMain.setText(english.getString("Reset"));
            languageText.setText(english.getString("Language"));
        }
    }

    public void OnLoginButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String selectedLanguage = languageChoiceBox.getValue();
        boolean loginAttempt = Query.logInVerification(username, password);
        int userId = UserAccess.userVerification(username, password);

        if (loginAttempt) {
            System.out.println("Login Successful");
            Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 600);
            stage.setScene(scene);
        }  else if (Locale.getDefault().getLanguage().equals("fr") || selectedLanguage.equals("French") && !loginAttempt) {
            System.out.println("Login Attempt failed");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Nom d'utilisateur ou mot de passe incorrect. Essayer à nouveau.");
            alert.showAndWait();
        } else if (!loginAttempt){
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
        String selectedLanguage = languageChoiceBox.getValue();
        if (Locale.getDefault().getLanguage().equals("fr") || selectedLanguage.equals("French")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Confirmation ");
            alert.setHeaderText("Confirmez la sortie !");
            alert.setContentText("Êtes-vous sûr de vouloir quitter?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Platform.exit();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Confirmation ");
            alert.setHeaderText("Confirm Exit!");
            alert.setContentText("Are you sure you want to exit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Platform.exit();
            }
        }
    }

    public void OnResetButtonClicked(ActionEvent actionEvent) {
        usernameField.clear();
        passwordField.clear();
    }


}