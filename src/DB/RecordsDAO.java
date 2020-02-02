package DB;

import Model.Records;
import Model.RecordsOutcomes;

import java.sql.*;
import java.util.ArrayList;

import static DB.DataBase.*;

public interface RecordsDAO extends IDBConection {

    default ArrayList<Records> outComesList(String typeOfProduct){
        ArrayList<Records> outComes = new ArrayList<>();

        try(Connection connection = conectToDB()) {
            String sql = "SELECT productos.nombre_producto, " + TSALIDAS_CANTIDAD+", areas.nombre_area, "+ TSALIDAS_FECHA +
                    ", usuarios.nombre_usuarios " + " FROM " + TSALIDAS + " INNER JOIN " + TPRODUCTOS +" ON salidas.productos_id=productos.id " +
                    " INNER JOIN " +TAREAS+ " ON salidas.areas_id = areas.id " + " INNER JOIN " + TUSUARIOS +
                    " ON salidas.Usuarios_id=usuarios.id ORDER BY " +TSALIDAS_FECHA;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Records recordsL = new Records(rs.getString(TPRODUCTOS_NOMBRE),
                        rs.getInt(TSALIDAS_CANTIDAD),
                        rs.getString(TAREAS_NOMBRE),
                        rs.getDate(TSALIDAS_FECHA),
                        rs.getString(TUSUARIOS_NOMBRE));
                outComes.add(recordsL);
            }

        }catch (SQLDataException e){

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return outComes;
    }
}
