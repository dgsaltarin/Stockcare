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
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            return connection;
        }
    }
}
