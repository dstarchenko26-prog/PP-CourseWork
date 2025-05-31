package ua.nulp.trainmanager.UI.controllers;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class InfoUIControllerTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {

        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/ua/nulp/trainmanager/UI/InfoUI.fxml")
        );
        stage.setScene(new javafx.scene.Scene(loader.load()));
        stage.show();
    }

    @Test
    void testTMOBTN () {
        clickOn("#prevBTN");
        Label header = lookup("#header").query();
        assertEquals("Головне меню", header.getText());
    }
}