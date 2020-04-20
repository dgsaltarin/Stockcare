package controllers;

import DB.ProductsDAO;
import Model.Alerts;
import Model.Products;
import Model.PurchaseOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InComeCorrectionController extends Operations implements Initializable, ProductsDAO {

    @FXML
    private TableView<Products> productsTableView;
    @FXML
    private TableColumn productsTableColumn;
    @FXML
    private CheckBox productCheckbox;
    @FXML
    private CheckBox quantityCheckbox;
    @FXML
    private TextField quantityTextField;
    @FXML
    private ComboBox typeOfProductCB;
    @FXML
    private TextField filterTextField;
    @FXML
    public Button correctionButton;

    private PurchaseOrder purchaseOrderCorrected;

    /**
     * Enable and disable the correction options
     * */
    public void enableProductCorrection() {
        if (productCheckbox.isSelected()) {
            productsTableView.setDisable(false);
            typeOfProductCB.setDisable(false);
            filterTextField.setDisable(false);
        } else {
            productsTableView.setDisable(true);
            typeOfProductCB.setDisable(true);
            filterTextField.setDisable(true);
        }
    }

    public void enableQuantityCorrection() {
        if (quantityCheckbox.isSelected()) {
            quantityTextField.setDisable(false);
        } else
            quantityTextField.setDisable(true);
    }

    /***
     * @description get the list of products once the user select a categrory
     */
    public void fillProducts() {
        ObservableList<Products> productsList = FXCollections.observableArrayList(productsNames(typeOfProductCB.getValue().toString()));

        productsTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        productsTableView.setItems(productsList);

        FilteredList<Products> filteredData = new FilteredList<>(productsList, p -> true);
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Products -> {
                // If filter text is empty, display all products
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare product's name with the filter
                String lowerCaseFilter = newValue.toLowerCase();

                if (Products.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        SortedList<Products> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productsTableView.comparatorProperty());

        productsTableView.setItems(sortedData);
    }

    /**
     * evaluate the values and just admit the numerical values
     */
    public void evaluateValue() {
        quantityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            quantityTextField.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeOfProductCB.setItems(typeOfProducts);
    }

    /**
     * verify the entered information and made the corrections on the purchase order received
     * */
    public void madeCorrection() {
        if (productCheckbox.isSelected()) {
            Products product = productsTableView.getSelectionModel().getSelectedItem();
            if (product!=null) {
                purchaseOrderCorrected.setProductCode(product.getCode());
                purchaseOrderCorrected.setProductName(product.getName());
            } else {
                Alerts.notSelectionAlert("Debe sellecionar algun producto primero!");
                return;
            }
        }
        if (quantityCheckbox.isSelected()) {
            int quantity = Integer.parseInt(quantityTextField.getText());
            if (!quantityTextField.getText().isEmpty()) {
                purchaseOrderCorrected.setQuantity(quantity);
            } else {
                Alerts.notSelectionAlert("Debe ingresar la nueva cantidad!");
                return;
            }
        }

        Stage stage1 = (Stage) quantityTextField.getScene().getWindow();
        stage1.close();
    }

    /**
     * Receive the purchase order that is wanted to be correct
     * */
    public void initData(PurchaseOrder purchaseOrderToCorrect) {
        this.purchaseOrderCorrected = purchaseOrderToCorrect;
    }

    public int getQuantity() {
        int quantity = Integer.valueOf(quantityTextField.getText());
        return quantity;
    }

    public PurchaseOrder getPurchaseOrderCorrected() {
        return purchaseOrderCorrected;
    }
}
