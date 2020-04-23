package Model;

import DB.ProductsDAO;
import javafx.collections.ObservableList;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.*;
import java.util.stream.IntStream;

public class Analyze implements ProductsDAO {

    /**
     * given a observable list of outcomes it takes only the data that belongs to the last year
     * @param observableList observableList of OutComes
     * */
    protected ArrayList<Records> lastYearOfData(ObservableList<Records> observableList){
        ArrayList<Records> sixMonthsOfData = new ArrayList<>();

        for (Records records : observableList) {
            if (records.getDateOfRecord().after(getDateTimeAgo(13))) {
                sixMonthsOfData.add(records);
            }
        }

        return sixMonthsOfData;
    }


    /**
     * given the last year of data, this function separate the data in 30 days periods
     * for every product
     * @param arrayList arrayList of records
     * @return a matrix with 3 columns, quantity, product id and month
     * */
    protected int[][] separateDataByMonth(ArrayList<Records> arrayList){
        int[][] dataMatrix = new int[arrayList.size()][3];

        for (int i=1; i<13; i++){
            for (int j=0;j<arrayList.size();j++){
                if (arrayList.get(j).getDateOfRecord().before(getDateTimeAgo(i))
                &&arrayList.get(j).getDateOfRecord().after(getDateTimeAgo(i+1))){
                    dataMatrix[j][0] = arrayList.get(j).getQuantity();
                    dataMatrix[j][1] = arrayList.get(j).getProductId();
                    dataMatrix[j][2] = i;
                }
            }
        }

        return dataMatrix;
    }

    /**
     * given a matrix with demand separated by month for very product, this
     * add the demand for every product by every month, obtaining the total demand for
     * every product every month every year
     * */
    protected int[][] averageMonthlyDemand(int[][] matrix, String typeOfProduct){
        ArrayList<Products> product = productsList(typeOfProduct);
        int[][] monthlyDemand = new int[product.size()][2];
        for (int i=0;i<product.size();i++){
            int productOnWorkId = product.get(i).getCode();
            int averageDemand = 0;
            for(int j=1;j<13;j++){
                for (int[] matrix1 : matrix) {
                    if (productOnWorkId == matrix1[1] &&
                            j == matrix1[2]) {
                        averageDemand += matrix1[0];
                    }
                }
            averageDemand = (int) Math.ceil(averageDemand/12);
            }
            monthlyDemand[i][0] = productOnWorkId;
            monthlyDemand[i][1] = averageDemand;
        }

        return monthlyDemand;
    }

    /**
     * call the list of products and them create a hashMap with the code and the name
     * */
    protected HashMap<Integer, String> createHashMapProductList(String typeOfProduct){
        HashMap<Integer, String> productListHashMap = new HashMap<>();
        ArrayList<Products> productList = productsList(typeOfProduct);
        for (Products products : productList) {
            productListHashMap.put(products.getCode(), products.getName());
        }

        return productListHashMap;
    }

    /**
     * sort the given matrix according with a column
     * */
    public static void sortMatrix(int[][] matrix, int col){
        // Using built-in sort function Arrays.sort
        // Compare values according to columns
        Arrays.sort(matrix, (entry1, entry2) -> {

            // To sort in descending order revert
            // the '>' Operator
            if (entry1[col] < entry2[col])
                return 1;
            else
                return -1;
        });  // End of function call sort().

    }


    /**
     * given a matrix with the demand of a product for over a year, this calculate the monthly demand
     * of that product
     * */
    protected int[][] totalMonthlyDemand(int[][] annualProductDemand){
        int[][] totalMonthlyDemand = new int[12][2];
        int[] month = {1,2,3,4,5,6,7,8,9,10,11,12};

        for (int i=0;i<month.length;i++){
            int demand =0;
            for (int[] ints : annualProductDemand) {
                if (ints[2] == month[i]) {
                    demand += ints[0];
                }
            }
            totalMonthlyDemand[i][0] = demand;
            totalMonthlyDemand[i][1] = month[i];
        }

        return totalMonthlyDemand;
    }

