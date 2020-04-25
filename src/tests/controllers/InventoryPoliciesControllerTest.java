package controllers;

import DB.RecordsDAO;
import Model.Inventory;
import Model.InventoryPolicies;
import Model.Records;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class InventoryPoliciesControllerTest implements RecordsDAO {

    @Test
    public void generatePolicies() {
        String typeOfProduct = "medicamento";
        ObservableList<Records> dataTest = FXCollections.observableArrayList(getRowOutComes(typeOfProduct));

        InventoryPolicies inventoryPolicies = new InventoryPolicies();
        ObservableList<InventoryPolicies> policies = inventoryPolicies.generatePolicies(dataTest, typeOfProduct);
        HashMap<String, Double> hashMap = inventoryPolicies.createHashMapProductPriceName(typeOfProduct);

        System.out.println(policies.size());
        for (InventoryPolicies item:policies){
            System.out.println(item.getProductName());
            System.out.println(item.getClassification());
            System.out.println(item.getBehaviorString());
            System.out.println(item.getAverageDemand());
            System.out.println(item.getQuantityToOrder());
            System.out.println(item.getWhenToOrder());
            System.out.println("\n");
        }
    }
}