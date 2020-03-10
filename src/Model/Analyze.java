package Model;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Analyze {

    /**
     * @description: diven a observable list of outcomes it takes only the data that belongs to the last six months
     * @param observableList observableList of OutComes
     * */
    protected ArrayList<Records> lastSixMonthsData(ObservableList<Records> observableList){
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

    protected Integer[][] separateDataByMonth(ArrayList<Records> arrayList){
        Integer[][] dataMatrix = new Integer[arrayList.size()][2];

        for (int i=1; i<7; i++){
            for (int j=0;j<arrayList.size();j++){
                if (arrayList.get(j).getDateOfRecord().before(getDateTimeAgo(i))
                &&arrayList.get(j).getDateOfRecord().after(getDateTimeAgo(i+1))){
                    dataMatrix[j][0] = arrayList.get(j).getQuantity();
                    dataMatrix[j][1] = 1;
                }
            }
        }

        return dataMatrix;
    }

    protected Date getDateTimeAgo(int monthsAgo){
        Date actualDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(actualDate);
        cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH)-monthsAgo));
        actualDate = cal.getTime();
        return actualDate;
    }
}
