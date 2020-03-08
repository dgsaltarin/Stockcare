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
            if (observableList.get(i).getDateOfRecord().after(getDateSixMonthAgo())){
                sixMonthsOfData.add(observableList.get(i));
            }
            else
                continue;
        }

        return sixMonthsOfData;
    }

    protected Date getDateSixMonthAgo(){
        Date actualDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(actualDate);
        cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH)-6));
        actualDate = cal.getTime();
        return actualDate;
    }
}
