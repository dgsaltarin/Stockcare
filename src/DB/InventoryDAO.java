package DB;
import Model.Inventory;

import java.sql.*;
import java.util.ArrayList;
import static DB.DataBase.*;

public interface InventoryDAO extends IDBConection {

    default ArrayList<Inventory> getInventario(){
        ArrayList<Inventory> inventory = new ArrayList<>();
        try(Connection connection = conectToDB()) {
            String sql = "SELECT * FROM " + TINVENTARIO;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Inventory inventario = new Inventory(rs.getInt(TINVENTARIO_ID),
                        rs.getInt(TINVENTARIO_CANTIDAD),
                        rs.getDate(TINVENTARIO_VENCIMIENTO),
                        rs.getInt(TINVENTARIO_PRODUCTOID));
                inventory.add(inventario);
            }

        }catch (SQLDataException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

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
}