    /**
     * given a matrix with the annual demand of a product, calculate the standard deviation for the data
     * @param monthlyProductDemand monthly demand for a product
     * */
    protected double standardDeviation(int[][] monthlyProductDemand){
        double standardDeviation;
        double averageDemand = averageDemandForProduct(monthlyProductDemand);
        double summatory = 0;
        float n = (float) 1/12;

        for (int[] ints : monthlyProductDemand) {
            summatory += Math.pow(ints[0] - averageDemand, 2);
        }

        standardDeviation = Math.sqrt(n*summatory);
        return standardDeviation;
    }

    /**
     * given a product's demand annual demand, calculate the slope of the data
     * */
    protected double slopeOfData(int[][] productDemand){
        int[] X = {1,2,3,4,5,6,7,8,9,10,11,12};
        double n = 12.0;
        double XSum = 0.0;
        double YSum = 0.0;
        double XYSum = 0.0;
        double XSquareSum = 0.0;
        double slope;

        for (int i=0; i<X.length;i++){
             XSum += X[i];
             YSum += productDemand[i][0];
             XYSum += X[i]*productDemand[i][0];
             XSquareSum += Math.pow(X[i], 2);
        }

        slope = (XYSum - ((XSum*YSum)/n))/(XSquareSum- (Math.pow(XSum,2)/n));

        return slope;
    }

    /**
     * given a number of estimations and matrix a monthly for a product
     * calculate noise base on the data for future predictions with this data
     * */
    protected double[] noiseForStimations(int numberOfEstimations, int[][] matrix){
        double[] noise = new double[numberOfEstimations];
        double[] noiseProbability = new double[numberOfEstimations];
        double averageDemand = averageDemandForProduct(matrix);

        for (int i = 0;i<noiseProbability.length;i++){
            noiseProbability[i] = Math.random();
        }

        double standardDeviation = standardDeviation(matrix);

        for(int i=0;i<noise.length;i++){
            NormalDistribution normalDistribution = new NormalDistribution(averageDemand, standardDeviation);
            double noiseResult = normalDistribution.inverseCumulativeProbability(noiseProbability[i]);
            noise[i] = noiseResult;
        }

        return noise;
    }

    /**
     * for this method, we received a product's annual demand, base on the information, we calculated
     * a seasonal index for 4 periods, we data is divided on 4, according to the date, a for every period of time
     * a seasonal index is calculated
     * */
    protected double[] seasonalIndex(int[][] matrix){
        double[] seasonalIndex = new double[12];
        double[] seasonalIndexes = new double[4];
        double[] averageDemand = new double[4];
        double[] totalDemand = new double[4];

        for (int[] matrix1 : matrix) {
            if (matrix1[1] <= 3)
                totalDemand[0] += matrix1[0];
            else if (matrix1[1] > 3 && matrix1[1] <= 6)
                totalDemand[1] += matrix1[0];
            else if (matrix1[1] > 6 && matrix1[1] <= 9)
                totalDemand[2] += matrix1[0];
            else if (matrix1[1] > 9 && matrix1[1] <= 12)
                totalDemand[3] += matrix1[0];
        }

        IntStream.range(0, totalDemand.length).forEach(i -> averageDemand[i] = totalDemand[i] / 3);
        double[] seasonalAverage = new double[1];
        IntStream.range(0,averageDemand.length).forEach(i -> seasonalAverage[0] += averageDemand[i]);
        IntStream.range(0, averageDemand.length).forEach(i -> seasonalIndexes[i] = averageDemand[i]/(seasonalAverage[0]/4));

        for (int i=0;i<12;i++){
            if (i<= 3)
                seasonalIndex[0] = seasonalIndexes[0];
                seasonalIndex[1] = seasonalIndexes[0];
                seasonalIndex[2] = seasonalIndexes[0];
            if (i > 3 && i <= 6)
                seasonalIndex[3] = seasonalIndexes[1];
                seasonalIndex[4] = seasonalIndexes[1];
                seasonalIndex[5] = seasonalIndexes[1];
            if (i > 6 && i <= 9)
                seasonalIndex[6] = seasonalIndexes[2];
                seasonalIndex[7] = seasonalIndexes[2];
                seasonalIndex[8] = seasonalIndexes[2];
            if (i > 9 && i <= 12)
                seasonalIndex[9] = seasonalIndexes[3];
                seasonalIndex[10] = seasonalIndexes[3];
                seasonalIndex[11] = seasonalIndexes[3];
        }
        return seasonalIndex;
    }

