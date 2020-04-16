package DB;

import Model.Users;

import java.sql.*;
import java.util.ArrayList;
import static DB.DataBase.*;

public interface UserDAO extends IDBConection {

    default ArrayList<Users> getUsersList(){
        ArrayList<Users> usersList = null;

        try { Connection connection = conectToDB();

            String sql = " SELECT * FROM " + TUSUARIOS;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Users user = new Users(rs.getInt(TUSUARIOS_ID),
                        rs.getString(TUSUARIOS_NOMBRE),
                        rs.getString(TUSUARIOS_TIPO),
                        rs.getString(TUSUARIOS_CONTRASEÑA));
                usersList.add(user);
            }
        } catch (SQLDataException e){
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return usersList;
    }

    default Users getUserByName(String name){
        Users user = new Users();

        try {
            Connection connection = conectToDB();

            String sql = "SELECT * FROM " + TUSUARIOS + " WHERE " + TUSUARIOS_NOMBRE + " = '" + name +"'";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                user = new Users(rs.getInt(TUSUARIOS_ID),
                        rs.getString(TUSUARIOS_NOMBRE),
                        rs.getString(TUSUARIOS_TIPO),
                        rs.getString(TUSUARIOS_CONTRASEÑA));

            }
        } catch (SQLDataException e){
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
