package DB;

import Model.Alerts;
import Model.Providers;
import java.sql.*;
import java.util.ArrayList;
import static DB.DataBase.*;

public interface ProvidersDAO extends IDBConection  {

    /***
     * @desciption get the provider's name
     */
    default ArrayList<String> providerName(){
        ArrayList<String> providersL = new ArrayList<>();

        try{
            Connection connection = conectToDB();
            String sql = "SELECT "+ TPROVEEDORES_NOMBRE +" FROM " + TPROVEEDORES;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Providers provider = new Providers(
                        rs.getString(TPROVEEDORES_NOMBRE));
                providersL.add(provider.getName());
            }

        } catch (SQLDataException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return providersL;
    }

    /**
     * get a provider id according to his name
     * */
    default Integer getProvidersCodeByName(String name){
        int providersCode = 0;

        try{
            Connection connection = conectToDB();
            String sql = "SELECT * FROM " + TPROVEEDORES + " WHERE " + TPROVEEDORES_NOMBRE + " = '" +name
                    + "' LIMIT 1";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Providers provider = new Providers(
                        rs.getInt(TPROVEEDORES_ID),
                        rs.getString(TPROVEEDORES_NOMBRE),
                        rs.getString(TPROVEEDORES_NIT),
                        rs.getString(TPROVEEDORES_EMAIL),
                        rs.getString(TPROVEEDORES_TELEFONO),
                        rs.getString(TPROVEEDORES_CIUDAD),
                        rs.getString(TPROVEEDORES_DIRECCIÓN));
                providersCode = provider.getId();
            }

        } catch (SQLDataException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return providersCode;
    }

    /**
     * get a provider's information according to an id
     * */
    default Providers getProviderById(int id){
        Providers provider = null;

        try{
            Connection connection = conectToDB();
            String sql = "SELECT * FROM " + TPROVEEDORES + " WHERE " + TPROVEEDORES_ID + " = '" +id
                    + "' LIMIT 1";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                provider = new Providers(
                        rs.getInt(TPROVEEDORES_ID),
                        rs.getString(TPROVEEDORES_NOMBRE),
                        rs.getString(TPROVEEDORES_NIT),
                        rs.getString(TPROVEEDORES_EMAIL),
                        rs.getString(TPROVEEDORES_TELEFONO),
                        rs.getString(TPROVEEDORES_CIUDAD),
                        rs.getString(TPROVEEDORES_DIRECCIÓN));
            }

        } catch (SQLDataException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return  provider;
    }

    /**
     * set a new provider inside the data base
     * */
    default void addNewProvider(Providers provider){

        try{Connection connection = conectToDB();

            String sql = "INSER INTO " + TPROVEEDORES + " VALUS (?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setNull(1,Types.NULL);
            preparedStatement.setString(2, provider.getNit());
            preparedStatement.setString(3, provider.getName());
            preparedStatement.setString(4, provider.getAddress());
            preparedStatement.setString(5, provider.getPhone());
            preparedStatement.setString(6, provider.getEmail());
            preparedStatement.setString(7, provider.getCity());

            preparedStatement.executeUpdate();

            Alerts.successfullAlert("Proveedor agregado de manera exitosa!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
