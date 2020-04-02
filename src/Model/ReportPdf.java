package Model;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import DB.HospitalDAO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;

import javax.swing.*;

public interface ReportPdf extends HospitalDAO {

    SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    DecimalFormat moneyFormat = new DecimalFormat("$###,###,###.##");

    /**
     * Generate a PDF's report for the product list
     * @param tittle title for the pdf
     * @param date date when the report is generated
     * @param observableList list of products to be reported
     * */
    default void productsReportPdf(String tittle, Date date, ObservableList<Products> observableList){

        String reportName = tittle + FORMAT.format(date)+".pdf";
        try{
            Document document = new Document(PageSize.LETTER);//tamaño del documento
            File file = new File(Report.productsUrl);

            //save the document
            PdfWriter save = PdfWriter.getInstance(document, new FileOutputStream(Report.productsUrl+reportName));
            //create directory in case it doesn't exist
            file.mkdirs();
            document.open();
            Paragraph header = new Paragraph();
            header.add(tittle);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("EMPRESA: "+ getHospitalInformation().getName()));
            document.add(new Paragraph("NIT: "+ getHospitalInformation().getNIT()));
            document.add(new Paragraph("TELÉFONO: "+ getHospitalInformation().getPhoneNumber()));
            document.add(new Paragraph("DIRECCIÓN: "+ getHospitalInformation().getAddress()));
            document.add(new Paragraph("FECHA: "+ FORMAT.format(date)));
            document.add(new Paragraph("\n\n"));

            PdfPTable table = new PdfPTable(4);
            table.setWidths(new float[] {8, 40,15,20});
            table.addCell("Código");
            table.addCell("Nombre del Producto");
            table.addCell("Precio");
            table.addCell("Clasificación VEN");

            for(int a=0;a<observableList.size();a++){
                table.addCell(String.valueOf(observableList.get(a).getCode()));
                table.addCell(observableList.get(a).getName());
                table.addCell(moneyFormat.format(observableList.get(a).getPrice()));
                table.addCell(observableList.get(a).getClasification());
            }

            document.add(table);
            document.close();
        }
        catch(com.itextpdf.text.DocumentException | FileNotFoundException DocumentException) {
            Alerts.notSelectionAlert("No se pudo generar el archivo de la orden.");
            DocumentException.printStackTrace();
        }
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(Report.productsUrl+reportName);
                Desktop.getDesktop().open(myFile);
            }
            catch (IOException ex) {
            }
        }
    }

    default void reportInventoryPDF(String tittle, Date date, ObservableList<Inventory> observableList){
        String reportName = tittle + FORMAT.format(date)+".pdf";
        try{
            Document document = new Document(PageSize.LETTER);
            PdfWriter save = PdfWriter.getInstance(document, new FileOutputStream(Report.inventoryUrl+reportName));
            File file = new File(Report.inventoryUrl);
            file.mkdirs();
            document.open();
            Paragraph header = new Paragraph();
            header.add(tittle);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("EMPRESA: "+ getHospitalInformation().getName()));
            document.add(new Paragraph("NIT: "+ getHospitalInformation().getNIT()));
            document.add(new Paragraph("TELÉFONO: "+ getHospitalInformation().getPhoneNumber()));
            document.add(new Paragraph("DIRECCIÓN: "+ getHospitalInformation().getAddress()));
            document.add(new Paragraph("FECHA: "+ FORMAT.format(date)));
            document.add(new Paragraph("\n\n"));

            PdfPTable table = new PdfPTable(4);
            table.setWidths(new float[] {40, 10,10, 20});
            table.addCell("Producto");
            table.addCell("Cantidad");
            table.addCell("Precio U.");
            table.addCell("Fecha de Venc.");

            for(int a=0;a<observableList.size();a++){
                table.addCell(observableList.get(a).getProductName());
                table.addCell(String.valueOf(observableList.get(a).getQuantity()));
                table.addCell(moneyFormat.format(observableList.get(a).getUnitPrice()));
                table.addCell(String.valueOf(observableList.get(a).getExpirationDate()));
            }

            document.add(table);
            document.close();
        }
        catch(com.itextpdf.text.DocumentException | FileNotFoundException DocumentException){
            JOptionPane.showMessageDialog(null,"No se pudo generar el archivo de la orden.");
        }
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(Report.inventoryUrl+reportName);
                Desktop.getDesktop().open(myFile);
            }
            catch (IOException ex) {
            }
        }
    }


    /**
     * Create a pdf report for the records
     * */
    default void reportRecordsPDF(String tittle, Date date, ObservableList<Records> observableList){
        String reportName = tittle + FORMAT.format(date)+".pdf";
        try{
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter save = PdfWriter.getInstance(document, new FileOutputStream(Report.recordsUrl+reportName));
            File file = new File(Report.recordsUrl);
            file.mkdirs();
            document.open();
            Paragraph header = new Paragraph();
            header.add(tittle);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("EMPRESA: "+ getHospitalInformation().getName()));
            document.add(new Paragraph("NIT: "+ getHospitalInformation().getNIT()));
            document.add(new Paragraph("TELÉFONO: "+ getHospitalInformation().getPhoneNumber()));
            document.add(new Paragraph("DIRECCIÓN: "+ getHospitalInformation().getAddress()));
            document.add(new Paragraph("FECHA: "+ FORMAT.format(date)));
            document.add(new Paragraph("\n\n"));

            PdfPTable table = new PdfPTable(7);

            table.setWidths(new float[] {45, 12, 12, 15, 20,20, 28});
            table.addCell("Producto");
            table.addCell("Cantidad");
            table.addCell("Precio U.");
            table.addCell("Total");
            table.addCell("Área");
            table.addCell("Fecha");
            table.addCell("Usuario");

            for(int a=0;a<observableList.size();a++){
                table.addCell(String.valueOf(observableList.get(a).getProductName()));
                table.addCell(String.valueOf(observableList.get(a).getQuantity()));
                table.addCell(moneyFormat.format(observableList.get(a).getUnitPrice()));
                table.addCell(moneyFormat.format(observableList.get(a).getTotalPrice()));
                table.addCell(String.valueOf(observableList.get(a).getAreaName()));
                table.addCell(String.valueOf(observableList.get(a).getDateOfRecord()));
                table.addCell(String.valueOf(observableList.get(a).getUserName()));
            }

            table.setTotalWidth(PageSize.A4.rotate().getWidth());
            table.setLockedWidth(true);

            document.add(table);
            document.close();
        }

        catch(com.itextpdf.text.DocumentException | FileNotFoundException DocumentException){
            JOptionPane.showMessageDialog(null,"No se pudo generar el archivo de la orden.");
        }

        //open file
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(Report.recordsUrl+reportName);
                Desktop.getDesktop().open(myFile);
            }
            catch (IOException ex) {
            }
        }
    }

    default void purchaseOrderReportPDF(){}

    default void outComesReportPDF(){}
}
