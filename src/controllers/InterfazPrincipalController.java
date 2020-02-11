package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InterfazPrincipalController {

    public void ProductsWindow(ActionEvent actionEvent) throws IOException {
        stageChanger("Ui/ProductsWindow.fxml", "Productos");
    }

    public void recordsWindow(ActionEvent actionEvent) throws IOException{
        stageChanger("Ui/RecordsWindow.fxml", "Registros");
    }

    public void inventoryWindow(ActionEvent actionEvent) throws IOException{
        stageChanger("Ui/InventoryWindow.fxml", "Inventario");
    }

    public void purchaseOrderWindow(ActionEvent actionEvent) throws  IOException{
        stageChanger("Ui/PurchaseOrderWindow.fxml", "Orden de Compra");
    }

    private void stageChanger(String stageUrl, String tittle) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(stageUrl));
        stage.setTitle(tittle);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
