package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import model.Appointments;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointments test1 = new Appointments(10, "Test", "Test",
                "Test", "Test", 10, 10, 10, 10, 10);

    }

    private void addTestData () {


        Appointments test1 = new Appointments(10, "Test", "Test",
                "Test", "Test", 10, 10, 10, 10, 10);
    }



    public void onReportsButtonAction(ActionEvent actionEvent) {
    }

    public void onLogoutButtonAction(ActionEvent actionEvent) {
    }

    public void onAddAppointmentButton(ActionEvent actionEvent) {
    }

    public void onAddCustomersButtonAction(ActionEvent actionEvent) {
    }

    public void onUpdateCustomersButtonAction(ActionEvent actionEvent) {
    }

    public void onDeleteCustomersButtonAction(ActionEvent actionEvent) {
    }

    public void onUpdateAppointmentButton(ActionEvent actionEvent) {
    }

    public void onDeleteAppointmentButton(ActionEvent actionEvent) {
    }

    public void onAllAppointmentsRadioButtonAction(ActionEvent actionEvent) {
    }

    public void onCurrentMonthRadioButtonAction(ActionEvent actionEvent) {
    }

    public void onCurrentWeekRadioButtonAction(ActionEvent actionEvent) {
    }
}
