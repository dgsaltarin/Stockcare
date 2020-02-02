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
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Ui/ProductsWindow.fxml"));
        stage.setTitle("Products");
        stage.setScene(new Scene(root, 600, 450));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void recordsWindow(ActionEvent actionEvent) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Ui/RecordsWindow.fxml"));
        stage.setTitle("Products");
        stage.setScene(new Scene(root, 600, 450));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void inventoryWindow(ActionEvent actionEvent) throws IOException{
        stageChanger("Ui/InventoryWindow.fxml", "Inventario");
    }

    private void stageChanger(String stageUrl, String tittle) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(stageUrl));
        stage.setTitle(tittle);
        stage.setScene(new Scene(root,600,450));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
