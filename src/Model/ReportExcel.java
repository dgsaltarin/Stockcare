package Model;

import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface ReportExcel {

    XSSFWorkbook book = new XSSFWorkbook();
    XSSFSheet sheet = book.createSheet();
    SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    CellStyle cellStyle = book.createCellStyle();
    CreationHelper createHelper = book.getCreationHelper();


    /**
     * create a excel report for product list
     * */
    default void productsReportExcel(String tittle, Date date, ObservableList<Products> observableList){
        String fileName = tittle + FORMAT.format(date) +".xlsx";

        for(int i=0;i<observableList.size();i++){
            XSSFRow row = sheet.createRow(i);
            XSSFCell cell1 = row.createCell(0);
            cell1.setCellValue(observableList.get(i).getCode());
            XSSFCell cell2 = row.createCell(1);
            cell2.setCellValue(observableList.get(i).getName());
            XSSFCell cell3 = row.createCell(2);
            cell3.setCellValue(observableList.get(i).getPrice());
            XSSFCell cell4 = row.createCell(3);
            cell4.setCellValue(observableList.get(i).getClasification());
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);

        //saving file
        try {
            FileOutputStream file = new FileOutputStream(Report.productsUrl+fileName);
            book.write(file);
            file.close();
        } catch (Exception e) {
            Alerts.notSelectionAlert("Error al generar informe!");
        }

        //open file
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(Report.productsUrl+fileName);
                Desktop.getDesktop().open(myFile);
            }
            catch (IOException ex) {
                Alerts.notSelectionAlert("Error al abrir archivo!");
            }
        }
    }

    default void reportInventoryExcel(String tittle, Date date, ObservableList<Inventory> observableList){
        String fileName = tittle + FORMAT.format(date) +".xlsx";
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/MM/dd"));

        for(int i=0;i<observableList.size();i++){
            XSSFRow row = sheet.createRow(i);
            XSSFCell cell1 = row.createCell(0);
            cell1.setCellValue(observableList.get(i).getProductName());
            XSSFCell cell2 = row.createCell(1);
            cell2.setCellValue(observableList.get(i).getQuantity());
            XSSFCell cell3 = row.createCell(2);
            cell3.setCellValue(observableList.get(i).getUnitPrice());
            XSSFCell cell4 = row.createCell(3);
            cell4.setCellValue(observableList.get(i).getExpirationDate());
            cell4.setCellStyle(cellStyle);
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);

        //saving file
        try {
            FileOutputStream file = new FileOutputStream(Report.productsUrl+fileName);
            book.write(file);
            file.close();
        } catch (Exception e) {
            Alerts.notSelectionAlert("Error al generar informe!");
        }

        //open file
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(Report.productsUrl+fileName);
                Desktop.getDesktop().open(myFile);
            }
            catch (IOException ex) {
                Alerts.notSelectionAlert("Error al abrir archivo!");
            }
        }
    }

    /**
     * Create a excel report for the records
     * */
    default void reportRecordsExcel(String tittle, Date date, ObservableList<Records> observableList){
        String fileName = tittle + FORMAT.format(date) +".xlsx";
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/MM/dd"));

        for(int i=0;i<observableList.size();i++){
            XSSFRow row = sheet.createRow(i);
            XSSFCell cell1 = row.createCell(0);
            cell1.setCellValue(observableList.get(i).getProductName());
            XSSFCell cell2 = row.createCell(1);
            cell2.setCellValue(observableList.get(i).getQuantity());
            XSSFCell cell3 = row.createCell(2);
            cell3.setCellValue(observableList.get(i).getUnitPrice());
            XSSFCell cell4 = row.createCell(3);
            cell4.setCellValue(observableList.get(i).getTotalPrice());
            XSSFCell cell5 = row.createCell(4);
            cell5.setCellValue(observableList.get(i).getAreaName());
            XSSFCell cell6 = row.createCell(5);
            cell6.setCellValue(observableList.get(i).getDateOfRecord());
            cell6.setCellStyle(cellStyle);
            XSSFCell cell7 = row.createCell(6);
            cell7.setCellValue(observableList.get(i).getUserName());
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);
        sheet.autoSizeColumn(7);

        //saving file
        try {
            FileOutputStream file = new FileOutputStream(Report.recordsUrl+fileName);
            book.write(file);
            file.close();
        } catch (Exception e) {
            Alerts.notSelectionAlert("Error al generar el informe!");
        }

        //Open file
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(Report.recordsUrl+fileName);
                Desktop.getDesktop().open(myFile);
            }
            catch (IOException ex) {
                Alerts.notSelectionAlert("Error al abrir el archivo!");
            }
        }
    }

    default void purchaseOrderReportExcel(){}
}
