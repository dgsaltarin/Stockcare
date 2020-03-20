package controllers;

import Model.AbcVenAnalyze;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AbcVenAnalyzeController implements Initializable {

    @FXML private TableView<AbcVenAnalyze> abcVenTableView;
    @FXML private TableColumn<AbcVenAnalyze, String> productColumn;
    @FXML private TableColumn<AbcVenAnalyze, String> abcColumn;
    @FXML private TableColumn<AbcVenAnalyze, String> venColumn;
    @FXML private PieChart abcVenPieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AbcVenAnalyze analyze = new AbcVenAnalyze();

        ObservableList<AbcVenAnalyze> abcVenAnalyzes = analyze.generateAnalyze();

        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        abcColumn.setCellValueFactory(new PropertyValueFactory<>("classification"));
        venColumn.setCellValueFactory(new PropertyValueFactory<>("abcVenClassification"));

        abcVenTableView.setItems(abcVenAnalyzes);

        abcVenPieChart.setData(analyze.createPieChart(abcVenAnalyzes));
        abcVenPieChart.setTitle("Clasificación ABC-VEN");
        abcVenPieChart.setAnimated(false);
        abcVenPieChart.setClockwise(true);
    }
}
