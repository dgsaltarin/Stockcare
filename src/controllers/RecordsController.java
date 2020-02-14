package controllers;

import DB.RecordsDAO;
import Model.Alerts;
import Model.Records;
import Model.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecordsController implements Initializable, RecordsDAO {

    @FXML private ComboBox comboBox;
    @FXML private RadioButton medicamentos;
    @FXML private RadioButton dispositivosM;
    @FXML private RadioButton insumos;
    @FXML private TableView<Records> recordsTableView;
    @FXML private TableColumn<Records, String> productColumn;
    @FXML private TableColumn<Records, Integer> quantityColumn;
    @FXML private TableColumn<Records, String> areaColumn;
    @FXML private TableColumn<Records, String> dateColumn;
    @FXML private TableColumn<Records, String> userColumn;
    @FXML private TableColumn<Records, String> unitPriceColumn;
    @FXML private TableColumn<Records, String> totalPriceColumn;
    @FXML private TextField filterTextField;
    @FXML private DatePicker initDate;
    @FXML private DatePicker finalDate;

    private ObservableList<String> typeOfProducts = FXCollections.observableArrayList("Salidas", "Entradas");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.setItems(typeOfProducts);
    }

    public void fillTable(javafx.event.ActionEvent actionEvent) {
        String typeofProduct = "";
        if (medicamentos.isSelected()){
            typeofProduct = "medicamento";
        }
        if (dispositivosM.isSelected()){
            typeofProduct = "dispositivo medico";
        }
        if (insumos.isSelected()){
            typeofProduct = "insumo";
        }

        ObservableList<Records> recordsList = null;

        if(comboBox.getSelectionModel().isEmpty()){
            Alerts.notSelectionAlert("Escoja el tipo de registro!");
            return;
        }
        else {
            if(comboBox.getValue().toString().equals("Salidas")){
                recordsList = FXCollections.observableArrayList(outComesList(typeofProduct));
            }
            if(comboBox.getValue().toString().equals("Entradas")){
                recordsList = FXCollections.observableArrayList(inComesList(typeofProduct));
            }
        }

        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("areaName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfRecord"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        recordsTableView.setItems(recordsList);

        FilteredList<Records> filteredData = new FilteredList<>(recordsList, p -> true);
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Records -> {
                // If filter text is empty, display all products
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare product's name with the filter
                String lowerCaseFilter = newValue.toLowerCase();

                if (Records.getProductName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        SortedList<Records> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(recordsTableView.comparatorProperty());

        recordsTableView.setItems(sortedData);
    }

    public void generateReport(javafx.event.ActionEvent actionEvent) throws IOException {
        ObservableList<Records> observableList = recordsTableView.getItems();
        Report.callReportWindow("records", observableList);
    }

    public void filtrateByDate(javafx.event.ActionEvent actionEvent){
        fillTable(actionEvent);
    }
}
