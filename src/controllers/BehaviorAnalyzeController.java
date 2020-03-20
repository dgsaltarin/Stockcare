package controllers;

import DB.ProductsDAO;
import DB.RecordsDAO;
import Model.BehaviorAnalyze;
import Model.Products;
import Model.Records;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class BehaviorAnalyzeController extends Operations implements Initializable, ProductsDAO, RecordsDAO {

    @FXML private TableView<Products> productsTableView;
    @FXML private TableColumn<Products, String> productsTableColumn;
    @FXML private Button generateAnalyzeButton;

    @FXML private ScatterChart<String, Number> behaviorAnalyzeChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeOfProductCB.setItems(typeOfProducts);

    }

    public void fillTable(javafx.event.ActionEvent actionEvent) {
        ObservableList<Products> productsList = FXCollections.observableArrayList(productsNames(typeOfProductCB.getValue().toString()));

        productsTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        productsTableView.setItems(productsList);

        FilteredList<Products> filteredData = new FilteredList<>(productsList, p -> true);
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Products -> {
                // If filter text is empty, display all products
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare product's name with the filter
                String lowerCaseFilter = newValue.toLowerCase();

                if (Products.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        SortedList<Products> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productsTableView.comparatorProperty());

        productsTableView.setItems(sortedData);
    }

    public void generateAnalyze(ActionEvent actionEvent) {
        BehaviorAnalyze behaviorAnalyze = new BehaviorAnalyze();

        Scene scene = generateAnalyzeButton.getScene();
        scene.getStylesheets().add("Resources/scatterChart.css");

        ObservableList<Records> observableList = FXCollections.observableArrayList(outComesList(typeOfProductCB.getValue().toString()));
        String productToAnalyze = productsTableView.getSelectionModel().getSelectedItem().getName();

        behaviorAnalyzeChart.setAnimated(false);
        behaviorAnalyzeChart.getData().add(behaviorAnalyze.generateScatterChart(observableList, productToAnalyze));
        behaviorAnalyzeChart.getXAxis().setLabel("Tiempo");
        behaviorAnalyzeChart.getYAxis().setLabel("Cantidad");
    }
}
