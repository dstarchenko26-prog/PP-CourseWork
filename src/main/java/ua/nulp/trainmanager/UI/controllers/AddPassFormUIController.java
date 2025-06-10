package ua.nulp.trainmanager.UI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.nulp.trainmanager.DPL.DPL;
import ua.nulp.trainmanager.util.Logger;

public class AddPassFormUIController {
    @FXML
    private TextField tfName;

    @FXML
    private TextField tfSpeed;

    @FXML
    private TextField tfWeight;

    @FXML
    private TextField tfCapacity;

    @FXML
    private TextField tfComfort;

    @FXML
    private TextField tfLuggage;

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
            String sCapacity = tfCapacity.getText().trim();
            String sComfort = tfComfort.getText().trim();
            String sLuggage = tfLuggage.getText().trim();

            if (name.isEmpty() || sSpeed.isEmpty() || sWeight.isEmpty() || sCapacity.isEmpty() || sComfort.isEmpty() || sLuggage.isEmpty()) {
                lblError.setText("Будь ласка, заповніть усі поля.");
                return;
            }

            int speed;
            int weight;
            int capacity;
            int comfort;
            int luggage;

            Logger.info("Спроба додати пасажирський вагон");

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
                capacity = Integer.parseInt(sCapacity);
                if (capacity < 0) {
                    lblError.setText("Ємність не може бути від’ємною.");
                    return;
                }
            } catch (NumberFormatException e) {
                lblError.setText("Ємність має бути числом.");
                return;
            }

            try {
                comfort = Integer.parseInt(sComfort);
                if (comfort < 0) {
                    lblError.setText("Комфорт не може бути від’ємним.");
                    return;
                }
            } catch (NumberFormatException e) {
                lblError.setText("Комфорт має бути числом.");
                return;
            }

            try {
                luggage = Integer.parseInt(sLuggage);
                if (luggage < 0) {
                    lblError.setText("К-сть багажу не може бути від’ємною.");
                    return;
                }
            } catch (NumberFormatException e) {
                lblError.setText("К-сть багажу має бути числом.");
                return;
            }

            lblError.setText("");

            DPL.addWagon(name, speed, weight, capacity, comfort, luggage);

            Stage stage = (Stage) btnSubmit.getScene().getWindow();
            stage.close();
        });
    }
}
