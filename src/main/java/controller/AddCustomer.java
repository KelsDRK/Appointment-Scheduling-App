package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCustomer {
    public void onSaveAction(ActionEvent actionEvent) {
    }

    public void onCancelAction(ActionEvent actionEvent) throws IOException {
        System.out.println("Login Successful");
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
    }
}
