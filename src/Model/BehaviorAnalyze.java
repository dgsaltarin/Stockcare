package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class BehaviorAnalyze extends Analyze {

    /**
     * given the list of records and the name of a product, return all the product's records for the last year
     * @param observableList observable list of records corresponding to a type of product
     * @param productToAnalyze name of the product to analyze
     * */
    public ObservableList<Records> productRecordsData(ObservableList<Records> observableList, String productToAnalyze){
        ObservableList<Records> productData = FXCollections.observableArrayList();

        for (Records record:observableList){
            if (record.getProductName().equals(productToAnalyze)){
                productData.add(record);
            }
        }

        productData = FXCollections.observableArrayList(lastYearOfData(productData));
        return productData;
    }

    public XYChart.Series<String, Number> generateScatterChart(ObservableList<Records> observableList, String productToAnalyze){
        XYChart.Series<String, Number> values = new XYChart.Series<>();

        for (int i=observableList.size()-1;i>0;i--){
            if(observableList.get(i).getProductName().equals(productToAnalyze)) {
                String date = observableList.get(i).getDateOfRecord().toString();
                Number quantity = observableList.get(i).getQuantity();
                values.getData().add(new XYChart.Data<>(date, quantity));
            }
        }

        values.setName(productToAnalyze);
        return values;
    }

    public String chooseBehavior(ObservableList<Records> productDemand){
        String behaviorString = "";
        ArrayList<Records> lastYearProductDemand = lastYearOfData(productDemand);
        int[][] separatedDemandByMonth = separateDataByMonth(lastYearProductDemand);
        int[][] totalMonthlyDemand = totalMonthlyDemand(separatedDemandByMonth);

        int behavior = chooseBehavior(totalMonthlyDemand);

        switch (behavior){
            case 0:
                behaviorString = "constante";
                break;
            case 1:
                behaviorString = "tendencia";
                break;
            case 2:
                behaviorString = "estacional";
                break;
        }

        return behaviorString;
    }
}