    protected double[][] simulations(int[][] productDemand){
        double[][] errors = new double[12][3];
        double[][] simulatedValues = new double[12][3];
        double dataSlope = slopeOfData(productDemand);
        double averageDemand = averageDemandForProduct(productDemand);
        double[] seasonalIndexes = seasonalIndex(productDemand);
        double[] noise = noiseForStimations(12, productDemand);

        for (int i=0;i<noise.length;i++){
            simulatedValues[i][0] = averageDemand + noise[1];
            simulatedValues[i][1] = averageDemand + (productDemand[i][0]*dataSlope) + noise[i];
            simulatedValues[i][2] = (averageDemand*seasonalIndexes[i]) + noise[i];

            errors[i][0] = productDemand[i][0] - simulatedValues[i][0];
            errors[i][1] = productDemand[i][0] - simulatedValues[i][0];
            errors[i][2] = productDemand[i][0] - simulatedValues[i][0];
        }

        return errors;
    }

    protected double[] calculateMAE(int[][] productDemand){
        double[][] errors = simulations(productDemand);
        double[] MAE = new double[3];

        for (int i = 0; i<MAE.length;i++){
            double totalError = 0.0;
            for (int j=0;j<productDemand.length;j++){
                totalError += errors[j][i];
            }
            MAE[i] = totalError/12;
        }
        return MAE;
    }


    /**
     * receive a product's demand and calculate the behavior of teh demand, 0 for constant, 1 for behavior with slope,
     * and 2 for seasonal behavior
     * */
    protected int chooseBehavior(int[][] productDemand){
        double[][] MAESamples = new double[50][3];
        double[] averageMAE = new double[3];

        for (int i=0;i<50;i++){
            double[] MAE = calculateMAE(productDemand);
            for (int j=0;j<MAE.length;j++){
                MAESamples[i][j] = MAE[j];
            }
        }

        for (int i=0;i<averageMAE.length;i++) {
            double totalMAE = 0.0;
            for (int j = 0; j < MAESamples.length; j++) {
                totalMAE += MAESamples[j][i];
            }
            averageMAE[i] = totalMAE/50;
        }

        int behavior = 0;

        if(averageMAE[0]<averageMAE[1]&&averageMAE[0]<averageMAE[2]){
            behavior = 0;
        }
        if(averageMAE[1]<averageMAE[0]&&averageMAE[1]<averageMAE[2]){
            behavior = 1;
        }
        if(averageMAE[2]<averageMAE[0]&&averageMAE[2]<averageMAE[1]){
            behavior = 2;
        }

        return behavior;
    }

    /**
     * given the monthly demand of a product, it returns the average demand
     * */
    protected double averageDemandForProduct(int[][] monthlyDemand){
        double averageDemand;
        int totalDemand = 0;

        for (int i=0;i<monthlyDemand.length;i++){
            totalDemand += monthlyDemand[i][0];
        }

        averageDemand = totalDemand/monthlyDemand.length;

        return averageDemand;
    }

    /**
     * calculate a past date according to the number of months given
     * @param  monthsAgo number of months to step back
     * */
    protected Date getDateTimeAgo(int monthsAgo){
        Date actualDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(actualDate);
        cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH)-monthsAgo));
        actualDate = cal.getTime();
        return actualDate;
    }
}
