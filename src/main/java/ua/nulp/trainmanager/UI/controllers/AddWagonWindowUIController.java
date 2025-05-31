package ua.nulp.trainmanager.UI.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ua.nulp.trainmanager.Main;
import ua.nulp.trainmanager.util.Logger;

import java.io.IOException;


public class AddWagonWindowUIController {
    @FXML
    private Button locBTN;
    @FXML
    private Button passBTN;
    @FXML
    private Button cargoBTN;

    @FXML
    public void initialize() {
        Image image1 = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/locICO.png"));
        ImageView icon1 = new ImageView(image1);
        icon1.setFitWidth(128);
        icon1.setFitHeight(128);
        locBTN.setGraphic(icon1);
        Image image2 = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/cargoICO.png"));
        ImageView icon2 = new ImageView(image2);
        icon2.setFitWidth(128);
        icon2.setFitHeight(128);
        cargoBTN.setGraphic(icon2);
        Image image3 = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/passICO.png"));
        ImageView icon3 = new ImageView(image3);
        icon3.setFitWidth(128);
        icon3.setFitHeight(128);
        passBTN.setGraphic(icon3);
        locBTN.setOnAction(event -> {
            try {
                Parent newRoot = FXMLLoader.load(Main.class.getResource("UI/AddLocForm.fxml"));
                Scene newScene = new Scene(newRoot, 1120, 630);

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(newScene);
            } catch (IOException e) {
                Logger.error("Помилка при відкритті форми додавання локомотива", "");
            }
        });
        cargoBTN.setOnAction(event -> {
            try {
                Parent newRoot = FXMLLoader.load(Main.class.getResource("UI/AddCargoForm.fxml"));
                Scene newScene = new Scene(newRoot, 1120, 630);

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(newScene);
            } catch (IOException e) {
                Logger.error("Помилка при відкритті форми додавання вантажного вагона", "");
            }
        });
        passBTN.setOnAction(event -> {
            try {
                Parent newRoot = FXMLLoader.load(Main.class.getResource("UI/AddPassForm.fxml"));
                Scene newScene = new Scene(newRoot, 1120, 630);

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(newScene);
            } catch (IOException e) {
                Logger.error("Помилка при відкритті форми додавання грузового вагона", "");
            }
        });
    }
}
