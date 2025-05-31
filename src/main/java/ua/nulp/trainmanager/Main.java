package ua.nulp.trainmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ua.nulp.trainmanager.DPL.DPL;
import ua.nulp.trainmanager.DPL.database.DBInit;
import ua.nulp.trainmanager.DPL.database.DBWrite;
import ua.nulp.trainmanager.util.Logger;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try (FileWriter fw = new FileWriter("logs/app.log", false)) {
            fw.write("");
        } catch (IOException e) {
            Logger.error("Помилка при очищені старого логу", "");
        }
        Logger.info("Cтарт додатку");
        DBInit.createTable("");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("UI/MainUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1120, 630);
        stage.setTitle("Train Manager");
        Image image = new Image(getClass().getResourceAsStream("/ua/nulp/trainmanager/UI/icon/mainICO.png"));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            DBWrite.saveWagons(DPL.wagons, "");
            DBWrite.saveTrains(DPL.trains, "");
            Logger.info("Роботу завершено");
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}