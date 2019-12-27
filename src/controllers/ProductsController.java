package controllers;

import DB.ProductsDAO;
import Model.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ProductsController implements ProductsDAO {

    @FXML private RadioButton medicamentos;
    @FXML private RadioButton dispositivo_medico;
    @FXML private RadioButton insumos;
    @FXML private TableView<Products> tableProducts;
    @FXML private Button acceptButton;
    @FXML private TableColumn<Products, Integer> codeColumn;
    @FXML private TableColumn<Products, String> nameColumn;
    @FXML private TableColumn<Products, Double> priceColumn;
    @FXML private TableColumn<Products, String> clasificationColumn;

    public void fillTable(ActionEvent actionEvent) {
        String typeofProduct = "";
        if (medicamentos.isSelected()){
            typeofProduct = "medicamento";
        }
        if (dispositivo_medico.isSelected()){
            typeofProduct = "dispotivo medico";
        }
        if (insumos.isSelected()){
            typeofProduct = "insumo";
        }
        else {
            System.out.println("debe seleccionar alguna de las categorias de products");
        }

        ObservableList<Products> productsList = FXCollections.observableArrayList(productsList(typeofProduct));

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        clasificationColumn.setCellValueFactory(new PropertyValueFactory<>("clasification"));

        tableProducts.setItems(productsList);
    }
}
