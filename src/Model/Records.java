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

    public String getProductName() {
        return productName;
    }

    public  Records(){}

    public  Records(String productName,Integer quantity,String areaName,Date date,String userName){
        this.productName = productName;
        this.quantity = quantity;
        this.areaName = areaName;
        this.dateOfRecord = date;
        this.userName = userName;
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
}
