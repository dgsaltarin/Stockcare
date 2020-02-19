package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InterfazPrincipalController implements Initializable {

    @FXML private MenuBar menuBar;

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

    public void outComeWindow(ActionEvent actionEvent) throws IOException {
        stageChanger("Ui/OutComesWindow.fxml","Salida de Inventario");
    }

    private void stageChanger(String stageUrl, String tittle) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(stageUrl));
        stage.setTitle(tittle);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        menuBar.setMinWidth(screenSize.width);
    }
}
