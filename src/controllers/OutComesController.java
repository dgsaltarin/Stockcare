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
    public int remainingProduct;

    private Records records;

    private ObservableList<Records> OutcomesList = FXCollections.observableArrayList();
    private ObservableList<String> areasNames = FXCollections.observableArrayList(getAreasName());

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

    private void updateInventoryTable() {
            inventoryTableView.getSelectionModel().getSelectedItem().setQuantity(remainingProduct);
            inventoryTableView.refresh();
    }

    /**
     * once a product's category have been choose, show all the available products on inventory for an outcome
     * */
    public void fillProducts() {
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
     *call the quantity window when the user double click on a product
     * */
    @FXML
    public void clickItem(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount()==2){
            if(!areasCB.getSelectionModel().isEmpty()&&!typeOfProductCB.getSelectionModel().isEmpty()) {
                //set the initial information for the outcome record
                Date date = new Date();
                productId = inventoryTableView.getSelectionModel().getSelectedItem().getProductId();
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
                window.setResizable(false);
                window.initModality(Modality.APPLICATION_MODAL);
                window.setScene(scene);

                window.showAndWait();
                remainingProduct = controller.getProductRemaining();
                updateInventoryTable();
            }else if(areasCB.getSelectionModel().isEmpty()){
                Alerts.notSelectionAlert("Seleccione un area!");
            }
            else if(typeOfProductCB.getSelectionModel().isEmpty()){
                Alerts.notSelectionAlert("Seleccione el tipo de producto!");
            }
        }
    }

    /**
     * takes all the selected products and call the methods for update the inventory and save the outcome record
     * */
    public void generateOutCome() {
        ObservableList<Records> outComeRecords = outComeTableView.getItems();
        ObservableList<Inventory> inventoryUpdate = inventoryTableView.getItems();
        setOutComeRecords(outComeRecords);
        updateInventory(inventoryUpdate);
        fillProducts();
        clearTable();
    }

    /**
     * clear the table
     * */
    public void clearTable() {
        outComeTableView.getItems().clear();
    }
}
