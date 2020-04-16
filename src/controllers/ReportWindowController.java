package controllers;

import Model.ReportExcel;
import Model.ReportPdf;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Date;

public class ReportWindowController implements ReportExcel, ReportPdf {

    private Date date = new Date();
    @FXML private Label label;
    private String typeOfReport;
    private ObservableList dataList;

    public void generatePDFReport(ActionEvent actionEvent) {
        switch (typeOfReport){
            case "products":
                productsReportPdf("Listado de Productos", date, dataList);
                break;
            case "inventory":
                reportInventoryPDF("Inventario", date, dataList);
                break;
            case "records":
                reportRecordsPDF("Listado de Registros", date, dataList);
                break;
             case "purchaseOrder":
                purchaseOrderReportPDF("Orden de Compra", date, dataList);

        }
        closeWindow();
    }

    public void generateExcelReport(ActionEvent actionEvent) {
        switch (typeOfReport){
            case "products":
                productsReportExcel("Listado de Productos", date, dataList);
                break;
            case "inventory":
                reportInventoryExcel("Inventario", date, dataList);
                break;
            case "records":
                reportRecordsExcel("Listado de Registros", date, dataList);
                break;
        }
        closeWindow();
    }

    private void closeWindow(){
        if (label==null){
            return;
        }else {
            Stage thisStage = (Stage) label.getScene().getWindow();
            thisStage.close();
        }
    }

    public void setTypeOfReport(String typeOfReport){
        this.typeOfReport = typeOfReport;
    }

    public void setData(ObservableList observableList){
        this.dataList = observableList;
    }
}
