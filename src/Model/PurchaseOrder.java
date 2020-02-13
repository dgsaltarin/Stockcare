package Model;

public class PurchaseOrder {

    private int orderNumber;
    private int quantity;
    private int productCode;
    private String productName;
    private String providerName;

    public PurchaseOrder(int orderNumber, int productCode, String productName, String providerName) {
        this.orderNumber = orderNumber;
        this.productCode = productCode;
        this.productName = productName;
        this.providerName = providerName;
    }

    public PurchaseOrder(int orderNumber, int quantity, int productCode, String productName, String providerName) {
        this.orderNumber = orderNumber;
        this.quantity = quantity;
        this.productCode = productCode;
        this.productName = productName;
        this.providerName = providerName;
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
}
