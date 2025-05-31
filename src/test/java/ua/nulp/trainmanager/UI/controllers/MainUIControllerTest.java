package ua.nulp.trainmanager.UI.controllers;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class MainUIControllerTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {

        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/ua/nulp/trainmanager/UI/MainUI.fxml")
        );
        stage.setScene(new javafx.scene.Scene(loader.load()));
        stage.show();
    }

    @Test
    void testTMOBTN () {
        clickOn("#tmo");
        Label header = lookup("#header").query();
        assertEquals("Управління поїздами", header.getText());
    }

    @Test
    void testWMOBTN () {
        clickOn("#wmo");
        Label header = lookup("#header").query();
        assertEquals("Управління вагонами", header.getText());
    }

    @Test
    void testIOBTN () {
        clickOn("#io");
        Label header = lookup("#header").query();
        assertEquals("Інформація", header.getText());
    }
}