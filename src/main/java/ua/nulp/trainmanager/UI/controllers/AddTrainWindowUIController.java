package ua.nulp.trainmanager.UI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.nulp.trainmanager.DPL.DPL;
import ua.nulp.trainmanager.DPL.train.Train;
import ua.nulp.trainmanager.util.Logger;

public class AddTrainWindowUIController {
    @FXML
    private TextField tfName;

    @FXML
    private Button btnSubmit;

    @FXML
    public void initialize() {
        btnSubmit.setOnAction(event -> {
            String name = tfName.getText().trim();

            Logger.info("Спроба додати локомотив");

            DPL.addTrain(new Train(name));

            Stage stage = (Stage) btnSubmit.getScene().getWindow();
            stage.close();
        });
    }
}
