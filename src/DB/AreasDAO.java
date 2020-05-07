package DB;

import Model.Alerts;
import Model.Areas;

import java.sql.*;
import java.util.ArrayList;

import static DB.DataBase.*;

public interface AreasDAO extends IDBConection {

    /**
     * get the area's name
     * */
    default ArrayList<String> getAreasName() {
        ArrayList<String> areasName = new ArrayList<>();

        try {
            Connection connection = conectToDB();

            String sql = "SELECT * FROM " + TAREAS;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Areas area = new Areas(rs.getString(TAREAS_NOMBRE));
                areasName.add(area.getName());
            }

        } catch (SQLDataException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return areasName;
    }

    /**
     * get the area's information according to a provided name
     * */
    default Integer getAreaIdByName(String name) {
        int areaId = 0;

        try {
            Connection connection = conectToDB();
            String sql = "SELECT * FROM " + TAREAS + " WHERE " + TAREAS_NOMBRE + " = '" + name
                    + "' LIMIT 1";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Areas area = new Areas(
                        rs.getInt(TAREAS_ID),
                        rs.getString(TAREAS_NOMBRE));
                areaId = area.getId();
            }

        } catch (SQLDataException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return areaId;
    }

    /**
     * set a new area inside the data base
     * */
    default void addNewArea(Areas area) {

        try {
            Connection connection = conectToDB();

            String sql = "INSERT INTO " + TAREAS + " VALUES(?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setNull(1, Types.NULL);
            preparedStatement.setString(2, area.getName());

            preparedStatement.executeUpdate();

            Alerts.successfullAlert("Área agregada de manera exitosa!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
