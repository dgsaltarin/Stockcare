package controllers;

import DB.AreasDAO;
import Model.Alerts;
import Model.Areas;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddAreaController implements AreasDAO {

    @FXML private TextField areaNameTextField;

    public void addArea() {

        if(!areaNameTextField.getText().isEmpty()){
            String areaName = areaNameTextField.getText();
            Areas area = new Areas(areaName);
            addNewArea(area);
        }
        else
            Alerts.notSelectionAlert("Por favor Ingrese el nombre de la nueva Área");
    }
}
