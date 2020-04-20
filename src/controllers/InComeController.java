package controllers;

import DB.*;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class InComeController implements Initializable, InComesDAO, PurchaseOrderDAO, InventoryDAO, ProductsDAO, ProvidersDAO {

    @FXML
    private TableView<PurchaseOrder> purchaseOrdersTableView;
    @FXML
    private TableView<PurchaseOrder> verificationTableView;
    @FXML
    private TableColumn<PurchaseOrder, Integer> orderNumberColumn;
    @FXML
    private TableColumn<PurchaseOrder, String> providerColumn;

    @FXML
    private TableColumn<PurchaseOrder, String> productColumn;
    @FXML
    private TableColumn<PurchaseOrder, Integer> quantityColumn;
    @FXML
    private TableColumn<PurchaseOrder, CheckBox> verificationColumn;
    @FXML
    private TableColumn<PurchaseOrder, Double> priceColumn;
    @FXML
    private TableColumn<PurchaseOrder, Date> expirationDateColumn;
    @FXML
    private TextField filterTextField;

    private PurchaseOrder purchaseOrderCorrected;
    private int indexToFix;
    private Double unitPrice;
    private Date expirationDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<PurchaseOrder> observableList = FXCollections.observableArrayList(getPurchaseOrderPending());

        //take a order number from the observable list
        for (int i = 0; i < observableList.size(); i++) {
            int orderNumber = observableList.get(i).getOrderNumber();

            //take the order number and compares it with the rest of the observable list
            //when find another order with the same order number removes it
            for (int j = 0; j < observableList.size(); j++) {
                if (observableList.get(j).getOrderNumber() == orderNumber) {
                    observableList.remove(j);
                }
            }
        }

        //display the order's number
        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        providerColumn.setCellValueFactory(new PropertyValueFactory<>("providerName"));
        purchaseOrdersTableView.setItems(observableList);

        FilteredList<PurchaseOrder> filteredData = new FilteredList<>(observableList, p -> true);
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(PurchaseOrder -> {
                // If filter text is empty, display all products
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare product's name with the filter
                String lowerCaseFilter = newValue.toLowerCase();

                if (PurchaseOrder.getProviderName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
    }

    /**
     * get the selected purchase order's number and open the complete purchase order and display it in the verification
     * table
     * */
    public void OpenPurchaseOrder() {
        verificationTableView.getItems().clear();
        int orderNumberSelected = purchaseOrdersTableView.getSelectionModel().getSelectedItem().getOrderNumber();

        //get all the purchase order that was selected
        ObservableList<PurchaseOrder> observableList = FXCollections.observableArrayList(getPurchaseOrderByNumber(orderNumberSelected));

        for (int i = 0; i < observableList.size(); i++) {
            CheckBox ch = new CheckBox();
            //set all checkbox selected for default
            ch.fire();

            //add the listener event and the behavior for the uncheck
            int finalI = i;
            ch.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    try {
                        openCorrectionWindow(finalI);
                        //purchaseOrdersTableView.edit(finalI, quantityColumn);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            observableList.get(i).setPerfectIncome(ch);
        }

        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        verificationColumn.setCellValueFactory(new PropertyValueFactory<>("perfectIncome"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("inComePrice"));
        expirationDateColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        verificationTableView.setItems(observableList);
    }

    /**
     * tales all the data from the verification table and call the methods updateInventory and setInComeRecord and close
     * the window
     * */
    public void generateInCome() {
        ObservableList<PurchaseOrder> purchaseOrdersList = verificationTableView.getItems();
        ObservableList<Inventory> inventoryUpdateList = FXCollections.observableArrayList();
        ObservableList<Records> inComeRecords = FXCollections.observableArrayList();

        Date date = new Date();
        Double totalPrice = 0.0;

        for (PurchaseOrder item:purchaseOrdersList) {
            if (item.getInComePrice()!=null||item.getExpirationDate()!=null) {
                totalPrice = item.getInComePrice() * item.getQuantity();
                Inventory inventory = new Inventory(getProductById(item.getProductCode()),
                        item.getQuantity(), item.getInComePrice(), item.getExpirationDate());
                inventoryUpdateList.add(inventory);

                Records record = new Records(date, item.getQuantity(), getProductById(item.getProductCode()),
                        item.getProviderCode(), Users.getCurrentUser().getId(),
                        item.getInComePrice(), totalPrice);
                inComeRecords.add(record);
            } else {
                Alerts.notSelectionAlert("Debe ingresar tantoe el precio como la fecha para cada producto!");
                return;
            }
        }

        setInComeRecords(inComeRecords);
        updateInventory(inventoryUpdateList);
        updatePurchaseOrderState(purchaseOrdersList.get(0).getOrderNumber());
        Stage stage = (Stage) verificationTableView.getScene().getWindow();
        stage.close();
    }

    /**
     *Once a correction have been made on a purchase order, this method update the verification table with the new
     * information
     * */
    private void updateVerificationTable(){
        verificationTableView.getItems().set(indexToFix, purchaseOrderCorrected);
        verificationTableView.refresh();
    }

    /**
     * If a item from the purchase order is received in a wrong way, opens a correction window to change the product or the
     * quantity received
     * */
    private void openCorrectionWindow(int index) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("Ui/InComeCorrection.fxml"));
        Parent view = loader.load();

        Scene scene = new Scene(view);

        InComeCorrectionController controller = loader.getController();

        //set the init data
        controller.initData(verificationTableView.getItems().get(index));

        Stage window = new Stage();
        window.getIcons().add(new Image("images/application_icon.png"));
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.showAndWait();

        purchaseOrderCorrected = controller.getPurchaseOrderCorrected();
        updateVerificationTable();
        indexToFix = index;
    }

    /**
     *Call the price window when the user double click on a purchase order
     * */
    @FXML
    public void clickItem(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        if(mouseEvent.getClickCount()==2) {
            //create an instance of the quantityController
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("Ui/PriceAndDateWindow.fxml"));
            Parent view = loader.load();

            Scene scene = new Scene(view);

            PriceAndDateController controller = loader.getController();

            Stage window = new Stage();
            window.getIcons().add(new Image("images/application_icon.png"));
            window.initModality(Modality.APPLICATION_MODAL);
            window.setResizable(false);
            window.setScene(scene);
            window.showAndWait();

            unitPrice = controller.getUnitPrice();
            expirationDate = controller.getExpirationDate();
            addUnitPriceAndDate();
        }
    }

    /**
     * takes the unit price value and add it to the purchase order
     * */
    private void addUnitPriceAndDate() {
        verificationTableView.getSelectionModel().getSelectedItem().setInComePrice(unitPrice);
        verificationTableView.getSelectionModel().getSelectedItem().setExpirationDate(expirationDate);
        verificationTableView.refresh();
    }
}
