package DB;

import Model.PurchaseOrder;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

import static DB.DataBase.*;

public interface PurchaseOrderDAO extends IDBConection, ProductsDAO, ProvidersDAO {


    /***
     * @description: get the last purchase order number in order to calculate the new purhase order number
     */
    default String orderNumber(){
        String orderNumerString ="";
        int orderNumber;

        try{
            Connection connection = conectToDB();

            String sql = "SELECT * FROM " + TORDEN_COMPRA + " ORDER BY "+ TORDEN_COMPRA_ID +" DESC LIMIT 1";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                PurchaseOrder purchaseOrder = new PurchaseOrder(rs.getInt(TORDEN_COMPRA_NUMERO));
                orderNumber = purchaseOrder.getOrderNumber()+1;

            }
            else {
                orderNumber = 1;
            }
            orderNumerString = String.valueOf(orderNumber);

        } catch (SQLDataException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return orderNumerString;
    }


    /***
     * @description: recibe a purchase order and set it into the data base
     */
    default void setPurchaseOrderInDB(ObservableList<PurchaseOrder> observableList){

        try{Connection connection = conectToDB();

            String sql = "INSERT INTO " +TORDEN_COMPRA +" VALUES (?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i=0; i<observableList.size(); i++){
                preparedStatement.setNull(1, Types.NULL);
                preparedStatement.setInt(2, observableList.get(i).getOrderNumber());
                preparedStatement.setInt(3, observableList.get(i).getQuantity());
                preparedStatement.setInt(4, observableList.get(i).getProductCode());
                preparedStatement.setInt(5, observableList.get(i).getProviderCode());
                preparedStatement.setBoolean(6,observableList.get(i).isOrderState());
                preparedStatement.executeUpdate();
            }
        } catch (SQLDataException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @description: change the status of a purchase order once it has been recibed
     * */
    default void updatePurchaseOrderState(){}

    default ArrayList<PurchaseOrder> getPurchaseOrderPending(){
        ArrayList<PurchaseOrder> purchaseOrders = new ArrayList<>();

        try{
            Connection connection = conectToDB();
            String sql = "SELECT * FROM " + TORDEN_COMPRA + " WHERE "+ TORDEN_COMPRA_ESTADO +" = 0";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                PurchaseOrder purchaseOrder = new PurchaseOrder(rs.getInt(TORDEN_COMPRA_NUMERO),
                        rs.getInt(TORDEN_COMPRA_CANTIDAD),
                        getProductById(rs.getInt(TORDEN_COMPRA_PRODUCTO)),
                        getProviderById(rs.getInt(TORDEN_COMPRA_PROVEEDOR)),
                        rs.getBoolean(TORDEN_COMPRA_ESTADO));
                purchaseOrders.add(purchaseOrder);
            }
        } catch (SQLDataException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return purchaseOrders;
    }

    /***
     * @description: recibe a purchase order number and bring back all the records with the same purchase order numer
     * @param purchaseOrderNumber recibe a purchase order number and bring back the full purchase order
     */
    default ArrayList<PurchaseOrder> getPurchaseOrderByNumber(int purchaseOrderNumber){
        ArrayList<PurchaseOrder> fullPurchaseOrder = new ArrayList<>();

        try{
            Connection connection = conectToDB();
            String sql = "SELECT * FROM " + TORDEN_COMPRA + " WHERE "+ TORDEN_COMPRA_NUMERO +" = " + purchaseOrderNumber;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                PurchaseOrder purchaseOrder = new PurchaseOrder(rs.getInt(TORDEN_COMPRA_NUMERO),
                        rs.getInt(TORDEN_COMPRA_CANTIDAD),
                        getProductById(rs.getInt(TORDEN_COMPRA_PRODUCTO)),
                        getProviderById(rs.getInt(TORDEN_COMPRA_PROVEEDOR)),
                        rs.getBoolean(TORDEN_COMPRA_ESTADO));
                fullPurchaseOrder.add(purchaseOrder);
            }
        } catch (SQLDataException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return fullPurchaseOrder;
    }
}
