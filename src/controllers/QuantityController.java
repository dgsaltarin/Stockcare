package controllers;

import DB.ProductsDAO;
import DB.ProvidersDAO;
import Model.Alerts;
import Model.PurchaseOrder;
import Model.Records;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class QuantityController implements Initializable, ProductsDAO, ProvidersDAO {

    @FXML
    private TextField quantityTextField;

    private PurchaseOrder purchaseOrder;
    private Records record;
    private ObservableList observableList;
    private int productRemaining;

    //take the received purchase order and with the quantity create a full purchase order
    public void receiveQuantity() {
        //complete the purchase order
        if (purchaseOrder != null) {
            PurchaseOrder purchaseOrderC = new PurchaseOrder(purchaseOrder.getOrderNumber(), getQuantityNumber(),
                    getProductById(purchaseOrder.getProductCode()),
                    getProviderById(purchaseOrder.getProviderCode()), false);
            observableList.add(purchaseOrderC);
        }
        if (record != null) {
            if (getQuantityNumber() <= productRemaining) {
                Double totalPrice = record.getUnitPrice() * getQuantityNumber();
                Records recordsC = new Records(record.getDateOfRecord(), getQuantityNumber(),
                        record.getProduct(), record.getAreaId(),
                        record.getUserId(),
                        record.getUnitPrice(),
                        totalPrice);
                observableList.add(recordsC);
                productRemaining = calculateRemainingProduct(getQuantityNumber(), productRemaining);
            } else {
                Alerts.notSelectionAlert("La cantidad excede la cantidad en inventario!");
                return;
            }
        }

        //close the stage
        Stage stage1 = (Stage) quantityTextField.getScene().getWindow();
        stage1.close();
    }

    private int getQuantityNumber() {
        String quantityString = quantityTextField.getText();
        int quantity = Integer.parseInt(quantityString);
        return quantity;
    }

    /**
     * @param purchaseOrder      recibe the data of the purchase order were is missing the quantity
     * @param purchaseOrdersList recibe an observableList were the purchase orders are stored
     */
    public void initData(PurchaseOrder purchaseOrder, ObservableList<PurchaseOrder> purchaseOrdersList) {
        this.purchaseOrder = purchaseOrder;
        this.observableList = purchaseOrdersList;
    }

    public void initData(Records record, ObservableList<Records> observableList, int productRemaining) {
        this.productRemaining = productRemaining;
        this.record = record;
        this.observableList = observableList;
    }

    public int calculateRemainingProduct(int quantity, int productRemaining) {
        int remaining = productRemaining - quantity;
        return remaining;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    public int getProductRemaining() {
        return productRemaining;
    }
}
