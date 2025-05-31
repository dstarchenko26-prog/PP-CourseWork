package ua.nulp.trainmanager.UI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.nulp.trainmanager.DPL.wagons.Loc;
import ua.nulp.trainmanager.DPL.DPL;
import ua.nulp.trainmanager.util.Logger;

public class AddLocFormUIController {
    @FXML
    private TextField tfName;

    @FXML
    private TextField tfSpeed;

    @FXML
    private TextField tfWeight;

    @FXML
    private TextField tfTraction;

    @FXML
    private TextField tfConsumption;

    @FXML
    private Button btnSubmit;

    @FXML
    private Label lblError;

    @FXML
    public void initialize() {
        btnSubmit.setOnAction(event -> {
            String name = tfName.getText().trim();
            String sSpeed = tfSpeed.getText().trim();
            String sWeight = tfWeight.getText().trim();
            String sTraction = tfTraction.getText().trim();
            String sConsumption = tfConsumption.getText().trim();

            if (name.isEmpty() || sSpeed.isEmpty() || sWeight.isEmpty() || sTraction.isEmpty() || sConsumption.isEmpty()) {
                lblError.setText("Будь ласка, заповніть усі поля.");
                return;
            }

            int speed;
            int weight;
            int traction;
            int consumption;

            Logger.info("Спроба додати локомотив");

            try {
                speed = Integer.parseInt(sSpeed);
                if (speed < 0) {
                    lblError.setText("Швидкість не може бути від’ємною.");
                    return;
                }
            } catch (NumberFormatException e) {
                lblError.setText("Швидкість має бути числом.");
                return;
            }

            try {
                weight = Integer.parseInt(sWeight);
                if (weight < 0) {
                    lblError.setText("Вага не може бути від’ємною.");
                    return;
                }
            } catch (NumberFormatException e) {
                lblError.setText("Вага має бути числом.");
                return;
            }

            try {
                traction = Integer.parseInt(sTraction);
                if (traction < 0) {
                    lblError.setText("Тяга не може бути від’ємною.");
                    return;
                }
            } catch (NumberFormatException e) {
                lblError.setText("Тяга має бути числом.");
                return;
            }

            try {
                consumption = Integer.parseInt(sConsumption);
                if (consumption < 0) {
                    lblError.setText("Розхід не може бути від’ємний.");
                    return;
                }
            } catch (NumberFormatException e) {
                lblError.setText("Розхід має бути числом.");
                return;
            }

            lblError.setText("");

            DPL.addWagon(new Loc(name, speed, weight, traction, consumption));

            Stage stage = (Stage) btnSubmit.getScene().getWindow();
            stage.close();
        });
    }
}
