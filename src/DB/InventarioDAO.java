package DB;
import Model.Inventario;

import java.sql.*;
import java.util.ArrayList;
import static DB.DataBase.*;

public interface InventarioDAO extends IDBConection {

    default ArrayList<Inventario> getInventario(){
        ArrayList<Inventario> inventarios = new ArrayList<>();
        try(Connection connection = conectToDB()) {
            String sql = "SELECT * FROM " + TINVENTARIO;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Inventario inventario = new Inventario(rs.getInt(TINVENTARIO_ID),
                        rs.getInt(TINVENTARIO_CANTIDAD),
                        rs.getDate(TINVENTARIO_VENCIMIENTO),
                        rs.getInt(TINVENTARIO_PRODUCTOID));
                inventarios.add(inventario);
            }

        }catch (SQLDataException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventarios;
    }
}
