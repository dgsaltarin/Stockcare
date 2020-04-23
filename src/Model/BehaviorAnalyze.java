package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class BehaviorAnalyze extends Analyze {

    /**
     * given the list of records and the name of a product, return all the product's records
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
}
