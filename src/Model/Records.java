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
    private int productId;
    private int areaId;
    private int userId;

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

    public Records(Date dateOfRecord,Integer quantity, Products product, Integer areaId, Integer userId, Double unitPrice, Double totalPrice){
        this.dateOfRecord = dateOfRecord;
        this.quantity = quantity;
        this.product = product;
        this.productId = product.getCode();
        this.productName = product.getName();
        this.areaId = areaId;
        this.userId = userId;
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
