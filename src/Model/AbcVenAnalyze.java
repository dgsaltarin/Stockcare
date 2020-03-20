package Model;

import DB.RecordsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.HashMap;

public class AbcVenAnalyze extends AnalyzeABC implements RecordsDAO {

    private String abcVenClassification;

    /**
     * @description taking the last year of information, this function make a Abc-Ven analyze, just for medicines
     * */
    public ObservableList<AbcVenAnalyze> generateAnalyze(){
        ObservableList<AbcVenAnalyze> result = FXCollections.observableArrayList();

        ObservableList<Records> records = FXCollections.observableArrayList(this.getRowOutComes("medicamento"));
        ObservableList<AnalyzeABC> abcAnalyze = ABCAnalyze(records, "medicamento");

        HashMap<String, String> productClassification = createHashMapProductClassification();

        for (int i=0; i<abcAnalyze.size();i++){
            String finalClassification = abcAnalyze.get(i).getClassification() + productClassification.get(abcAnalyze.get(i).productName);
            AbcVenAnalyze abcVenAnalyze = new AbcVenAnalyze(abcAnalyze.get(i).getProductName(), abcAnalyze.get(i).getClassification(), finalClassification);
            result.add(abcVenAnalyze);
        }

        return result;
    }

    /**
     * @description create a hashMap with the names of the medicines and their respective VEN classification
     * */
    private HashMap<String, String> createHashMapProductClassification(){
        HashMap<String, String> productListHashMap = new HashMap<>();
        ArrayList<Products> productList = productsList("medicamento");
        for (int i=0; i<productList.size();i++){
            productListHashMap.put(productList.get(i).getName(), productList.get(i).getClasification());
        }

        return productListHashMap;
    }


    /**
     * @description taking the information from a ABC-VEN analyze, create a pie chart to illustrate the data
     * */
    public ObservableList<PieChart.Data> createPieChart(ObservableList<AbcVenAnalyze> observableList){
        int avClassification =0;
        int bvClassification =0;
        int cvClassification =0;
        int aeClassification =0;
        int beClassification =0;
        int ceClassification =0;
        int anClassification =0;
        int bnClassification =0;
        int cnClassification =0;
        //get the account of item on every classification
        for (int i=0;i<observableList.size();i++){
            if(observableList.get(i).getAbcVenClassification().equals("AV")){
                avClassification += 1;
            }else if(observableList.get(i).getAbcVenClassification().equals("BV")){
                bvClassification += 1;
            }else if(observableList.get(i).getAbcVenClassification().equals("CV")){
                cvClassification += 1;
            }else if(observableList.get(i).getAbcVenClassification().equals("AE")){
                aeClassification += 1;
            }else if(observableList.get(i).getAbcVenClassification().equals("BE")){
                beClassification += 1;
            }else if(observableList.get(i).getAbcVenClassification().equals("CE")){
                ceClassification += 1;
            }else if(observableList.get(i).getAbcVenClassification().equals("AB")){
                anClassification += 1;
            }else if(observableList.get(i).getAbcVenClassification().equals("BN")){
                bnClassification += 1;
            }else if(observableList.get(i).getAbcVenClassification().equals("CN")){
                cnClassification += 1;
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("AV", avClassification),
                new PieChart.Data("BV", bvClassification),
                new PieChart.Data("CV", cvClassification),
                new PieChart.Data("AE", aeClassification),
                new PieChart.Data("BE", beClassification),
                new PieChart.Data("CE", ceClassification),
                new PieChart.Data("AN", anClassification),
                new PieChart.Data("BN", bnClassification),
                new PieChart.Data("CN", cnClassification));
        return pieChartData;
    }

    public AbcVenAnalyze(){}

    public AbcVenAnalyze(String productName, String abcClassification, String abcVenClassification){
        this.productName = productName;
        this.classification = abcClassification;
        this.abcVenClassification = abcVenClassification;
    }

    public String getAbcVenClassification() {
        return abcVenClassification;
    }
}
