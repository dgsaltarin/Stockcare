package Model;

import DB.InventoryDAO;

import java.util.ArrayList;
import java.util.Date;

public class Inventory implements InventoryDAO {

    private int id;
    private int quantity;
    private Date expirationDate;
    private int productoID;
    private Products producto;
    private String productName;

    public Inventory() {
    }

    public Inventory(int id, int quantity, Date expirationDate, int productoID) {
        this.id = id;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
        this.productoID = productoID;
    }

    public Inventory(String productName, int quantity, Date expirationDate){
        this.productName = productName;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getProductoID() {
        return productoID;
    }

    public void setProductoID(int productoID) {
        this.productoID = productoID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
