package Model;

import java.util.Date;

public class Records {

    private Products product;
    private Date dateOfRecord;
    private Users user;
    private int quantity;
    private String userName;
    private String areaName;
    private String productName;
    private String providerName;
    private Double unitPrice;
    private Double totalPrice;

    public String getProductName() {
        return productName;
    }

    public  Records(){}

    public  Records(String productName, Integer quantity, String areaName, Date date, String userName, Double unitPrice, Double totalPrice){
        this.productName = productName;
        this.quantity = quantity;
        this.areaName = areaName;
        this.dateOfRecord = date;
        this.userName = userName;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Date getDateOfRecord() {
        return dateOfRecord;
    }

    public void setDateOfRecord(Date dateOfRecord) {
        this.dateOfRecord = dateOfRecord;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
