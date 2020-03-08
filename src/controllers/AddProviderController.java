package controllers;

import DB.ProvidersDAO;
import Model.Providers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddProviderController implements ProvidersDAO {

    @FXML private TextField providerNameTextField;
    @FXML private TextField nitTextField;
    @FXML private TextField phonenumberTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField cityTextField;

    public void addProvider(ActionEvent actionEvent) {

        if(addressTextField.getText().isEmpty()|| nitTextField.getText().isEmpty()
        ||providerNameTextField.getText().isEmpty()||phonenumberTextField.getText().isEmpty()
        ||emailTextField.getText().isEmpty()||cityTextField.getText().isEmpty()){
            String name = providerNameTextField.getText();
            String nit = nitTextField.getText();
            String phonenumber = phonenumberTextField.getText();
            String address = addressTextField.getText();
            String email = emailTextField.getText();
            String city = cityTextField.getText();

            Providers provider = new Providers(null, name, nit, email, phonenumber, city, address);

            addNewProvider(provider);
        }
    }
}
