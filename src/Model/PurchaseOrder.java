package Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.control.CheckBox;

import java.awt.*;
import java.util.Date;

public class PurchaseOrder {

    private int orderNumber;
    private int quantity;
    private int productCode;
    private String productName;
    private String providerName;
    private int providerCode;
    private boolean orderState;
    private CheckBox perfectIncome;
    private Double inComePrice;
    private Date expirationDate;

    public PurchaseOrder(){}

    public PurchaseOrder(int orderNumber, int productCode, String productName, int providerCode) {
        this.orderNumber = orderNumber;
        this.productCode = productCode;
        this.productName = productName;
        this.providerCode = providerCode;
    }

    public PurchaseOrder(int orderNumber, int quantity, Products product, Providers provider, boolean orderState) {
        this.orderNumber = orderNumber;
        this.quantity = quantity;
        this.productCode = product.getCode();
        this.productName = product.getName();
        this.providerCode = provider.getId();
        this.providerName = provider.getName();
        this.orderState = orderState;
    }

    public PurchaseOrder(int orderNumber, int quantity, Products product, Providers provider, boolean orderState, Double inComePrice, Date expirationDate) {
        this.orderNumber = orderNumber;
        this.quantity = quantity;
        this.productCode = product.getCode();
        this.productName = product.getName();
        this.providerCode = provider.getId();
        this.providerName = provider.getName();
        this.orderState = orderState;
        this.inComePrice = inComePrice;
        this.expirationDate = expirationDate;
    }

    public PurchaseOrder(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
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

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public int getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(int providerCode) {
        this.providerCode = providerCode;
    }

    public boolean isOrderState() {
        return orderState;
    }

    public void setOrderState(boolean orderState) {
        this.orderState = orderState;
    }

    public CheckBox getPerfectIncome() {
        return perfectIncome;
    }

    public void setPerfectIncome(CheckBox perfectIncome) {
        this.perfectIncome = perfectIncome;
    }

    public Double getInComePrice() {
        return inComePrice;
    }

    public void setInComePrice(Double inComePrice) {
        this.inComePrice = inComePrice;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
