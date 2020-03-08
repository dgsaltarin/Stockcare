package controllers;

import DB.ProductsDAO;
import Model.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController extends Operations implements Initializable, ProductsDAO {

    @FXML private TextField productNameTextField;
    @FXML private TextField productCodeTextField;
    @FXML private TextField manufacturerTextField;
    @FXML private TextField priceTextField;
    @FXML private ComboBox venCB;

    private ObservableList<String> venClassification = FXCollections.observableArrayList("V", "E","N");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeOfProductCB.setItems(typeOfProducts);
        venCB.setItems(venClassification);
    }

    public void addProduct(ActionEvent actionEvent) {

        String productName = productNameTextField.getText();
        Integer productCode = Integer.parseInt(productCodeTextField.getText());
        String manufacturer = manufacturerTextField.getText();
        Double producPrice = Double.valueOf(priceTextField.getText());
        String typeOfProduct = typeOfProductCB.getSelectionModel().getSelectedItem().toString();
        String classificationVEN;

        if(typeOfProduct.equals("medicamento")){
            classificationVEN = venCB.getSelectionModel().getSelectedItem().toString();
        } else {
            classificationVEN = "";
        }

        Products product = new Products(productCode, productName, producPrice, classificationVEN, manufacturer);

        addNewProduct(product);
    }
}
