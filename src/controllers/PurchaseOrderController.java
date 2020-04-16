package controllers;

import DB.ProductsDAO;
import DB.ProvidersDAO;
import DB.PurchaseOrderDAO;
import Model.*;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PurchaseOrderController extends Operations implements Initializable, ProvidersDAO, ProductsDAO, PurchaseOrderDAO {

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

    public PurchaseOrder purchaseOrder;
    private int productCode;
    private String productName;
    private String provider;

    private ObservableList<PurchaseOrder> purchaseOrders = FXCollections.observableArrayList();
    ObservableList<String> providerNames = FXCollections.observableArrayList(providerName());


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeOfProductCB.setItems(typeOfProducts);
        providersCB.setItems(providerNames);
        orderNumber.setText(orderNumber());
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("productCode"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        ordersTableView.setItems(purchaseOrders);
    }

    /***
     * @description get the list of products once the user select a categrory
     */
     public void fillProducts() {
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


    /**
     * @description call the quantity window when the user doble clic on a product
     * */
    @FXML
    public void clickItem(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount()==2){
            if(!providersCB.getSelectionModel().isEmpty()&&!typeOfProductCB.getSelectionModel().isEmpty()) {
                //get the init data for the purchase order
                productCode = productsTableView.getSelectionModel().getSelectedItem().getCode();
                productName = productsTableView.getSelectionModel().getSelectedItem().getName();
                provider = providersCB.getValue().toString();

                purchaseOrder = new PurchaseOrder(Integer.parseInt(orderNumber()), productCode, productName, getProvidersCodeByName(provider));

                //create an instance of the quantityController
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("Ui/quantityWindow.fxml"));
                Parent view = loader.load();

                Scene scene = new Scene(view);

                QuantityController controller = loader.getController();

                //set the init data
                controller.initData(purchaseOrder, purchaseOrders);

                Stage window = new Stage();
                window.getIcons().add(new Image("images/application_icon.png"));
                window.initModality(Modality.APPLICATION_MODAL);
                window.setScene(scene);
                window.showAndWait();
            }else if(providersCB.getSelectionModel().isEmpty()){
                Alerts.notSelectionAlert("Seleccione un proveedor!");
            }
            else if(typeOfProductCB.getSelectionModel().isEmpty()){
                Alerts.notSelectionAlert("Seleccione el tipo de producto!");
            }
        }
    }


    /**
     * @description clear the table in case of a mistake
     * */
    public void clearTable() {
        ordersTableView.getItems().clear();
    }

    /**
     * @description generate the report order and set the data into the data base
     * */
    public void generateOrder(ActionEvent actionEvent) throws IOException {
        ObservableList<PurchaseOrder> observableList = ordersTableView.getItems();
        ReportWindowController reportWindowController = new ReportWindowController();
        reportWindowController.setData(observableList);
        reportWindowController.setTypeOfReport("purchaseOrder");
        reportWindowController.generatePDFReport(actionEvent);
        setPurchaseOrderInDB(observableList);
        Alerts.successfullAlert("Orden generada de manera Exitosa");
        clearTable();
    }
}
