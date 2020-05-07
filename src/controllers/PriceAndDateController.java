package controllers;

import Model.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PriceAndDateController {

    @FXML
    private TextField priceTextField;
    @FXML
    private DatePicker expirationDatePicker;

    private Double unitPrice;
    private Date expirationDate;

    /**
     * get the entered value for the price and convert it to double
     * */
    public void addPrice() {
        if (!priceTextField.getText().isEmpty()&&expirationDatePicker.getValue()!=null){

            LocalDate initialLocalDate = expirationDatePicker.getValue();
            Instant instant = Instant.from(initialLocalDate.atStartOfDay(ZoneId.systemDefault()));
            Date initialDate = Date.from(instant);

            unitPrice = Double.valueOf(priceTextField.getText());
            expirationDate = initialDate;
        } else {
            Alerts.notSelectionAlert("Debe ingesar el precio del prodicto primero!");
            return;
        }

        Stage stage = (Stage) priceTextField.getScene().getWindow();
        stage.close();
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    /**
     * evaluate the values and just admit the numerical values
     * */
    public void evaluateValue() {
       priceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*")) return;
            priceTextField.setText(newValue.replaceAll("[^\\d]", ""));
        });
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
}
