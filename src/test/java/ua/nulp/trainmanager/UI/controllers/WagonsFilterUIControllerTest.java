package ua.nulp.trainmanager.UI.controllers;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ua.nulp.trainmanager.DPL.DPL;

import static org.junit.jupiter.api.Assertions.*;

class WagonsFilterUIControllerTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/ua/nulp/trainmanager/UI/WagonsFilterUI.fxml")
        );
        stage.setScene(new javafx.scene.Scene(loader.load()));
        stage.show();
    }

    @Test
    void testTypeGroup() {
        clickOn("#r1");
        clickOn("#r2");
        clickOn("#r3");
        clickOn("#r4");
    }

    @Test
    void testPrevBTN() {
        clickOn("#prevBTN");
        Label header = lookup("#header").query();
        assertEquals("Управління вагонами", header.getText());
    }

    @Test void test1FiltBTN() {
        clickOn("#tfMinSpeed").write("1");
        clickOn("#tfMaxSpeed").write("1");
        clickOn("#tfMaxWeight").write("1");
        clickOn("#tfMinTrac").write("1");
        clickOn("#tfMaxCons").write("1");
        clickOn("#tfMinPass").write("1");
        clickOn("#tfMinComf").write("1");
        clickOn("#tfMinCargo").write("1");
        clickOn("#filtBTN");
        assertTrue(DPL.FW);
    }

    @Test void test2FiltBTN() {
        clickOn("#tfMinSpeed").write("-1");
        clickOn("#tfMaxSpeed").write("-1");
        clickOn("#tfMaxWeight").write("-1");
        clickOn("#tfMinTrac").write("-1");
        clickOn("#tfMaxCons").write("-1");
        clickOn("#tfMinPass").write("-1");
        clickOn("#tfMinComf").write("-1");
        clickOn("#tfMinCargo").write("-1");
        clickOn("#filtBTN");
        assertTrue(DPL.FW);
    }

    @Test void test3FiltBTN() {
        clickOn("#filtBTN");
        assertTrue(DPL.FW);
    }
}