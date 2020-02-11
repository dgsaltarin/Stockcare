package controllers;

import DB.RecordsDAO;
import Model.Records;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.ActionEvent;
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
    @FXML private TextField filterTextField;
    private ObservableList<String> typeOfProducts = FXCollections.observableArrayList("Salidas", "Entradas");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.setItems(typeOfProducts);
    }

    public void fillTable(ActionEvent actionEvent){
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
        else {
            System.out.println("debe seleccionar alguna de las categorias de products");
        }

        ObservableList<Records> recordsList = FXCollections.observableArrayList(outComesList(typeofProduct));

        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("areaName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

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
        else {
            System.out.println("debe seleccionar alguna de las categorias de products");
        }

        ObservableList<Records> recordsList = FXCollections.observableArrayList(outComesList(typeofProduct));

        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
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
}
