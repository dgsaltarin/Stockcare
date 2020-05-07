package controllers;

import Model.Inventory;
import Model.InventoryPolicies;
import Model.InventorySupervision;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class PrincipalInterfaceController implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML
    private TableView<Inventory> inventoryTableView;
    @FXML
    private TableView<InventorySupervision> inventoryPoliciesTableView;
    @FXML
    private TableColumn<Inventory, String> productInventoryColumn;
    @FXML
    private TableColumn<Inventory, Date> expirationDateColumn;
    @FXML
    private TableColumn<InventoryPolicies, String> productPolicyColumn;
    @FXML
    private TableColumn<InventoryPolicies, Double> RColumn;
    @FXML
    private TableColumn<InventoryPolicies, Integer> quantityColumn;

    /**
     * call a stage and shows it
     * */
    private void stageChanger(String stageUrl, String tittle) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(stageUrl));
        stage.setTitle(tittle);
        stage.getIcons().add(new Image("images/application_icon.png"));
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

    /**
     * handle all the methods and data related with the inventory supervision
     * */
    public void inventorySupervision() {
        InventorySupervision inventorySupervision = new InventorySupervision();
        ObservableList<Inventory> inventoryOnRisk = inventorySupervision.expirationDateSupervision();
        ObservableList<InventorySupervision> policiesOnRisk = inventorySupervision.inventoryPolicySupervision();

        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        expirationDateColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        inventoryTableView.setItems(inventoryOnRisk);

        productPolicyColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        RColumn.setCellValueFactory(new PropertyValueFactory<>("R"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        inventoryPoliciesTableView.setItems(policiesOnRisk);
    }

    public void ProductsWindow() throws IOException {
        stageChanger("Ui/ProductsWindow.fxml", "Productos");
    }

    public void recordsWindow() throws IOException {
        stageChanger("Ui/RecordsWindow.fxml", "Registros");
    }

    public void inventoryWindow() throws IOException {
        stageChanger("Ui/InventoryWindow.fxml", "Inventario");
    }

    public void purchaseOrderWindow() throws IOException {
        stageChanger("Ui/PurchaseOrderWindow.fxml", "Orden de Compra");
    }

    public void outComeWindow() throws IOException {
        stageChanger("Ui/OutComesWindow.fxml", "Salida de Inventario");
    }

    public void behaviorWindow() throws IOException {
        stageChanger("Ui/BehaviorAnalyzeWindow.fxml", "Análisis de Comportamiento");
    }

    public void InComesWindow() throws IOException {
        stageChanger("Ui/InComesWindow.fxml", "Entradas a Inventario");
    }

    public void addProductWindow() throws IOException {
        stageChanger("Ui/AddProductWindow.fxml", "Agregar Producto");
    }

    public void addAreaWindow() throws IOException {
        stageChanger("Ui/AddAreaWindow.fxml", "Agregar Área");
    }

    public void addProviderWindow() throws IOException {
        stageChanger("Ui/AddProviderWindow.fxml", "Agregar Proveedor");
    }

    public void ABCAnalyzeWindow() throws IOException {
        stageChanger("Ui/AnalyzeABCWindow.fxml", "Análisis ABC");
    }

    public void AbcVenWindow() throws IOException {
        stageChanger("Ui/AbcVenAnalyzeWindow.fxml", "Análisis ABC-VEN");
    }

    public void addUserWindow() throws IOException {
        stageChanger("ui/AddUserWindow.fxml", "Agregar Usuario");
    }

    public void removeUserWindow() throws IOException {
        stageChanger("Ui/RemoveUserWindow.fxml", "Eliminar Usuario");
    }

    public void InventoryPoliciesWindow() throws IOException {
        stageChanger("Ui/InventoryPoliciesWindow.fxml", "Políticas de Inventario");
    }
}
