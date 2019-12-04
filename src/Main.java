import Model.Inventario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

import static javafx.application.Application.launch;

public class Main extends Application {

    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        inventario.printInventario();
        launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Ui/InterfazPrincipal.fxml"));
        primaryStage.setTitle("StockCare");
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
