package DB;

import Model.Alerts;
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

    /**
     * get all the product's name from a type of product
     * */
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

    /**
     * get a product's information according to a provided product's id
     * */
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

    /**
     * set a new product inside the data base
     * */
    default void addNewProduct(Products product){

        try{ Connection connection = conectToDB();

            String sql = " INSER INTO " + TPRODUCTOS + " VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, product.getCode());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, product.getManufacturerName());
            if (product.getClasification().equals("")){
                preparedStatement.setNull(5, Types.NULL);
            }else{
                preparedStatement.setString(5, product.getClasification());}

            preparedStatement.executeUpdate();

            Alerts.successfullAlert("Área agregada de manera exitosa!");

        } catch (SQLDataException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
