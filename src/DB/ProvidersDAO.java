package DB;

import Model.Providers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import static DB.DataBase.*;

public interface ProvidersDAO extends IDBConection  {

    default ArrayList<Providers> providerList(){
        ArrayList<Providers> providersL = new ArrayList<>();

        try{
            Connection connection = conectToDB();
            String sql = "SELECT * FROM " + TPROVEEDORES;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Providers provider = new Providers(
                        rs.getString(TPROVEEDORES_NOMBRE),
                        rs.getString(TPROVEEDORES_NIT),
                        rs.getString(TPROVEEDORES_EMAIL),
                        rs.getString(TPROVEEDORES_TELEFONO),
                        rs.getString(TPROVEEDORES_CIUDAD),
                        rs.getString(TPROVEEDORES_DIRECCIÓN));
                providersL.add(provider);
            }

        } catch (SQLDataException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return providersL;
    }
    default ObservableList<Providers> providerName(){
        ObservableList<Providers> providersL = FXCollections.observableArrayList();

        try{
            Connection connection = conectToDB();
            String sql = "SELECT "+ TPROVEEDORES_NOMBRE +" FROM " + TPROVEEDORES;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Providers provider = new Providers(
                        rs.getString(TPROVEEDORES_NOMBRE));
                providersL.add(provider);
            }

        } catch (SQLDataException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return providersL;
    }
}
