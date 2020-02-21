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

    public Inventory() {
    }

    public Inventory(String productName, int quantity, Date expirationDate){
        this.productName = productName;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }

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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getProproductId() {
        return proproductId;
    }

    public void setProproductId(int proproductId) {
        this.proproductId = proproductId;
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

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
}
