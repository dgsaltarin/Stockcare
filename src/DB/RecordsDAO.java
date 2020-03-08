package DB;

import Model.Records;

import java.sql.*;
import java.util.ArrayList;

import static DB.DataBase.*;

public interface RecordsDAO extends IDBConection {

    default ArrayList<Records> outComesList(String typeOfProduct){
        ArrayList<Records> outComes = new ArrayList<>();

        try(Connection connection = conectToDB()) {
            String sql = "SELECT productos.nombre_producto, " + TSALIDAS_CANTIDAD + ", " + TSALIDAS_PRECIO_UNITARIO + ", " + TSALIDAS_PRECIO_TOTAL
                     +", areas.nombre_area, "+ TSALIDAS_FECHA + ", usuarios.nombre_usuarios " + " FROM " + TSALIDAS
                     + " INNER JOIN " +TAREAS + " ON salidas.areas_id = areas.id " + " INNER JOIN " + TUSUARIOS +
                     " ON salidas.Usuarios_id=usuarios.id " + " INNER JOIN " + TPRODUCTOS +" ON salidas.productos_id=productos.id where"+
                     " productos.tipo_de_producto = '" + typeOfProduct +"' ORDER BY " +TSALIDAS_FECHA + " DESC";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Records recordsL = new Records(rs.getString(TPRODUCTOS_NOMBRE),
                        rs.getInt(TSALIDAS_CANTIDAD),
                        rs.getString(TAREAS_NOMBRE),
                        rs.getDate(TSALIDAS_FECHA),
                        rs.getString(TUSUARIOS_NOMBRE),
                        rs.getDouble(TSALIDAS_PRECIO_UNITARIO),
                        rs.getDouble(TSALIDAS_PRECIO_TOTAL));
                outComes.add(recordsL);
            }

        }catch (SQLDataException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return outComes;
    }

    default ArrayList<Records> inComesList(String typeOfProduct){
        ArrayList<Records> outComes = new ArrayList<>();

        try(Connection connection = conectToDB()) {
            String sql = "SELECT productos.nombre_producto, " + TENTRADAS_CANTIDAD + ", " + TENTRADAS_PRECIO_UNITARIO + ", " + TENTRADAS_PRECIO_TOTAL
                    +", proveedores.nombre_proveedor, "+ TENTRADAS_FECHA + ", usuarios.nombre_usuarios " + " FROM " + TENTRADAS + " INNER JOIN " +TPROVEEDORES+
                    " ON entradas.proveedores_id = proveedores.id " + " INNER JOIN " + TUSUARIOS +" ON entradas.Usuarios_id = usuarios.id"
                    +" INNER JOIN " + TPRODUCTOS +" ON entradas.productos_id=productos.id where productos.tipo_de_producto = '" +typeOfProduct+"' ORDER BY " +TENTRADAS_FECHA +" DESC";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Records recordsL = new Records(rs.getString(TPRODUCTOS_NOMBRE),
                        rs.getInt(TENTRADAS_CANTIDAD),
                        rs.getString(TPROVEEDORES_NOMBRE),
                        rs.getDate(TENTRADAS_FECHA),
                        rs.getString(TUSUARIOS_NOMBRE),
                        rs.getDouble(TSALIDAS_PRECIO_UNITARIO),
                        rs.getDouble(TSALIDAS_PRECIO_TOTAL));
                outComes.add(recordsL);
            }

        }catch (SQLDataException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return outComes;
    }
}
