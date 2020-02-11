package controllers;

import Model.PurchaseOrder;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class QuantityController implements Initializable {

    @FXML private TextField quantityTextField;
    private PurchaseOrder purchaseOrder;
    private ObservableList<PurchaseOrder> purchaseOrdersList;

    //take the recibed purchase order and with the quantity create a full purchase order
    public void recibeQuantity(ActionEvent actionEvent) throws IOException {
        //complete the purchase order
        PurchaseOrder purchaseOrderC = new PurchaseOrder(purchaseOrder.getOrderNumber(),getQuantityNumber(),purchaseOrder.getProductCode(),purchaseOrder.getProductName(),purchaseOrder.getProviderName());
        purchaseOrdersList.add(purchaseOrderC);

        //close the stage
        Stage stage1 = (Stage) quantityTextField.getScene().getWindow();
        stage1.close();
    }

    private int getQuantityNumber(){
        String quantityString = quantityTextField.getText();
        int quantity = Integer.parseInt(quantityString);
        return quantity;
    }

    /**
     * @param purchaseOrder recibe the data of the purchase order were is missing the quantity
     * @param purchaseOrdersList recibe an observableList were the purchase orders are stored
     * */
    public void initData(PurchaseOrder purchaseOrder, ObservableList<PurchaseOrder> purchaseOrdersList){
        this.purchaseOrder = purchaseOrder;
        this.purchaseOrdersList = purchaseOrdersList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
