package DB;

import Model.PurchaseOrder;

import java.sql.*;
import java.util.ArrayList;

import static DB.DataBase.*;

public interface PurchaseOrderDAO extends IDBConection {

    default String orderNumber(){
        String orderNumerString ="";
        int orderNumber;

        try{
            Connection connection = conectToDB();

            String sql = "SELECT * FROM " + TORDEN_COMPRA + " ORDER BY "+ TORDEN_COMPRA_ID +" DESC LIMIT 1";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if(!rs.wasNull()){
                orderNumber = 1;
            }
            else {PurchaseOrder purchaseOrder = new PurchaseOrder(rs.getInt(TORDEN_COMPRA_NUMERO));
                orderNumber = purchaseOrder.getOrderNumber()+1;}
            orderNumerString = String.valueOf(orderNumber);

        } catch (SQLDataException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return orderNumerString;
    }

    default void setPurchaseOrder(){

    }

    default ArrayList<PurchaseOrder> getPurchaseOrder(){
        ArrayList<PurchaseOrder> purchaseOrderArrayList = new ArrayList<>();
        return  purchaseOrderArrayList;
    }
}
