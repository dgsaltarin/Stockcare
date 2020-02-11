package controllers;

import DB.ProductsDAO;
import DB.ProvidersDAO;
import DB.PurchaseOrderDAO;
import Model.Products;
import Model.PurchaseOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PurchaseOrderController implements Initializable, ProvidersDAO, ProductsDAO, PurchaseOrderDAO {

    private ObservableList<String> typeOfProducts = FXCollections.observableArrayList("medicamento", "dispositivo medico",
            "insumo");
    @FXML private ComboBox typeOfProductCB;
    @FXML private ComboBox providersCB;
    @FXML private TableView<Products> productsTableView;
    @FXML private TableColumn<Products, String> productsColumnP;
    @FXML private TableColumn<Products, Integer> codeColumnP;
    @FXML private TextField filterTextField;
    @FXML private Label orderNumber;
    @FXML private TableView<PurchaseOrder> ordersTableView;
    @FXML private TableColumn<PurchaseOrder, Integer> codeColumn;
    @FXML private TableColumn<PurchaseOrder, String> nameColumn;
    @FXML private TableColumn<PurchaseOrder, Integer> quantityColumn;
    private int productCode;
    private String productName;
    private String provider;
    private ObservableList<PurchaseOrder> purchaseOrders = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeOfProductCB.setItems(typeOfProducts);
        providersCB.setItems(providerName());
        orderNumber.setText(orderNumber());
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        ordersTableView.setItems(purchaseOrders);
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public PurchaseOrder purchaseOrder;

     public void fillProducts(ActionEvent actionEvent) {
        ObservableList<Products> productsList = FXCollections.observableArrayList(productsNames(typeOfProductCB.getValue().toString()));

        codeColumnP.setCellValueFactory(new PropertyValueFactory<>("code"));
        productsColumnP.setCellValueFactory(new PropertyValueFactory<>("name"));

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

    @FXML
    public void clickItem(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount()==2){

            productCode = productsTableView.getSelectionModel().getSelectedItem().getCode();
            productName = productsTableView.getSelectionModel().getSelectedItem().getName();
            provider = providersCB.getValue().toString();
            purchaseOrder = new PurchaseOrder(Integer.parseInt(orderNumber()), productCode, productName, "proveedor1" );

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("Ui/quantityWindow.fxml"));
            Parent view = loader.load();

            Scene scene = new Scene(view);

            QuantityController  controller = loader.getController();
            controller.initData(purchaseOrder, purchaseOrders);

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setScene(scene);
            window.showAndWait();
        }
    }



    public void clearTable(ActionEvent actionEvent) {
        ordersTableView.getSelectionModel().clearSelection();
        ordersTableView.getItems().clear();
    }
}
