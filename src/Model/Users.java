package Model;

public class Users {

    private String name;
    private int id;
    private String typeOfUser;
    private String password;

    public Users(){}

    public Users(int id, String name, String password, String typeOfUser){
        this.id = id;
        this.name = name;
        this.password = password;
        this.typeOfUser = typeOfUser;
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
}
