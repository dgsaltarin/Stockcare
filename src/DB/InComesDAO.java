package DB;

import Model.Alerts;
import Model.Records;
import javafx.collections.ObservableList;

import java.sql.*;

import static DB.DataBase.TENTRADAS;

public interface InComesDAO extends IDBConection {

    /**
     * save in the data base the record of a new income
     * */
    default void setInComeRecords(ObservableList<Records> observableList){
        Date date = new Date(observableList.get(0).getDateOfRecord().getTime());
        try{
            Connection connection = conectToDB();

            String sql = "INSERT INTO " + TENTRADAS + " VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for(int i=0; i<observableList.size();i++){
                preparedStatement.setNull(1, Types.NULL);
                preparedStatement.setDate(2, date);
                preparedStatement.setInt(3, observableList.get(i).getQuantity());
                preparedStatement.setInt(4, observableList.get(i).getAreaId());
                preparedStatement.setInt(5, observableList.get(i).getUserId());
                preparedStatement.setInt(6, observableList.get(i).getProductId());
                preparedStatement.setDouble(7, observableList.get(i).getUnitPrice());
                preparedStatement.setDouble(8, observableList.get(i).getTotalPrice());
                preparedStatement.executeUpdate();
            }

            Alerts.successfullAlert("Salida generada de manera exitosa!");
        }catch (SQLDataException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
