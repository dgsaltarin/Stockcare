package DB;
import Model.Alerts;
import Model.Inventory;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import static DB.DataBase.*;

public interface InventoryDAO extends IDBConection, ProductsDAO {

        default ArrayList<Inventory> getInventoryWithName(String type) {
        ArrayList<Inventory>  inventory = new ArrayList<>();
        try{Connection connection = conectToDB();
            String sql = "SELECT " + TPRODUCTOS_NOMBRE_EXTERNO + ", " + TINVENTARIO_CANTIDAD + ", " + TINVENTARIO_VENCIMIENTO +
                    " FROM " + TINVENTARIO + " INNER JOIN " + TPRODUCTOS + " ON inventario.productos_id=productos.id WHERE " +
                    "productos.tipo_de_producto = " + "'" +type+"'" ;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while ((rs.next())){
                Inventory inventoryP = new Inventory(rs.getString(TPRODUCTOS_NOMBRE),
                        rs.getInt(TINVENTARIO_CANTIDAD),
                        rs.getDate(TINVENTARIO_VENCIMIENTO));
                inventory.add(inventoryP);
            }

        } catch (SQLDataException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    default ArrayList<Inventory> getInventory(String type){
        ArrayList<Inventory> inventory = new ArrayList<>();
        try(Connection connection = conectToDB()) {
            String sql = "SELECT * FROM " + TINVENTARIO + " INNER JOIN " + TPRODUCTOS +
                    " ON inventario.productos_id=productos.id WHERE " +
                    "productos.tipo_de_producto = " + "'" +type+"'";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Inventory inventario = new Inventory(
                        getProductById(rs.getInt(TINVENTARIO_PRODUCTOID)),
                        rs.getInt(TINVENTARIO_CANTIDAD),
                        rs.getDouble(TINVENTARIO_PRECIO_UNITARIO),
                        rs.getDate(TINVENTARIO_VENCIMIENTO));
                inventory.add(inventario);
            }

        }catch (SQLDataException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    default void updateInventory(ObservableList<Inventory> observableList){

        try{Connection connection = conectToDB();

            String sql = "";

            for (int i=0; i<observableList.size(); i++){

                if (observableList.get(i).getQuantity()==0){
                    sql = "DELETE FROM " +TINVENTARIO + " WHERE " + TINVENTARIO_PRODUCTOID +" = " + observableList.get(i).getProproductId()
                    + " AND " +TINVENTARIO_PRECIO_UNITARIO + " = " + observableList.get(i).getUnitPrice();

                }else{ sql = "UPDATE "+ TINVENTARIO+" SET "+ TINVENTARIO_CANTIDAD + " = ?" + " WHERE "
                        + TINVENTARIO_PRODUCTOID +" = " + observableList.get(i).getProproductId()
                        + " AND " +TINVENTARIO_PRECIO_UNITARIO + " = " + observableList.get(i).getUnitPrice();}

                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, observableList.get(i).getQuantity());
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

    default Inventory getInventoryItem(int idProduct, Double unitPrice, int outComeQuantity){
        Inventory inventoryItem = null;
        try(Connection connection = conectToDB()) {
            String sql = "SELECT * FROM " + TINVENTARIO + " WHERE " + TINVENTARIO_PRODUCTOID +
                    " = " + idProduct + " AND " + TINVENTARIO_PRECIO_UNITARIO + " = " + unitPrice;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                inventoryItem = new Inventory(
                        getProductById(rs.getInt(TINVENTARIO_PRODUCTOID)),
                        rs.getInt(TINVENTARIO_CANTIDAD) - outComeQuantity,
                        rs.getDouble(TINVENTARIO_PRECIO_UNITARIO),
                        rs.getDate(TINVENTARIO_VENCIMIENTO));
            }

        }catch (SQLDataException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryItem;
    }
}
