package Model;

import DB.UserDAO;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static DB.DataBase.*;

public class Users implements UserDAO {

    private String name;
    private int id;
    private String typeOfUser;
    private String password;
    private String user;

    public static Users currentUser;

    public Users(){}

    public Users(int id, String name,String typeOfUser, String password,String user ){
        this.id = id;
        this.name = name;
        this.password = password;
        this.typeOfUser = typeOfUser;
        this.user = user;
    }

    /**
     * Receive a user and add it to the data base
     * */
    public void addNewUser(Users newUser){
        try {
            Connection connection = conectToDB();

            String sql = " INSERT INTO " + TUSUARIOS + " VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, newUser.getId());
            preparedStatement.setString(2, newUser.getName());
            preparedStatement.setString(3, newUser.getTypeOfUser());
            preparedStatement.setString(4, encryptPassword(newUser.getPassword()));
            preparedStatement.setString(5, newUser.getUser());
            preparedStatement.executeUpdate();

            Alerts.successfullAlert("Usuario agregado de manera exitosa!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * receive a row password from the UI and encrypt it to be save on the data base in a safe way
     * */
    public String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(password.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashText = bigInt.toString(16);
        // Now we need to zero pad it if you actually want the full 32 chars.
        while(hashText.length() < 32 ){
            hashText = "0"+hashText;
        }
        return  hashText;
    }

    @Override
    public String toString(){
        return name;
    }

    /**
     * receive an user's id and then remove it from the data base
     * */
    public void removeUser(int userId){
        try { Connection connection = conectToDB();
            String sql = "DELETE FROM " + TUSUARIOS + " WHERE " + TUSUARIOS_ID + " = " + userId;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

            Alerts.successfullAlert("Usuario eliminado demanera exitosa!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static Users getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Users currentUser) {
        Users.currentUser = currentUser;
    }
}
