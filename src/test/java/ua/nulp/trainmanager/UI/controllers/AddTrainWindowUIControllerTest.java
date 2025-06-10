package ua.nulp.trainmanager.UI.controllers;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ua.nulp.trainmanager.DPL.DPL;
import ua.nulp.trainmanager.DPL.database.Database;
import ua.nulp.trainmanager.DPL.train.Train;

import static org.junit.jupiter.api.Assertions.*;

class AddTrainWindowUIControllerTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        Database.DB_NAME = "test.db";
        DPL.trains = new Train[0];

        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/ua/nulp/trainmanager/UI/AddTrainWindow.fxml")
        );
        stage.setScene(new javafx.scene.Scene(loader.load()));
        stage.show();
    }

    @Test
    void test() {
     clickOn("#tfName").write("test");
     clickOn("#btnSubmit");
     assertEquals("test", DPL.trains[0].getName());
    }
}