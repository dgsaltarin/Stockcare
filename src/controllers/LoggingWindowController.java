package controllers;

import Model.Alerts;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class LoggingWindowController implements Initializable {

    @FXML
    private Button loggingButton;
    @FXML
    private ComboBox typeOfUserCB;
    @FXML
    private TextField userTextField;
    @FXML
    private PasswordField passwordTextField;

    /**
     * receive the parameters to validate the logging and them load the corresponding principal interface
     * */
    public void loadPrincipalInterface() throws IOException, NoSuchAlgorithmException {
        if (!typeOfUserCB.getSelectionModel().isEmpty()) {
            Boolean correctLogging = authenticateLogging(userTextField.getText(), passwordTextField.getText(),
                    typeOfUserCB.getSelectionModel().getSelectedItem().toString());

            if (correctLogging==true) {
                int typeOfUserIndex = typeOfUserCB.getSelectionModel().getSelectedIndex();
                Stage stage = new Stage();

                String uiUrl;
                if (typeOfUserIndex == 1 || typeOfUserIndex == 2) {
                    uiUrl = "Ui/PrincipalInterface.fxml";
                } else {
                    uiUrl = "Ui/PrincipalInterfaceForAuxiliar.fxml";
                }


                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(uiUrl));
                stage.setScene(new Scene(root));
                stage.setTitle("StockCare");
                stage.getIcons().add(new Image("images/application_icon.png"));
                stage.setMaximized(true);
                Stage loggingStage = (Stage) loggingButton.getScene().getWindow();
                loggingStage.close();
                stage.show();
            }
            else {
                Alerts.notSelectionAlert("Usuario o contraseña incorrectos");
                return;
            }
        } else
            Alerts.notSelectionAlert("Debe llenar todos los campos primero!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> observableList = FXCollections.observableArrayList("Auxiliar", "Químico Farmacéutico", " Gerente");
        typeOfUserCB.setItems(observableList);
    }

    /**
     * Receive the user and the password and search the user in the data base and compare the passwords, if there is match
     * then return a true value and set the current user as a global variable
     * */
    private boolean authenticateLogging(String user, String password, String typeOfUser) throws NoSuchAlgorithmException {
        boolean isLoggingCorrect = false;

        Users usersForAuthenticate = new Users();
        String encryptedPassword = usersForAuthenticate.encryptPassword(password);
        usersForAuthenticate = usersForAuthenticate.getUserByUser(user);

        if (usersForAuthenticate.getTypeOfUser().equals(typeOfUser)) {
            if (encryptedPassword.equals(usersForAuthenticate.getPassword())) {
                isLoggingCorrect = true;
                usersForAuthenticate.setCurrentUser(usersForAuthenticate);
            }
        } else
            Alerts.notSelectionAlert("Tipo de usuario Incorrecto");


        return isLoggingCorrect;
    }

    /**
     * shot down the application
     * */
    public void cancelLogging() {
        System.exit(0);
    }
}
