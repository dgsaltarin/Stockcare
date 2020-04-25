package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.HashMap;

public class AnalyzeABC extends Analyze {

    protected String productName;
    protected int averageDemand;
    protected Double participationPercentage;
    protected Double accumulatedPercentage;
    protected String classification;

    public AnalyzeABC(){}

    /**
     * receive the data records and a type of products and do an ABC analyze
     * */
    public ObservableList<AnalyzeABC> ABCAnalyze(ObservableList<Records> observableList, String typeOfProduct){
        ObservableList<AnalyzeABC> AbcAnalyze = FXCollections.observableArrayList();
        //preparing data
        Double accumulatedParticipat = 0.0;
        ArrayList<Records> lastYearData = lastYearOfData(observableList);
        monthlyDemand = separateDataByMonth(lastYearData);
        averageDemands = averageMonthlyDemand(monthlyDemand, typeOfProduct);
        double[][] averageDemandByPrice = new double[averageDemands.length][2];
        sortMatrix(averageDemands,1);

        HashMap<Integer, String> productList = createHashMapProductList(typeOfProduct);
        HashMap<Integer, Double> productPriceList = createHashMapProductPrice(typeOfProduct);

        //calculate the average demand taking in count the product's price
        for (int i = 0; i<averageDemands.length;i++){
            double productUnitPrice = productPriceList.get(averageDemands[i][0]);
            averageDemandByPrice[i][0] = averageDemands[i][0];
            averageDemandByPrice[i][1] = averageDemands[i][1]*productUnitPrice;
        }

        //calculating the total demand for all product
        int totalDemand = 0;

        for (int i = 0; i<averageDemandByPrice.length;i++){
            totalDemand += averageDemandByPrice[i][1];
        }

        for (int i=0; i<averageDemands.length;i++){
            if (averageDemands[i][1]>0){
                String productName = productList.get(averageDemands[i][0]);
                Double participation = averageDemandByPrice[i][1]/totalDemand;
                accumulatedParticipat += participation;
                String classification = "";
                if(accumulatedParticipat<0.8){
                    classification ="A";
                }
                else if(0.8<=accumulatedParticipat&&accumulatedParticipat<0.95){
                    classification ="B";
                }
                else if(0.95<=accumulatedParticipat){
                    classification ="C";
                }
                AnalyzeABC analyze = new AnalyzeABC(productName, averageDemands[i][1], participation, accumulatedParticipat, classification);
                AbcAnalyze.add(analyze);
            }
        }

        return AbcAnalyze;
    }

    /**
     * receive the result from ABC analyze and create a pie chart to illustrate the data
     * */
    public ObservableList<PieChart.Data> generatePieChart(ObservableList<AnalyzeABC> observableList){
        int aClassification =0;
        int bClassification =0;
        int cClassification =0;
        //get the account of item on every classification
        for (int i=0;i<observableList.size();i++){
            if(observableList.get(i).getClassification().equals("A")){
                aClassification += 1;
            }
             else if(observableList.get(i).getClassification().equals("B")){
                bClassification += 1;
            }
             else if(observableList.get(i).getClassification().equals("C")){
                cClassification += 1;
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("A", aClassification),
                new PieChart.Data("B", bClassification),
                new PieChart.Data("C", cClassification));
       return pieChartData;
    }

    public AnalyzeABC(String productName, int averageDemand, Double participationPercentage, Double accumulatedPercentage, String classification) {
        this.productName = productName;
        this.averageDemand = averageDemand;
        this.participationPercentage = participationPercentage;
        this.accumulatedPercentage = accumulatedPercentage;
        this.classification = classification;
    }

    public String getProductName() {
        return productName;
    }

    public int getAverageDemand() {
        return averageDemand;
    }

    public Double getParticipationPercentage() {
        return participationPercentage;
    }

    public Double getAccumulatedPercentage() {
        return accumulatedPercentage;
    }

    public String getClassification() {
        return classification;
    }
}
