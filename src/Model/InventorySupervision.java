package Model;

import DB.InventoryDAO;
import DB.RecordsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Calendar;
import java.util.Date;

public class InventorySupervision extends InventoryPolicies implements InventoryDAO, RecordsDAO {

    private String productName;
    private double R;
    private int quantity;

    public ObservableList<Inventory> expirationDateSupervision(){
        ObservableList<Inventory> productsInRisk = FXCollections.observableArrayList();

        for (int i=0;i<3;i++){

        }
        ObservableList<Inventory> inventoryList = getAllInventory();

        //future date is a date 30 in the future from the actual date
        Date futureDate;
        Date actualDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(actualDate);
        cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH)+1));
        futureDate = cal.getTime();

        for (Inventory item:inventoryList){
            int days = (int) ((item.getExpirationDate().getTime()-futureDate.getTime())/86400000);
            if (item.getExpirationDate().before(actualDate)||days<=30){
                productsInRisk.add(item);
            }
            System.out.println(days);
        }

        return  productsInRisk;
    }

    public ObservableList<InventorySupervision> inventoryPolicySupervision(){

        ObservableList<InventoryPolicies> policies = getAllInventoryPolicies();
        ObservableList<InventorySupervision> itemOnRick = FXCollections.observableArrayList();
        ObservableList<Inventory> allInventory = getAllInventory();
        ObservableList<String> productNames = FXCollections.observableArrayList();

        for (Inventory item:allInventory){
            productNames.add(item.getProductName());
        }

        for (InventoryPolicies item:policies){
            InventorySupervision inventorySupervisionItem = new InventorySupervision();
            for (Inventory inventoryItem:allInventory) {
                if (!productNames.contains(item.getProductName())){
                    inventorySupervisionItem = new InventorySupervision(item.getProductName(), item.getWhenToOrder(), 0);
                } if (productNames.contains(item.getProductName())||inventoryItem.getQuantity()<item.getWhenToOrder()){
                    inventorySupervisionItem = new InventorySupervision(item.getProductName(), item.getWhenToOrder(), inventoryItem.getQuantity());
                }
            }
            itemOnRick.add(inventorySupervisionItem);
        }
        return itemOnRick;
    }

    private ObservableList<InventoryPolicies> getAllInventoryPolicies(){
        ObservableList<InventoryPolicies> policies = FXCollections.observableArrayList();

        for (int i=0;i<3;i++){
            ObservableList<InventoryPolicies> result;
            String typeOfProduct = "";
            switch (i){
                case 0:
                    typeOfProduct = "medicamento";
                    break;
                case 1:
                    typeOfProduct = "dispositivo médico";
                    break;
                case 2:
                    typeOfProduct = "insumo";
                    break;
            }

            ObservableList<Records> rowData = FXCollections.observableArrayList(getRowOutComes(typeOfProduct));
            result = generatePolicies(rowData,typeOfProduct);
            policies.addAll(result);
           }
        return policies;
    }

    public InventorySupervision(){}

    public InventorySupervision(String productName, double R, int quantity){
        this.productName = productName;
        this.R = R;
        this.quantity = quantity;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getR() {
        return R;
    }

    public void setR(double r) {
        R = r;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
