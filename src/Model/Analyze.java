package Model;

import DB.ProductsDAO;
import javafx.collections.ObservableList;

import java.util.*;

public class Analyze implements ProductsDAO {

    /**
     * @description: diven a observable list of outcomes it takes only the data that belongs to the last year
     * @param observableList observableList of OutComes
     * */
    protected ArrayList<Records> lastYearOfData(ObservableList<Records> observableList){
        ArrayList<Records> sixMonthsOfData = new ArrayList<>();

        for(int i=0; i<observableList.size();i++){
            if (observableList.get(i).getDateOfRecord().after(getDateTimeAgo(13))){
                sixMonthsOfData.add(observableList.get(i));
            }
            else
                continue;
        }

        return sixMonthsOfData;
    }


    /**
     * @description: given the last year of data, this function separate the data in 30 days periods
     * for every product
     * @param arrayList arrayList of records
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
     * @description: given a matrix with demand separated by month for very product, this
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
                for (int m = 0;m<matrix.length;m++){
                    if (productOnWorkId==matrix[m][1]&&
                    j==matrix[m][2]){
                        averageDemand += matrix[m][0];
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
     * @description call the list of products and them create a hashMap with the code and the name
     * */
    protected HashMap<Integer, String> createHashMapProductList(String typeOfProduct){
        HashMap<Integer, String> productListHashMap = new HashMap<>();
        ArrayList<Products> productList = productsList(typeOfProduct);
        for (int i=0; i<productList.size();i++){
            productListHashMap.put(productList.get(i).getCode(), productList.get(i).getName());
        }

        return productListHashMap;
    }

    /**
     * @description sort the given matrix according with a column
     * */
    public static void sortMatrix(int[][] matrix, int col){
        // Using built-in sort function Arrays.sort
        Arrays.sort(matrix, new Comparator<int[]>() {

            @Override
            // Compare values according to columns
            public int compare(final int[] entry1,
                               final int[] entry2) {

                // To sort in descending order revert
                // the '>' Operator
                if (entry1[col] < entry2[col])
                    return 1;
                else
                    return -1;
            }
        });  // End of function call sort().

    }


    /**
     * @description: given a matrix with the demand of a product for over a year, this calculate the monthly demand
     * of that product
     * */
    protected int[][] totalMonthlyDemand(int[][] annualProductDemand){
        int[][] totalMonthlyDemand = new int[12][2];
        int[] month = {1,2,3,4,5,6,7,8,9,10,11,12};

        for (int i=0;i<month.length;i++){
            int demand =0;
            for (int j=0;j<annualProductDemand.length;j++){
                if (annualProductDemand[j][2]==month[i]){
                    demand += annualProductDemand[j][0];
                }
            }
            totalMonthlyDemand[i][0] = demand;
            totalMonthlyDemand[i][1] = month[i];
        }

        return totalMonthlyDemand;
    }

    /**
     * @description given a matrix with the annual demand of a product, calculate the standard deviation for the data
     * @param monthlyProductDemand monthly demand for a product
     * */
    protected double standardDeviation(int[][] monthlyProductDemand){
        double standardDeviation;
        double averageDemand = averageDemandForProduct(monthlyProductDemand);
        double summatory = 0;
        float n = (float) 1/12;

        for (int i=0;i<monthlyProductDemand.length;i++){
            summatory += Math.pow(monthlyProductDemand[i][0]-averageDemand,2);
        }

        standardDeviation = Math.sqrt(n*summatory);
        return standardDeviation;
    }

    protected double slopeOfData(){
        double slope=0;

        return slope;
    }

    protected ArrayList<Double> noiseForStimations(){
        ArrayList<Double> noise = null;

        return noise;
    }

    /**
     * @description given the monthly demand of a product, it returns the average demand
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
     * @description calculate a past date according to the number of months given
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
