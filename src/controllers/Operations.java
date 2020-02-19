package controllers;

import Model.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Operations implements Initializable {

    protected ObservableList<String> typeOfProducts = FXCollections.observableArrayList("medicamento", "dispositivo medico",
            "insumo");

    @FXML protected ComboBox typeOfProductCB;
    @FXML private ComboBox providersCB;
    @FXML protected TextField filterTextField;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeOfProductCB.setItems(typeOfProducts);
    }
}
