package Model;

import DB.RecordsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

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
        ObservableList<Records> observableList = FXCollections.observableArrayList(outComesList("medicamento"));
        Analyze analyze = new Analyze();
        System.out.println(analyze.getDateTimeAgo(13));
        System.out.println(observableList.size());
        ArrayList<Records> records = analyze.lastSixMonthsData(observableList);

        System.out.println(records.size());
        for(int i=0;i<records.size();i++){
            System.out.println(records.get(i).getProductName());
            System.out.println(records.get(i).getDateOfRecord());
            System.out.println(records.get(i).getQuantity());

        }

        Integer[][] matrix = analyze.separateDataByMonth(records);

        for (int i=0; i<matrix.length; i++){
            System.out.println(matrix[i][0]);
            System.out.println(matrix[i][1]);
        }
    }
}