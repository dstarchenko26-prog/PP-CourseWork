package ua.nulp.trainmanager.UI.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ua.nulp.trainmanager.Main;
import ua.nulp.trainmanager.util.Logger;

import java.io.IOException;

public class MainUIController {

    @FXML
    protected void wagonsMenuOpen(javafx.event.ActionEvent event) throws IOException {
        Parent newRoot = FXMLLoader.load(Main.class.getResource("UI/WagonsMenuUI.fxml"));
        Scene newScene = new Scene(newRoot, 1120, 630);
        Logger.info("Відкрито меню вагонів");
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
    }

    @FXML
    protected void trainsMenuOpen(javafx.event.ActionEvent event) throws IOException {
        Parent newRoot = FXMLLoader.load(Main.class.getResource("UI/TrainsMenuUI.fxml"));
        Scene newScene = new Scene(newRoot, 1120, 630);
        Logger.info("Відкрито меню поїздів");
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
    }

    @FXML
    protected void infoOpen(javafx.event.ActionEvent event) throws IOException {
        Parent newRoot = FXMLLoader.load(Main.class.getResource("UI/InfoUI.fxml"));
        Scene newScene = new Scene(newRoot, 1120, 700);
        Logger.info("Відкрито інформацію про програму");
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(newScene);
    }

    @FXML
    protected void exitEvent() throws IOException {
        Platform.exit();
    }
}
