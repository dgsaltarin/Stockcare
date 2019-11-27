package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import static DB.DataBase.*;

public interface IDBConection {

    default Connection conectToDB(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL + DB, USER, PASSWORD);
            if(connection != null){
                System.out.println("se establecio la conexion");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            return connection;
        }
    }
}
