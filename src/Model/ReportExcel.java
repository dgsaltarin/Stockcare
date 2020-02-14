package Model;

import javafx.collections.ObservableList;
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
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

    default void productsReportExcel(String tittle, Date date, ObservableList<Products> observableList){
        String fileName = tittle + formato.format(date) +".xlsx";
        for(int i=0;i<observableList.size();i++){
            XSSFRow fila = sheet.createRow(i);
            XSSFCell celda1 = fila.createCell(0);
            celda1.setCellValue(observableList.get(i).getCode());
            XSSFCell celda2 = fila.createCell(1);
            celda2.setCellValue(observableList.get(i).getName());
            XSSFCell celda3 = fila.createCell(2);
            celda3.setCellValue(observableList.get(i).getClasification());
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        //guardar el archivo
        try {
            FileOutputStream archivo = new FileOutputStream(Report.url + "Listado de productos\\"+fileName);
            book.write(archivo);
            archivo.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar el informe.");
        }

        //abrir el archivo
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(Report.url + "Listado de productos\\"+fileName);
                Desktop.getDesktop().open(myFile);
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al tratar de abrir el archivo.");
            }
        }
    }

    default void reportInventoryExcel(){}

    default void reportRecordsExcel(){}

    default void purchaseOrderReportExcel(){}
}
