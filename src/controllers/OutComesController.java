package controllers;

import DB.AreasDAO;
import DB.InventoryDAO;
import DB.OutComesDAO;
import DB.ProductsDAO;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class OutComesController extends Operations implements Initializable, AreasDAO, InventoryDAO, ProductsDAO, OutComesDAO {

    @FXML private TableView<Inventory> inventoryTableView;
    @FXML private TableColumn<Inventory, Integer> quantityColumnI;
    @FXML private TableColumn<Inventory, String> productsColumnI;
    @FXML private TableColumn<Inventory, Double> unitPriceColumnI;

    @FXML private TableView<Records> outComeTableView;
    @FXML private TableColumn<Records, Integer> codeColumn;
    @FXML private TableColumn<Records, String> nameColumn;
    @FXML private TableColumn<Records, Integer> quantityColumn;
    @FXML private TableColumn<Records, Double> unitPriceColumn;
    @FXML private TableColumn<Records, Double> totalPriceColumn;
    @FXML private ComboBox areasCB;

    private Integer productId;
    private Integer userId;
    private Integer areaId;
    private Double unitPrice;
    private Double totalPrice;
    public int remainingProduct;

    private Records records;

    private ObservableList<Records> OutcomesList = FXCollections.observableArrayList();
    private ObservableList<String> areasNames = FXCollections.observableArrayList(getAreasName());
    private ObservableList<Inventory> inventoryUpdate = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeOfProductCB.setItems(typeOfProducts);
        areasCB.setItems(areasNames);
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        outComeTableView.setItems(OutcomesList);
    }

    public void updateInventoryTable() {
            inventoryTableView.getSelectionModel().getSelectedItem().setQuantity(remainingProduct);
            inventoryTableView.refresh();
    }

    public void fillProducts(ActionEvent actionEvent) {
        ObservableList<Inventory> inventoryList = FXCollections.observableArrayList(getInventory(typeOfProductCB.getValue().toString()));

        quantityColumnI.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productsColumnI.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        unitPriceColumnI.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        inventoryTableView.setItems(inventoryList);

        FilteredList<Inventory> filteredData = new FilteredList<>(inventoryList, p -> true);
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Inventory -> {
                // If filter text is empty, display all products
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare product's name with the filter
                String lowerCaseFilter = newValue.toLowerCase();

                if (Inventory.getProductName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        SortedList<Inventory> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(inventoryTableView.comparatorProperty());

        inventoryTableView.setItems(sortedData);
    }

    /**
     * @description call the quantity window when the user doble clic on a product
     * */
    @FXML
    public void clickItem(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount()==2){
            if(!areasCB.getSelectionModel().isEmpty()&&!typeOfProductCB.getSelectionModel().isEmpty()) {
                //set the initial information for the outcome record
                Date date = new Date();
                productId = inventoryTableView.getSelectionModel().getSelectedItem().getProproductId();
                unitPrice = inventoryTableView.getSelectionModel().getSelectedItem().getUnitPrice();
                areaId = getAreaIdByName(areasCB.getValue().toString());
                remainingProduct = inventoryTableView.getSelectionModel().getSelectedItem().getQuantity();
                userId = 1143155845;

                records = new Records(date, 0, getProductById(productId), areaId, userId, unitPrice ,0.0);

                //create an instance of the quantityController
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("Ui/quantityWindow.fxml"));
                Parent view = loader.load();

                Scene scene = new Scene(view);

                QuantityController controller = loader.getController();

                //set the init data
                controller.initData(records, OutcomesList, remainingProduct);

                Stage window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.setScene(scene);

                window.showAndWait();
                remainingProduct = remainingProduct - OutcomesList.get(OutcomesList.size() - 1).getQuantity();

            }else if(areasCB.getSelectionModel().isEmpty()){
                Alerts.notSelectionAlert("Seleccione un area!");
            }
            else if(typeOfProductCB.getSelectionModel().isEmpty()){
                Alerts.notSelectionAlert("Seleccione el tipo de producto!");
            }
        }
    }

    public void generateOutCome(ActionEvent actionEvent) {
        ObservableList<Records> outComeRecords = outComeTableView.getItems();
        for(int i=0; i< outComeRecords.size();i++){
            inventoryUpdate.add(getInventoryItem(outComeRecords.get(i).getProductId(), outComeRecords.get(i).getUnitPrice(), outComeRecords.get(i).getQuantity()));
        }
        setOutComeRecords(outComeRecords);
        updateInventory(inventoryUpdate);
        fillProducts(actionEvent);
        clearTable(actionEvent);
    }

    public void clearTable(ActionEvent actionEvent) {
        outComeTableView.getItems().clear();
    }
}
