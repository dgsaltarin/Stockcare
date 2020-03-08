package controllers;

import DB.InComesDAO;
import DB.PurchaseOrderDAO;
import Model.PurchaseOrder;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class InComeController implements Initializable, InComesDAO, PurchaseOrderDAO {

    @FXML private TableView<PurchaseOrder> purchaseOrdersTableView;
    @FXML private TableView<PurchaseOrder> verificationTableView;
    @FXML private TableColumn<PurchaseOrder, Integer> orderNumberColumn;
    @FXML private TableColumn<PurchaseOrder, String> providerColumn;

    @FXML private TableColumn<PurchaseOrder, String> productColumn;
    @FXML private TableColumn<PurchaseOrder, Integer> quantityColumn;
    @FXML private TableColumn<PurchaseOrder, Boolean> verificationColumn;
    @FXML private TextField filterTextField;

    private BooleanProperty perfectOrder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<PurchaseOrder> observableList = FXCollections.observableArrayList(getPurchaseOrderPending());

        //take a order number from the observable list
        for(int i=0; i<observableList.size();i++){
            int orderNumber = observableList.get(i).getOrderNumber();

            //take the order number and compares it with the rest of the observable list
            //when find another order with the same order number removes it
            for (int j=0; j<observableList.size();j++){
                if(observableList.get(j).getOrderNumber()== orderNumber){
                    observableList.remove(j);
                }
            }
        }

        //display the order numbers
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


    public void OpenPurchaseOrder(ActionEvent actionEvent) {
        int orderNumberSelected = purchaseOrdersTableView.getSelectionModel().getSelectedItem().getOrderNumber();

        //get all the purchase order that was selected
        ObservableList<PurchaseOrder> observableList = FXCollections.observableArrayList(getPurchaseOrderByNumber(orderNumberSelected));

        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        verificationColumn.setCellValueFactory(
                param -> param.getValue().perfectIncome()
        );

        verificationColumn.setCellFactory(CheckBoxTableCell.forTableColumn(verificationColumn));

        verificationTableView.setItems(observableList);
    }


    public void generateInCome(ActionEvent actionEvent) {
    }
}
