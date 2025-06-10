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

public class InfoUIController {
    @FXML
    private Button prevBTN;

    @FXML
    public void initialize() {
        Image image1 = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/prevICO.png"));
        ImageView icon1 = new ImageView(image1);
        icon1.setFitWidth(64);
        icon1.setFitHeight(64);
        prevBTN.setGraphic(icon1);
        prevBTN.setOnAction(event -> {
            try {
                Parent newRoot = FXMLLoader.load(Main.class.getResource("UI/MainUI.fxml"));
                Scene newScene = new Scene(newRoot, 1120, 630);
                Logger.info("Повернення до головного меню");
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(newScene);
            } catch (IOException e) {
                Logger.error("Помилка при повернені до головного меню з панелі інформації", "");
            }
        });
    }
}
