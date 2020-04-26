package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.HashMap;

public class InventoryPolicies extends Analyze {

    private String productName;
    private String classification;
    private int behavior;
    private String behaviorString;
    private double averageDemand;
    private double quantityToOrder;
    private double whenToOrder;
    private double cost;

    //constants of the model
    private double K= 42.916;//cost of put a purchase order
    private double keepInventory = 0.7669;//percentage of keeping inventory x unit cost
    private ArrayList<Double> D = new ArrayList<>();//average demand for the product
    private ArrayList<Double> deviations = new ArrayList<>();//standard Deviation for the products

    /**
     * take the product's records from a product's type and give back just the data where the demand is > 0
     * @param rowData outCome record from a product's type
     * @param typeOfProduct product's type for the outCome records
     * */
    public ObservableList<InventoryPolicies> generatePolicies(ObservableList<Records> rowData, String typeOfProduct){
        double z;
        double L = 0.25;
        double P = 1;
        ObservableList<InventoryPolicies> policies = FXCollections.observableArrayList();
        ObservableList<String> productNames = FXCollections.observableArrayList();
        ObservableList<String> classifications = FXCollections.observableArrayList();

        //get the classification for the products, for medicines use ABC-VEN, for medical device and inputs
        if (typeOfProduct.equals("medicamento")){
            AbcVenAnalyze analyze = new AbcVenAnalyze();
            ObservableList<AbcVenAnalyze> classificationABC = analyze.generateAnalyze();
            for (AbcVenAnalyze item:classificationABC){
                productNames.add(item.productName);
                classifications.add(item.getAbcVenClassification());
            }
        } if (typeOfProduct.equals("dispositivo médico")||typeOfProduct.equals("insumo")){
            AnalyzeABC analyze = new AnalyzeABC();
            ObservableList<AnalyzeABC> classificationABC = analyze.ABCAnalyze(rowData, typeOfProduct);
            for (AnalyzeABC item:classificationABC){
                productNames.add(item.productName);
                classifications.add(item.getClassification());
            }
        }

        //get the behavior for every product witch is in the abc analyse's result
        BehaviorAnalyze behaviorAnalyze = new BehaviorAnalyze();
        ObservableList<String> behaviors = FXCollections.observableArrayList();

        //get the hashMap for the product's price by name
        HashMap<String, Double> productPrice = createHashMapProductPriceName(typeOfProduct);

        for (String product:productNames) {
            ObservableList<Records> productData = behaviorAnalyze.productRecordsData(rowData, product);
            double[] result = calculateDAndDeviation(productData);
            D.add(result[0]);
            deviations.add(result[1]);
            String behavior = behaviorAnalyze.chooseBehavior(productData);
            behaviors.add(behavior);
        }

        for (int i=0;i<classifications.size();i++){
            String itemClassification = classifications.get(i);
            String itemName = productNames.get(i);
            double H = productPrice.get(itemName)*keepInventory;
            double d = D.get(i);
            double deviation = deviations.get(i);
            double[] modelResult = new double[2];

            //depending of the product's type, choose a model and calculate the policies
            if (typeOfProduct.equals("medicamento")){

                if(itemClassification.equals("AV")||itemClassification.equals("BV")||itemClassification.equals("CV")){
                    z = 1.644854;
                     modelResult = modelQR(d, H, z, deviation, L);
                }
                if(itemClassification.equals("AE")||itemClassification.equals("AN")||itemClassification.equals("BE")
                        ||behaviors.get(i).equals("BN")||behaviors.get(i).equals("CE")){
                    z = 0.841621;
                    modelResult = modelQR(d, H, z, deviation, L);
                }
                if(itemClassification.equals("CN")){
                    modelResult = periodicalModel(d, L, P);
                }

            } if(typeOfProduct.equals("dispositivo médico")||typeOfProduct.equals("insumo")){
                if(itemClassification.equals("A")){
                    z = 1.644854;
                    modelResult = modelQR(d, H, z, deviation, L);
                }
                if(itemClassification.equals("B")||itemClassification.equals("C")){
                    z = 0.841621;
                    modelResult = modelQR(d, H, z, deviation, L);
                }
            }

            double inventoryCost = modelResult[0]*productPrice.get(itemName);

            InventoryPolicies policyItem = new InventoryPolicies(productNames.get(i), itemClassification, behaviors.get(i), d, modelResult[0], modelResult[1], inventoryCost);
            policies.add(policyItem);
        }

        return policies;
    }

    /**
     * given the product´s demand, calculate the average demand and the standard deviation for a product
     * */
    private double[] calculateDAndDeviation(ObservableList<Records> productData){
        double[] result = new double[2];
        ArrayList<Records> lastYearDemand = lastYearOfData(productData);
        int[][] separatedData = separateDataByMonth(lastYearDemand);
        int[][] monthlyDemand = totalMonthlyDemand(separatedData);

        result[0] = averageDemandForProduct(monthlyDemand);
        result[1] = standardDeviation(monthlyDemand);
        return result;
    }

    /**
     * inventory model to calculate the inventory policies
     * */
    public double[] modelQR(double D, double h, double z, double des, double L){
        double[] polity = new double[2];
        polity[0] = ((Math.sqrt((2*K*D)/h)+(z*des)));
        polity[1] = (D*L);
        return polity;
    }

    /**
     * inventory model to calculate the inventory policies
     * */
    public double[] periodicalModel(double D, double L, double P){
        double[] polity = new double[2];
        polity[0] = (D*(L+P));
        polity[1] = (1.0);
        return polity;
    }

    public InventoryPolicies(){}

    public InventoryPolicies(String productName, String classification, String behaviorString,double averageDemand, double Q, double R, double inventoryCost) {
        this.productName = productName;
        this.classification = classification;
        this.behaviorString = behaviorString;
        this.averageDemand = averageDemand;
        this.quantityToOrder = Q;
        this.cost = inventoryCost;
        this.whenToOrder = R;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public int getBehavior() {
        return behavior;
    }

    public void setBehavior(int behavior) {
        this.behavior = behavior;
    }

    public String getBehaviorString() {
        return behaviorString;
    }

    public void setBehaviorString(String behaviorString) {
        this.behaviorString = behaviorString;
    }

    public double getAverageDemand() {
        return averageDemand;
    }

    public void setAverageDemand(double averageDemand) {
        this.averageDemand = averageDemand;
    }

    public double getQuantityToOrder() {
        return quantityToOrder;
    }

    public void setQuantityToOrder(double quantityToOrder) {
        this.quantityToOrder = quantityToOrder;
    }

    public double getWhenToOrder() {
        return whenToOrder;
    }

    public void setWhenToOrder(double whenToOrder) {
        this.whenToOrder = whenToOrder;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
