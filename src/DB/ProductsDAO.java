package DB;

import Model.Products;

import java.sql.*;
import java.util.ArrayList;

import static DB.DataBase.*;

public interface ProductsDAO extends IDBConection {

    /**
     * @author dgsaltarin
     * busca dentri de la base de datos la lista de productos de un tipo especifico
     * @param typeOfProduct recibe el tipo de producto que sera buscado dentro de la base de datos
     * */

    default ArrayList<Products> productsList(String typeOfProduct){
        ArrayList<Products> products = new ArrayList<>();

        try(Connection connection = conectToDB()) {
            String sql = "SELECT * FROM " + TPRODUCTOS + " WHERE " + TPRODUCTOS_TIPO + "= '" + typeOfProduct +"'";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Products producto = new Products(rs.getInt(TPRODUCTOS_CODIGO),
                        rs.getString(TPRODUCTOS_NOMBRE),
                        rs.getDouble(TPRODUCTOS_PRECIO),
                        rs.getString(TPRODUCTOS_CLASIFICACION));
                products.add(producto);
            }

        }catch (SQLDataException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    default ArrayList<Products> productsNames(String typeOfProduct){
        ArrayList<Products> products = new ArrayList<>();

        try(Connection connection = conectToDB()) {
            String sql = "SELECT * FROM " + TPRODUCTOS + " WHERE " + TPRODUCTOS_TIPO + "= '" + typeOfProduct +"'";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Products producto = new Products(rs.getString(TPRODUCTOS_NOMBRE),
                        rs.getInt(TPRODUCTOS_CODIGO));
                products.add(producto);
            }

        }catch (SQLDataException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    default Products getProductById(Integer id){
        Products product = null;

        try(Connection connection = conectToDB()) {
            String sql = "SELECT * FROM " + TPRODUCTOS + " WHERE " + TPRODUCTOS_CODIGO + "= '" + id +"'";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                product = new Products(rs.getInt(TPRODUCTOS_CODIGO),
                        rs.getString(TPRODUCTOS_NOMBRE),
                        rs.getDouble(TPRODUCTOS_PRECIO),
                        rs.getString(TPRODUCTOS_CLASIFICACION));
            }

        }catch (SQLDataException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
}
