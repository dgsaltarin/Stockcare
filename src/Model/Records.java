package Model;

import java.util.Date;

public class Records {

    private Products product;
    private Date dateOfRecord;
    private int quantity;
    private String userName;
    private String areaName;
    private String productName;
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

    public Date getDateOfRecord() {
        return dateOfRecord;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public int getProductId() {
        return productId;
    }

    public int getAreaId() {
        return areaId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getAreaName() {
        return areaName;
    }
}
