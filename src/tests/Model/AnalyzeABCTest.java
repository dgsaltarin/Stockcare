package Model;

import DB.RecordsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;

public class AnalyzeABCTest implements RecordsDAO {

    @Test
    public void ABCAnalyze() {
        ObservableList<Records> observableList = FXCollections.observableArrayList(getRowOutComes("medicamento"));
        String typeOfProduct = "medicamento";
        AnalyzeABC analyzeABC = new AnalyzeABC();
        ObservableList<AnalyzeABC> result = analyzeABC.ABCAnalyze(observableList, typeOfProduct);

        for (int i=0; i<result.size();i++){
            System.out.println(result.get(i).getProductName());
            System.out.println(result.get(i).getAverageDemand());
            System.out.println(result.get(i).getParticipationPercentage());
            System.out.println(result.get(i).getAccumulatedPercentage());
            System.out.println(result.get(i).getClassification());
        }
    }
}