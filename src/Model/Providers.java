package Model;

public class Providers {

    private String name;
    private String nit;
    private String email;
    private String phone;
    private String city;
    private String address;
    private int id;

    public Providers(String name) {
        this.name = name;
    }

    public Providers(Integer id, String name, String nit, String email, String phone, String city, String address) {
        this.id = id;
        this.name = name;
        this.nit = nit;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
