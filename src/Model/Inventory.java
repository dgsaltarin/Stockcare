package Model;

import DB.InventoryDAO;

import java.util.Date;

public class Inventory implements InventoryDAO {

    private int id;
    private int quantity;
    private Date expirationDate;
    private int productoID;
    private String productName;
    private Double unitPrice;

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

    public Inventory(Products product, int quantity,Double unitPrice, Date expirationDate){
        this.productName = product.getName();
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.productoID = product.getCode();
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

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
