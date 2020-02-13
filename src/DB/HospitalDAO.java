package DB;

import Model.Hospital;
import static DB.DataBase.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface HospitalDAO extends IDBConection {

    default Hospital getHospitalInformation(){
        Hospital hospital = null;

        try{Connection connection = conectToDB();

            String sql = "SELECT * FROM " + THOSPITAL;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                hospital = new Hospital(rs.getString(THOSPITAL_NOMBRE),
                        rs.getString(THOSPITAL_NIT),
                        rs.getString(THOSPITAL_DIRECCION),
                        rs.getString(THOSPITAL_TELEFONO),
                        rs.getString(THOSPITAL_EMAIL),
                        rs.getString(THOSPITAL_CIUDAD));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hospital;
    }
}
