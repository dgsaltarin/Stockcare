package controllers;

import DB.ProductsDAO;
import Model.Products;
import Model.Report;
import Model.ReportPdf;
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

public class ProductsController implements ProductsDAO, ReportPdf {

    @FXML private RadioButton medicamentos;
    @FXML private RadioButton dispositivo_medico;
    @FXML private RadioButton insumos;
    @FXML private TableView<Products> tableProducts;
    @FXML private TableColumn<Products, Integer> codeColumn;
    @FXML private TableColumn<Products, String> nameColumn;
    @FXML private TableColumn<Products, Double> priceColumn;
    @FXML private TableColumn<Products, String> clasificationColumn;
    @FXML private TextField filterField;

    /**
     * @description get the list of products and put them on a table 
     * */
    public void fillTable(ActionEvent actionEvent) {
        String typeofProduct = "";
        if (medicamentos.isSelected()){
            typeofProduct = "medicamento";
        }
        if (dispositivo_medico.isSelected()){
            typeofProduct = "dispositivo medico";
        }
        if (insumos.isSelected()){
            typeofProduct = "insumo";
        }

        ObservableList<Products> productsList = FXCollections.observableArrayList(productsList(typeofProduct));

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        clasificationColumn.setCellValueFactory(new PropertyValueFactory<>("clasification"));

        tableProducts.setItems(productsList);

        FilteredList<Products> filteredData = new FilteredList<>(productsList, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
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

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableProducts.comparatorProperty());

        tableProducts.setItems(sortedData);
    }

    public void generateReport(ActionEvent actionEvent) throws IOException {
        ObservableList<Products> selectedProducts = tableProducts.getItems();
        Report.callReportWindow("products", selectedProducts);
        System.out.println(selectedProducts.size());
    }
}
