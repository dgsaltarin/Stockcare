package controllers;

import DB.RecordsDAO;
import Model.AnalyzeABC;
import Model.Records;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AnalyzeABCController extends Operations implements Initializable, RecordsDAO {

    @FXML private TableView<AnalyzeABC> abcTableView;
    @FXML private TableColumn<AnalyzeABC, String> productColumn;
    @FXML private TableColumn<AnalyzeABC, Double> averageDemandColumn;
    @FXML private TableColumn<AnalyzeABC, Double> percentageColumn;
    @FXML private TableColumn<AnalyzeABC, Double> acumPercentageColumn;
    @FXML private TableColumn<AnalyzeABC, Double> classificationColumn;

    @FXML private PieChart abcPieChart;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeOfProductCB.setItems(typeOfProducts);
    }

    /**
     * generate ABC analyze
     * */
    public void generateAnalyze() {
        String typeOfProduct = typeOfProductCB.getSelectionModel().getSelectedItem().toString();
        AnalyzeABC analyzeABC = new AnalyzeABC();

        ObservableList<Records> observableList = FXCollections.observableArrayList(this.getRowOutComes(typeOfProduct));
        ObservableList<AnalyzeABC> result = analyzeABC.ABCAnalyze(observableList, typeOfProduct);

        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        averageDemandColumn.setCellValueFactory(new PropertyValueFactory<>("averageDemand"));
        percentageColumn.setCellValueFactory(new PropertyValueFactory<>("participationPercentage"));
        acumPercentageColumn.setCellValueFactory(new PropertyValueFactory<>("accumulatedPercentage"));
        classificationColumn.setCellValueFactory(new PropertyValueFactory<>("classification"));

        abcPieChart.setData(analyzeABC.generatePieChart(result));
        abcTableView.setItems(result);

        abcPieChart.setTitle("Clasificación ABC");

        abcPieChart.setAnimated(false);

        abcPieChart.setClockwise(true);
    }
}
