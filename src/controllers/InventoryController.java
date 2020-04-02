package controllers;

import DB.InventoryDAO;
import Model.Alerts;
import Model.Inventory;
import Model.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Date;

public class InventoryController implements InventoryDAO {

    @FXML RadioButton medicamento;
    @FXML RadioButton dispositivoM;
    @FXML RadioButton insumos;
    @FXML TableView inventoryTable;
    @FXML TableColumn<Inventory, String> nameColumn;
    @FXML TableColumn<Inventory, Integer> quantityColumn;
    @FXML TableColumn<Inventory, Double> unitPriceColumn;
    @FXML TableColumn<Inventory, Date> expirationColumn;
    @FXML TextField filterField;

    public void fillTable(ActionEvent actionEvent) {

        String typeofProduct = "";
        if (medicamento.isSelected()){
            typeofProduct = "medicamento";
        }
        else if (dispositivoM.isSelected()){
            typeofProduct = "dispositivo medico";
        }
        else if (insumos.isSelected()){
            typeofProduct = "insumo";
        } else
            Alerts.notSelectionAlert("Seleccione algúnn tipo de producto!");

        ObservableList<Inventory> inventoryList = FXCollections.observableArrayList(getInventory(typeofProduct));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        expirationColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));

        inventoryTable.setItems(inventoryList);

        FilteredList<Inventory> filteredData = new FilteredList<>(inventoryList, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
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

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(inventoryTable.comparatorProperty());

        inventoryTable.setItems(sortedData);
    }

    public void generateReport(ActionEvent actionEvent) throws IOException {
        ObservableList<Inventory> observableList = inventoryTable.getItems();

        if (observableList.isEmpty()){
            Alerts.notSelectionAlert("La tabla se encuentra vacia!");
            return;
        }

        Report.callReportWindow("inventory", observableList);
    }
}
