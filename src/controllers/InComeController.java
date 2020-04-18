package controllers;

import DB.InComesDAO;
import DB.PurchaseOrderDAO;
import Model.PurchaseOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InComeController implements Initializable, InComesDAO, PurchaseOrderDAO {

    @FXML
    private TableView<PurchaseOrder> purchaseOrdersTableView;
    @FXML
    private TableView<PurchaseOrder> verificationTableView;
    @FXML
    private TableColumn<PurchaseOrder, Integer> orderNumberColumn;
    @FXML
    private TableColumn<PurchaseOrder, String> providerColumn;

    @FXML
    private TableColumn<PurchaseOrder, String> productColumn;
    @FXML
    private TableColumn<PurchaseOrder, Integer> quantityColumn;
    @FXML
    private TableColumn<PurchaseOrder, CheckBox> verificationColumn;
    @FXML
    private TextField filterTextField;

    private ObservableList<PurchaseOrder> correctedPurchaseOrders = FXCollections.observableArrayList();
    private int quantityCorrection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<PurchaseOrder> observableList = FXCollections.observableArrayList(getPurchaseOrderPending());

        //take a order number from the observable list
        for (int i = 0; i < observableList.size(); i++) {
            int orderNumber = observableList.get(i).getOrderNumber();

            //take the order number and compares it with the rest of the observable list
            //when find another order with the same order number removes it
            for (int j = 0; j < observableList.size(); j++) {
                if (observableList.get(j).getOrderNumber() == orderNumber) {
                    observableList.remove(j);
                }
            }
        }

        //display the order numbers
        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        providerColumn.setCellValueFactory(new PropertyValueFactory<>("providerName"));
        purchaseOrdersTableView.setItems(observableList);

        FilteredList<PurchaseOrder> filteredData = new FilteredList<>(observableList, p -> true);
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(PurchaseOrder -> {
                // If filter text is empty, display all products
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare product's name with the filter
                String lowerCaseFilter = newValue.toLowerCase();

                if (PurchaseOrder.getProviderName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });


        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        verificationColumn.setCellValueFactory(new PropertyValueFactory<>("perfectIncome"));

        verificationTableView.getItems().addAll(correctedPurchaseOrders);
        verificationTableView.refresh();
    }


    public void OpenPurchaseOrder() {
        verificationTableView.getItems().clear();

        int orderNumberSelected = purchaseOrdersTableView.getSelectionModel().getSelectedItem().getOrderNumber();

        //get all the purchase order that was selected
        ObservableList<PurchaseOrder> observableList = FXCollections.observableArrayList(getPurchaseOrderByNumber(orderNumberSelected));

        for (int i = 0; i < observableList.size(); i++) {
            CheckBox ch = new CheckBox();
            //set all checkbox selected for default
            ch.fire();

            //add the listener event and the behavior for the uncheck
            int finalI = i;
            ch.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == false) {
                    try {
                        openCorrectionWindow(finalI);
                        //purchaseOrdersTableView.edit(finalI, quantityColumn);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            observableList.get(i).setPerfectIncome(ch);
        }

        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        verificationColumn.setCellValueFactory(new PropertyValueFactory<>("perfectIncome"));
        verificationTableView.setItems(observableList);
    }


    public void generateInCome() {
    }

    private void openCorrectionWindow(int index) throws IOException {
        ObservableList<PurchaseOrder> sendingItem = verificationTableView.getItems();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("Ui/InComeCorrection.fxml"));
        Parent view = loader.load();

        Scene scene = new Scene(view);

        InComeCorrectionController controller = loader.getController();

        //set the init data
        controller.initData(index, correctedPurchaseOrders, sendingItem, quantityCorrection);



        Stage window = new Stage();
        Stage thisWindow = (Stage) verificationTableView.getScene().getWindow();
        window.initOwner(thisWindow);
        window.getIcons().add(new Image("images/application_icon.png"));
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.showAndWait();
        window.setOnCloseRequest((WindowEvent event) -> {
            System.out.println(controller.getQuantity());
        });

    }
}
