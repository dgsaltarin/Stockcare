package DB;

import Model.Users;

import java.sql.*;
import java.util.ArrayList;
import static DB.DataBase.*;

public interface UserDAO extends IDBConection {

    /**
     * get the list of all users
     * */
    default ArrayList<Users> getUsersList(){
        ArrayList<Users> usersList = new ArrayList<>();

        try { Connection connection = conectToDB();

            String sql = " SELECT * FROM " + TUSUARIOS;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Users user = new Users(rs.getInt(TUSUARIOS_ID),
                        rs.getString(TUSUARIOS_NOMBRE),
                        rs.getString(TUSUARIOS_TIPO),
                        rs.getString(TUSUARIOS_CONTRASEÑA),
                        rs.getString(TUSUARIOS_USUARIO));
                usersList.add(user);
            }
        } catch (SQLDataException e){
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return usersList;
    }

    /**
     * get an user's information according to an id
     * */
    default Users getUserByUser(String user){
        Users users = new Users();

        try {
            Connection connection = conectToDB();

            String sql = "SELECT * FROM " + TUSUARIOS + " WHERE " + TUSUARIOS_USUARIO + " = '" + user +"'";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                users = new Users(rs.getInt(TUSUARIOS_ID),
                        rs.getString(TUSUARIOS_NOMBRE),
                        rs.getString(TUSUARIOS_TIPO),
                        rs.getString(TUSUARIOS_CONTRASEÑA),
                        rs.getString(TUSUARIOS_USUARIO));

            }
        } catch (SQLDataException e){
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

}
