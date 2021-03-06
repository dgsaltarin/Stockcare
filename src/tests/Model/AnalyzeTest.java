package Model;

import DB.RecordsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static Model.Analyze.*;


public class AnalyzeTest implements RecordsDAO {

    @Test
    public void lastSixMonths() throws ParseException {
        Analyze analyze = new Analyze();
        System.out.println(analyze.getDateTimeAgo(13));
        Date date = new Date();
        Boolean bool = analyze.getDateTimeAgo(13).before(date);
        System.out.println(bool);
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void lastSixMonthsData() throws ParseException {
        ObservableList<Records> observableList = FXCollections.observableArrayList(getRowOutComes("medicamento"));
        Analyze analyze = new Analyze();
        System.out.println(analyze.getDateTimeAgo(13));
        System.out.println(observableList.size());
        ArrayList<Records> records = analyze.lastYearOfData(observableList);

        System.out.println(records.size());
        for(int i=0;i<records.size();i++){
            System.out.println(records.get(i).getProductName());
            System.out.println(records.get(i).getDateOfRecord());
            System.out.println(records.get(i).getQuantity());

        }

        int[][] matrix = analyze.separateDataByMonth(records);

        int[][] monthlyDemand = analyze.averageMonthlyDemand(matrix, "medicamento");

        sortMatrix(monthlyDemand, 1);

        for(int i=0;i<monthlyDemand.length;i++){
            System.out.println(monthlyDemand[i][0] + ", " + monthlyDemand[i][1]);
        }
    }

    @Test
    public void lastSixMonthsData1() {
    }

    @Test
    public void createHashMapProductList() {
        Analyze analyze = new Analyze();
        HashMap<Integer, String> hashMap = analyze.createHashMapProductList("medicamento");
        System.out.println(hashMap.get(1));
    }
}