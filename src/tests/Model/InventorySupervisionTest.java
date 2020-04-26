package Model;

import javafx.collections.ObservableList;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventorySupervisionTest {

    @Test
    public void expirationDateSupervision() {
        InventorySupervision inventorySupervision = new InventorySupervision();
        ObservableList<Inventory> supervision = inventorySupervision.expirationDateSupervision();

        for (Inventory item:supervision){
            System.out.println(item.getProductName());
            System.out.println(item.getExpirationDate());
        }
    }

    @Test
    public void inventoryPolicySupervision() {
        InventorySupervision supervision = new InventorySupervision();
        ObservableList<InventorySupervision> result = supervision.inventoryPolicySupervision();

        for (InventorySupervision item:result){
            System.out.println(item.getProductName());
            System.out.println(item.getR());
            System.out.println(item.getQuantity());
        }

    }
}