package ua.nulp.trainmanager.UI.controllers;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ua.nulp.trainmanager.DPL.DPL;

import static org.junit.jupiter.api.Assertions.*;

class TrainsFilterUIControllerTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/ua/nulp/trainmanager/UI/TrainsFilterUI.fxml")
        );
        stage.setScene(new javafx.scene.Scene(loader.load()));
        stage.show();
    }

    @Test
    void testPrevBTN() {
        clickOn("#prevBTN");
        Label header = lookup("#header").query();
        assertEquals("Управління поїздами", header.getText());
    }

    @Test
    void test1FiltBTN() {
        clickOn("#tfMinSpeed").write("1");
        clickOn("#tfMaxSpeed").write("1");
        clickOn("#tfMaxWeight").write("1");
        clickOn("#tfMinTrac").write("1");
        clickOn("#tfMaxCons").write("1");
        clickOn("#tfMinPass").write("1");
        clickOn("#tfMinCargo").write("1");
        clickOn("#filtBTN");
        assertTrue(DPL.FT);
    }

    @Test void test2FiltBTN() {
        clickOn("#tfMinSpeed").write("-1");
        clickOn("#tfMaxSpeed").write("-1");
        clickOn("#tfMaxWeight").write("-1");
        clickOn("#tfMinTrac").write("-1");
        clickOn("#tfMaxCons").write("-1");
        clickOn("#tfMinPass").write("-1");
        clickOn("#tfMinCargo").write("-1");
        clickOn("#filtBTN");
        assertTrue(DPL.FT);
    }

    @Test void test3FiltBTN() {
        clickOn("#filtBTN");
        assertTrue(DPL.FT);
    }
}