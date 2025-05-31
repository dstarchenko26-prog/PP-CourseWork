package ua.nulp.trainmanager.UI.controllers;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddWagonWindowControllerTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/ua/nulp/trainmanager/UI/AddWagonWindow.fxml")
        );
        stage.setScene(new javafx.scene.Scene(loader.load()));
        stage.show();
    }

    @Test
    void testLocBTN () {
        clickOn("#locBTN");

        Label header = lookup("#header").query();
        assertEquals("Новий локомотив", header.getText());
    }

    @Test
    void testCargoBTN () {
        clickOn("#cargoBTN");

        Label header = lookup("#header").query();
        assertEquals("Новий вантажний вагон", header.getText());
    }

    @Test
    void testPassBTN () {
        clickOn("#passBTN");

        Label header = lookup("#header").query();
        assertEquals("Новий пасажирський вагон", header.getText());
    }
}