package DB;

import Model.Areas;
import Model.Providers;

import java.sql.*;
import java.util.ArrayList;
import static DB.DataBase.*;

public interface AreasDAO extends IDBConection {

    default ArrayList<String> getAreasName(){
        ArrayList<String> areasName = new ArrayList<>();

        try{Connection connection = conectToDB();

            String sql = "SELECT * FROM " + TAREAS;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Areas area = new Areas(rs.getString(TAREAS_NOMBRE));
                areasName.add(area.getName());
            }

        }catch (SQLDataException e){
            e.printStackTrace();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return areasName;
    }

    default Integer getAreaIdByName(String name){
        int areaId = 0;

        try{
            Connection connection = conectToDB();
            String sql = "SELECT * FROM " + TAREAS + " WHERE " + TAREAS_NOMBRE + " = '" +name
                    + "' LIMIT 1";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Areas area = new Areas(
                        rs.getInt(TAREAS_ID),
                        rs.getString(TAREAS_NOMBRE));
                areaId = area.getId();
            }

        } catch (SQLDataException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return areaId;
    }
}
