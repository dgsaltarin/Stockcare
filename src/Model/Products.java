package Model;

import DB.ProductsDAO;

public class Products implements ProductsDAO {

    private int code;
    private String name;
    private double price;
    private String classification;
    private String manufacturerName;

    public Products(int code, String name, double price, String classification, String manufacturerName) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.classification = classification;
        this.manufacturerName = manufacturerName;
    }

    public Products(){}

    public Products(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public Products(int code, String name, double price, String clasification) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.classification = clasification;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public String getClasification() {
        return classification;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
}

