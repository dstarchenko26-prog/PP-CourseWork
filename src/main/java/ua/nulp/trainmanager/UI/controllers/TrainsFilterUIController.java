package ua.nulp.trainmanager.UI.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ua.nulp.trainmanager.DPL.DPL;
import ua.nulp.trainmanager.Main;
import ua.nulp.trainmanager.util.Logger;

import java.io.IOException;

public class TrainsFilterUIController {
    @FXML
    private TextField tfName;

    @FXML
    private TextField tfMinSpeed;

    @FXML
    private TextField tfMaxSpeed;

    @FXML
    private TextField tfMaxWeight;

    @FXML
    private TextField tfMinTrac;

    @FXML
    private TextField tfMaxCons;

    @FXML
    private TextField tfMinPass;

    @FXML
    private TextField tfMinCargo;

    @FXML
    private Button prevBTN;

    @FXML
    private Button filtBTN;

    @FXML
    public void initialize() {
        Image image1 = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/prevICO.png"));
        ImageView icon1 = new ImageView(image1);
        icon1.setFitWidth(96);
        icon1.setFitHeight(96);
        prevBTN.setGraphic(icon1);
        Image image2 = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/filterICO.png"));
        ImageView icon2 = new ImageView(image2);
        icon2.setFitWidth(96);
        icon2.setFitHeight(96);
        filtBTN.setGraphic(icon2);
        prevBTN.setOnAction(event -> {
            try {
                Parent newRoot = FXMLLoader.load(Main.class.getResource("UI/TrainsMenuUI.fxml"));
                Scene newScene = new Scene(newRoot, 1120, 630);
                Logger.info("Повернення до меню поїздів");
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(newScene);
            } catch (IOException e) {
                Logger.error("Помилка при повернені до меню поїздів з фільтрування поїздів", "");
            }
        });
        filtBTN.setOnAction(event -> {
            String name = tfName.getText().trim();
            String sMinSpeed = tfMinSpeed.getText().trim();
            String sMaxSpeed = tfMaxSpeed.getText().trim();
            String sMaxWeight = tfMaxWeight.getText().trim();
            String sMinTrac = tfMinTrac.getText().trim();
            String sMaxCons = tfMaxCons.getText().trim();
            String sMinPass = tfMinPass.getText().trim();
            String sMinCargo = tfMinCargo.getText().trim();

            int minSpeed;
            int maxSpeed;
            int maxWeight;
            int minTrac;
            int maxCons;
            int minPass;
            int minCargo;

            try {
                minSpeed = Integer.parseInt(sMinSpeed);
                if (minSpeed < 0) {
                    minSpeed = 0;
                }
            } catch (NumberFormatException e) {
                minSpeed = 0;
            }

            try {
                maxSpeed = Integer.parseInt(sMaxSpeed);
                if (maxSpeed < minSpeed) {
                    maxSpeed = 0;
                }
            } catch (NumberFormatException e) {
                maxSpeed = 0;
            }

            try {
                maxWeight = Integer.parseInt(sMaxWeight);
                if (maxWeight < 0) {
                    maxWeight = 0;
                }
            } catch (NumberFormatException e) {
                maxWeight = 0;
            }

            try {
                minTrac = Integer.parseInt(sMinTrac);
                if (minTrac < 0) {
                    minTrac = 0;
                }
            } catch (NumberFormatException e) {
                minTrac = 0;
            }

            try {
                maxCons = Integer.parseInt(sMaxCons);
                if (maxCons < 0) {
                    maxCons = 0;
                }
            } catch (NumberFormatException e) {
                maxCons = 0;
            }

            try {
                minPass = Integer.parseInt(sMinPass);
                if (minPass < 0) {
                    minPass = 0;
                }
            } catch (NumberFormatException e) {
                minPass = 0;
            }

            try {
                minCargo = Integer.parseInt(sMinCargo);
                if (minCargo < 0) {
                    minCargo = 0;
                }
            } catch (NumberFormatException e) {
                minCargo = 0;
            }

            DPL.usParams.FiltTParams(minSpeed, maxSpeed, maxWeight, minTrac, maxCons, minPass, minCargo, name);
            try {
                DPL.FT = true;
                Parent newRoot = FXMLLoader.load(Main.class.getResource("UI/TrainsMenuUI.fxml"));
                Scene newScene = new Scene(newRoot, 1120, 630);
                Logger.info("Нові фільтри встановлено");
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(newScene);
            } catch (IOException e) {
                Logger.error("Помилка при повернені до меню поїздів з фільтрування поїздів", "");
            }
        });
    }
}
