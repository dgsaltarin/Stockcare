package controllers;

import DB.UserDAO;
import Model.Alerts;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class RemoveUserController implements Initializable, UserDAO {

    @FXML
    private ComboBox<Users> usersListCB;
    @FXML
    private PasswordField userPasswordTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Users> usersList = FXCollections.observableArrayList(getUsersList());
        usersListCB.setItems(usersList);
    }

    /**
     * validate the password for the current user and if it's correct, call the method to remove and user
     * */
    public void RemoveUser() throws NoSuchAlgorithmException {
        Users users = new Users();
        String userPassword = userPasswordTextField.getText();
        if (users.encryptPassword(userPassword).equals(users.currentUser.getPassword())){
            users.removeUser(usersListCB.getSelectionModel().getSelectedItem().getId());
            Stage stage = (Stage) usersListCB.getScene().getWindow();
            stage.close();
        } else
            Alerts.notSelectionAlert("Contraseña incorrecta!");
    }
}
