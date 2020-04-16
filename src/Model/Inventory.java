package Model;

import DB.InventoryDAO;

import java.util.Date;

public class Inventory implements InventoryDAO {

    private int id;
    private int quantity;
    private Date expirationDate;
    private int proproductId;
    private String productName;
    private Double unitPrice;
    private Products product;

    public Inventory(Products product, int quantity, Double unitPrice, Date expirationDate){
        this.product = product;
        this.productName = product.getName();
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.proproductId = product.getCode();
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

    public Date getExpirationDate() {
        return expirationDate;
    }

    public int getProproductId() {
        return proproductId;
    }

    public String getProductName() {
        return productName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
