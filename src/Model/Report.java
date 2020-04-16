package Model;

import controllers.ReportWindowController;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class Report {

    private String tittle;
    private static Date date;

    public static final String url = System.getenv("USERPROFILE") + "\\Informes\\";
    static final String recordsUrl = url + "Registros\\";
    static final String productsUrl = url + "Listado de productos\\";
    static final String inventoryUrl = url + "Inventario\\";

    public static final void callReportWindow(String typeOfReport, ObservableList observableList) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Report.class.getClassLoader().getResource("Ui/ReportWindow.fxml"));
        Parent root = loader.load();

        ReportWindowController controller = loader.getController();
        controller.setTypeOfReport(typeOfReport);
        controller.setData(observableList);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image("images/application_icon.png"));
        stage.setScene(scene);
        stage.showAndWait();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
}
