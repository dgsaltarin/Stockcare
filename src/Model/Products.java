package Model;

import DB.ProductsDAO;

import java.util.ArrayList;

public class Products implements ProductsDAO {

    private int code;
    private String name;
    private double price;
    private char clasification;

    public Products(){}

    public Products(int code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public void setPrice(double price) {
        this.price = price;
    }


    public char getClasification() {
        return clasification;
    }

    public void setClasification(char clasification) {
        this.clasification = clasification;
    }

    public void showProducts(String tipo){
        ArrayList<Products> productos = productsList(tipo);
        productos.forEach(i -> System.out.println(i.getCode()+" "+i.getName()));
    }
}
