package Model;

import DB.RecordsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BehaviorAnalyzeTest implements RecordsDAO {

    @Test
    public void productRecordsdata() {

        BehaviorAnalyze behaviorAnalyze = new BehaviorAnalyze();
        ObservableList<Records> records = FXCollections.observableArrayList(getRowOutComes("medicamento"));
        String productToAnalyze = "Acetaminofén tabletas 500 mg caja x 100";

        ObservableList<Records> productData = behaviorAnalyze.productRecordsData(records, productToAnalyze);

        for (Records record:productData){
            System.out.println(record.getProductName());
            System.out.println(record.getQuantity());
            System.out.println(record.getDateOfRecord());
        }

        System.out.println("\n");
        ArrayList<Records> lastYearProductData = behaviorAnalyze.lastYearOfData(productData);

        for (Records record:lastYearProductData){
            System.out.println(record.getProductName());
            System.out.println(record.getQuantity());
            System.out.println(record.getDateOfRecord());
        }

        System.out.println("\n");
        int[][] monthltyProductDeman = behaviorAnalyze.separateDataByMonth(lastYearProductData);
        for (int i=0; i<monthltyProductDeman.length;i++){
            System.out.println(monthltyProductDeman[i][0] + ", " + monthltyProductDeman[i][1]+ ", " +monthltyProductDeman[i][2]);
        }

        System.out.println("\n");
        int[][] totalMontlyProductDeman = behaviorAnalyze.totalMonthlyDemand(monthltyProductDeman);
        for (int i=0;i<totalMontlyProductDeman.length;i++){
            System.out.println(totalMontlyProductDeman[i][0] + ", " + totalMontlyProductDeman[i][1]);
        }

        Double standardDeviation = behaviorAnalyze.standardDeviation(totalMontlyProductDeman);

        System.out.println(standardDeviation);
    }
}