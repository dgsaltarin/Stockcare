package DB;
import Model.Alerts;
import Model.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import static DB.DataBase.*;

public interface InventoryDAO extends IDBConection, ProductsDAO {

    /**
     * get all inventory from a product's category
     * */
    default ArrayList<Inventory> getInventory(String type){
        ArrayList<Inventory> inventory = new ArrayList<>();
        try(Connection connection = conectToDB()) {
            String sql = "SELECT * FROM " + TINVENTARIO + " INNER JOIN " + TPRODUCTOS +
                    " ON inventario.productos_id=productos.id WHERE " +
                    "productos.tipo_de_producto = " + "'" +type+"'";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Inventory inventoryItem = new Inventory(rs.getInt(TINVENTARIO_ID),
                        getProductById(rs.getInt(TINVENTARIO_PRODUCTOID)),
                        rs.getInt(TINVENTARIO_CANTIDAD),
                        rs.getDouble(TINVENTARIO_PRECIO_UNITARIO),
                        rs.getDate(TINVENTARIO_VENCIMIENTO));
                inventory.add(inventoryItem);
            }

        }catch (SQLDataException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    /**
     * update the inventory data base once and outcome or income have been made
     * */
    default void updateInventory(ObservableList<Inventory> observableList){

        try{Connection connection = conectToDB();

            String sql = "";

            String requestSql = "SELECT * FROM "+ TINVENTARIO;
            ObservableList<Inventory> allInventory = FXCollections.observableArrayList();
            ObservableList<Integer> inventoryIDs = FXCollections.observableArrayList();

            PreparedStatement inventoryStatement = connection.prepareStatement(requestSql);
            ResultSet resultSet = inventoryStatement.executeQuery();

            while (resultSet.next()){
                Inventory item = new Inventory(resultSet.getInt(TINVENTARIO_ID),
                        getProductById(resultSet.getInt(TINVENTARIO_PRODUCTOID)),
                        resultSet.getInt(TINVENTARIO_CANTIDAD), resultSet.getDouble(TINVENTARIO_CANTIDAD),
                        resultSet.getDate(TINVENTARIO_VENCIMIENTO));
                int itemID = resultSet.getInt(TINVENTARIO_ID);
                inventoryIDs.add(itemID);
                allInventory.add(item);
            }

            PreparedStatement preparedStatement = null;

            for (int i=0; i<observableList.size(); i++){

                    if (inventoryIDs.contains(observableList.get(i).getId())){

                        if (observableList.get(i).getQuantity()<=0){
                            sql = "DELETE FROM " +TINVENTARIO + " WHERE " + TINVENTARIO_ID +" = " + observableList.get(i).getId();

                            preparedStatement = connection.prepareStatement(sql);

                        }else{ sql = "UPDATE "+ TINVENTARIO+" SET "+ TINVENTARIO_CANTIDAD + " = ?" + " WHERE "
                                + TINVENTARIO_ID +" = " + observableList.get(i).getId();

                            preparedStatement = connection.prepareStatement(sql);

                            preparedStatement.setInt(1, observableList.get(i).getQuantity());}

                    } else if (allInventory.contains(observableList.get(i).getId())){
                            Date date = new Date(observableList.get(i).getExpirationDate().getTime());
                            sql = " INSERT INTO " + TINVENTARIO + " VALUES (?, ?, ?, ?, ?)";
                            preparedStatement = connection.prepareStatement(sql);

                            preparedStatement.setNull(1,Types.NULL);
                            preparedStatement.setInt(2,observableList.get(i).getQuantity());
                            preparedStatement.setDate(3,date);
                            preparedStatement.setInt(4,observableList.get(i).getProductId());
                            preparedStatement.setDouble(5,observableList.get(i).getUnitPrice());
                    }

                preparedStatement.executeUpdate();
            }
        } catch (SQLDataException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        Alerts.successfullAlert("Inventario actualizado de manera exitosa!");
    }

}
