package controllers;

import DB.RecordsDAO;
import Model.InventoryPolicies;
import Model.Records;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class InventoryPoliciesController implements RecordsDAO {

    @FXML
    private TableView<InventoryPolicies> policiesTableView;
    @FXML
    private TableColumn<InventoryPolicies, String> productsColumn;
    @FXML
    private TableColumn<InventoryPolicies, String> classificationColumn;
    @FXML
    private TableColumn<InventoryPolicies, String> behaviorColumn;
    @FXML
    private TableColumn<InventoryPolicies, Double> demandColumn;
    @FXML
    private TableColumn<InventoryPolicies, Double> QColumn;
    @FXML
    private TableColumn<InventoryPolicies, Double> RColumn;
    @FXML
    private TableColumn<InventoryPolicies, Double> costColumn;
    @FXML
    private RadioButton medicineRB;
    @FXML
    private RadioButton medicalDeviceRB;
    @FXML
    private RadioButton insumRB;

    public void generatePolicies(){
        ObservableList<Records> recordsData = FXCollections.observableArrayList();
        String typeOfProduct = "";

        if (medicineRB.isSelected()) {
            typeOfProduct = "medicamento";
            recordsData = FXCollections.observableArrayList(getRowOutComes("medicamento"));
        }
        if (medicalDeviceRB.isSelected()) {
            typeOfProduct = "dispositivo médico";
            recordsData = FXCollections.observableArrayList(getRowOutComes("dispositivo médico"));
        }
        if (insumRB.isSelected()) {
            typeOfProduct = "insumo";
            recordsData = FXCollections.observableArrayList(getRowOutComes("insumo"));
        }

        InventoryPolicies inventoryPolicies = new InventoryPolicies();
        ObservableList<InventoryPolicies> policies = inventoryPolicies.generatePolicies(recordsData, typeOfProduct);

        productsColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        classificationColumn.setCellValueFactory(new PropertyValueFactory<>("classification"));
        behaviorColumn.setCellValueFactory(new PropertyValueFactory<>("behaviorString"));
        demandColumn.setCellValueFactory(new PropertyValueFactory<>("averageDemand"));
        QColumn.setCellValueFactory(new PropertyValueFactory<>("quantityToOrder"));
        RColumn.setCellValueFactory(new PropertyValueFactory<>("whenToOrder"));
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        policiesTableView.setItems(policies);
    }
}
