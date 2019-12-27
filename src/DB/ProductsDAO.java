package DB;

import Model.Products;

import java.sql.*;
import java.util.ArrayList;

import static DB.DataBase.*;

public interface ProductsDAO extends IDBConection {

    /**
     * @author dgsaltarin
     * busca dentri de la base de datos la lista de productos de un tipo especifico
     * @param tipo recibe el tipo de producto que sera buscado dentro de la base de datos
     * */

    default ArrayList<Products> productsList(String tipo){
        ArrayList<Products> products = new ArrayList<>();

        try(Connection connection = conectToDB()) {
            String sql = "SELECT * FROM " + TPRODUCTOS + " WHERE " + TPRODUCTOS_TIPO + "= '" + tipo +"'";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Products producto = new Products(rs.getInt(TPRODUCTOS_CODIGO),
                        rs.getString(TPRODUCTOS_NOMBRE),
                        rs.getDouble(TPRODUCTOS_PRECIO));
                products.add(producto);
            }

        }catch (SQLDataException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
