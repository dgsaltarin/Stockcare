package Model;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import Model.Report.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;

import javax.swing.*;

public interface ReportPdf {

    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

    default void productsReportPdf(String tittle, Date date, ObservableList<Products> observableList){

        String reportName = tittle + formato.format(date)+".pdf";
        try{
            //empresa datosEMpresa = new empresa();//conecta con los datos de la empresa
            //datosEMpresa.ConseguirEmpresa();// trae los datos de la empresa
            Document document = new Document(PageSize.LETTER);//tamaño del documento
            File file = new File(Report.url+"\\Listado de productos");
            file.mkdirs();
            PdfWriter s = PdfWriter.getInstance(document, new FileOutputStream(Report.url+"Listado de productos\\"+reportName));// ubicación a guardar

            document.open();
            Paragraph encabezado = new Paragraph();
            encabezado.add(tittle);
            encabezado.setAlignment(Element.ALIGN_CENTER);
            document.add(encabezado);
            document.add(new Paragraph("\n\n"));
            //document.add(new Paragraph("EMPRESA: "+datosEMpresa.nombre));
            //document.add(new Paragraph("NIT: "+datosEMpresa.nit));
            //document.add(new Paragraph("TELÉFONO: "+datosEMpresa.telefono));
            //document.add(new Paragraph("DIRECCIÓN: "+datosEMpresa.direccion));
            //document.add(new Paragraph("FECHA: "+formato2.format(fecha)));
            document.add(new Paragraph("\n\n"));

            PdfPTable tabla = new PdfPTable(3);
            tabla.setWidths(new float[] {8, 40,20});
            tabla.addCell("Código");
            tabla.addCell("Nombre del Producto");
            tabla.addCell("Clasificación VEN");

            for(int a=0;a<observableList.size();a++){
                tabla.addCell(String.valueOf(observableList.get(a).getCode()));
                tabla.addCell(observableList.get(a).getName());
                tabla.addCell(observableList.get(a).getClasification());
            }

            document.add(tabla);
            document.close();
        }
        catch(com.itextpdf.text.DocumentException | FileNotFoundException DocumentException){
            JOptionPane.showMessageDialog(null,"No se pudo generar el archivo de la orden.");
        }
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(Report.url+"\\Listado de productos\\"+reportName);
                Desktop.getDesktop().open(myFile);
            }
            catch (IOException ex) {
            }
        }
    }
}