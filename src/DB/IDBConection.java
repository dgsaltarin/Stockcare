package DB;

import Model.Alerts;

import java.sql.Connection;
import java.sql.DriverManager;
import static DB.DataBase.*;

public interface IDBConection {

    default Connection conectToDB(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL + DB, USER, PASSWORD);
        } catch (Exception e){
            Alerts.notSelectionAlert("Error al tratar de conectar a la base de datos!!");
            e.printStackTrace();
        }
        finally {
            return connection;
        }
    }
}
