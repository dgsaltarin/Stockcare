package controllers;

import Model.Alerts;
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

    public void loadPrincipalInterface() throws IOException {
        if (!typeOfUserCB.getSelectionModel().isEmpty()) {
            int typeOfUserIndex = typeOfUserCB.getSelectionModel().getSelectedIndex();
            Stage stage = new Stage();

            String uiUrl;
            if (typeOfUserIndex == 1 || typeOfUserIndex == 2) {
                uiUrl = "Ui/PrincipalInterface.fxml";
            } else {
                uiUrl = "Ui/PrincipalInterfaceForAuxiliar.fxml";
            }

            System.out.println(userTextField.getText());
            System.out.println(passwordTextField.getText());

            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(uiUrl));
            stage.setScene(new Scene(root));
            stage.setTitle("StockCare");
            stage.getIcons().add(new Image("images/application_icon.png"));
            stage.setMaximized(true);
            Stage loggingStage = (Stage) loggingButton.getScene().getWindow();
            loggingStage.close();
            stage.show();
        } else
            Alerts.notSelectionAlert("Debe llenar todos los campos primero!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> observableList = FXCollections.observableArrayList("Auxiliar", " Quimico Farmaceutico", " Gerente");
        typeOfUserCB.setItems(observableList);
    }

    public void cancelLogging() {
        System.exit(0);
    }
}
