package controllers;

import DB.UserDAO;
import Model.Alerts;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserController implements Initializable, UserDAO {

    @FXML
    private TextField idTextField;
    @FXML
    private TextField userTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox<String> typeOfUserCB;
    @FXML
    private PasswordField password1TextField;
    @FXML
    private PasswordField password2TextField;

    /**
     * set the new user on the data base
     * */
    public void addUser() {
        Users users = new Users();
        if (!idTextField.getText().isEmpty() && !userTextField.getText().isEmpty() && !nameTextField.getText().isEmpty()
                && !password1TextField.getText().isEmpty() && !password2TextField.getText().isEmpty() && !typeOfUserCB.getSelectionModel().isEmpty()) {

            if (password1TextField.getText().equals(password2TextField.getText())) {
                users = new Users(Integer.valueOf(idTextField.getText()), nameTextField.getText(), typeOfUserCB.getSelectionModel().getSelectedItem(), password1TextField.getText(), userTextField.getText());
            } else
                Alerts.notSelectionAlert("Las contraseñas no coinciden, intentelo nuevamente!");

        } else
            Alerts.notSelectionAlert("Todos los campos son obligatorios!");

        users.addNewUser(users);
        Stage stage = (Stage) typeOfUserCB.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> observableList = FXCollections.observableArrayList("Axiliar", "Químico Farmacéutico", "Gerente");
        typeOfUserCB.setItems(observableList);
    }
}
