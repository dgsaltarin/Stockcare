package Model;

import javafx.collections.ObservableList;
import static Model.Analyze.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AnalyzeABC extends Analyze {

    private String productName;
    private int averageDemand;
    private Double participationPercentage;
    private Double acumulatedPercetage;
    private String classification;

    public ObservableList<AnalyzeABC> ABCAnalyze(ObservableList<Records> observableList, String typeOfProduct){
        ObservableList<AnalyzeABC> AbcAnalyze = null;
        //preparing data
        Double acumulatedParticipat = 0.0;
        ArrayList<Records> lastYearData = lastYearOfData(observableList);
        int[][] monthlyDemand = separateDataByMonth(lastYearData);
        int[][] averageDemand = averageMonthlyDemand(monthlyDemand, typeOfProduct);
        sortMatrix(averageDemand,1);

        HashMap<Integer, String> productList = createHashMapProductList(typeOfProduct);

        //calculating the total demand for all product
        int totalDemand = 0;
        for (int i = 0; i<averageDemand.length;i++){
            totalDemand += averageDemand[i][1];
        }

        for (int i=0; i<=averageDemand.length;i++){
            if (averageDemand[i][1]>0){
                String productName = productList.get(averageDemand[i][0]);
                Double participation = (double) (averageDemand[i][1]/totalDemand);
                acumulatedParticipat += acumulatedParticipat + participation;
                AnalyzeABC analyze = new AnalyzeABC(productName, averageDemand[i][1], participation, acumulatedParticipat, "NA");
                AbcAnalyze.add(analyze);
            }
        }

        return AbcAnalyze;
    }

    public AnalyzeABC(String productName, int averageDemand, Double participationPercentage, Double acumulatedPercetage, String classification) {
        this.productName = productName;
        this.averageDemand = averageDemand;
        this.participationPercentage = participationPercentage;
        this.acumulatedPercetage = acumulatedPercetage;
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

    public Double getAcumulatedPercetage() {
        return acumulatedPercetage;
    }

    public String getClassification() {
        return classification;
    }
}
