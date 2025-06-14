package ua.nulp.trainmanager.UI.controllers;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ua.nulp.trainmanager.DPL.DPL;
import ua.nulp.trainmanager.DPL.database.Database;
import ua.nulp.trainmanager.DPL.wagons.Cargo;
import ua.nulp.trainmanager.DPL.wagons.Wagon;

import static org.junit.jupiter.api.Assertions.*;

class AddCargoFormUIControllerTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/ua/nulp/trainmanager/UI/AddCargoForm.fxml")
        );
        stage.setScene(new javafx.scene.Scene(loader.load()));
        stage.show();
    }

    @Test
    void testValidInput() {
        DPL.wagons = new Wagon[0];
        Database.DB_NAME = "test.db";
        clickOn("#tfName").write("test");
        clickOn("#tfSpeed").write("1");
        clickOn("#tfWeight").write("2");
        clickOn("#tfCapacity").write("3");
        clickOn("#btnSubmit");
        Cargo cargo = new Cargo(0,"test", 1, 2, 3);
        Wagon[] wagons = new Wagon[1];
        wagons[0] = cargo;

        assertTrue(eqWags(DPL.wagons, wagons));
    }

    @Test
    void testInvalidInput() {
        Database.DB_NAME = "test.db";
        clickOn("#btnSubmit");
        Label messageLabel = lookup("#lblError").query();
        assertEquals("Будь ласка, заповніть усі поля.", messageLabel.getText());

        clickOn("#tfName").write("test");
        clickOn("#btnSubmit");
        messageLabel = lookup("#lblError").query();
        assertEquals("Будь ласка, заповніть усі поля.", messageLabel.getText());

        clickOn("#tfSpeed").write("t");
        clickOn("#btnSubmit");
        messageLabel = lookup("#lblError").query();
        assertEquals("Будь ласка, заповніть усі поля.", messageLabel.getText());

        clickOn("#tfWeight").write("t");
        clickOn("#btnSubmit");
        messageLabel = lookup("#lblError").query();
        assertEquals("Будь ласка, заповніть усі поля.", messageLabel.getText());

        clickOn("#tfCapacity").write("t");
        clickOn("#btnSubmit");
        messageLabel = lookup("#lblError").query();
        assertEquals("Швидкість має бути числом.", messageLabel.getText());

        doubleClickOn("#tfSpeed");
        write("");
        clickOn("#tfSpeed").write("-1");
        clickOn("#btnSubmit");
        messageLabel = lookup("#lblError").query();
        assertEquals("Швидкість не може бути від’ємною.", messageLabel.getText());

        doubleClickOn("#tfSpeed");
        write("");
        clickOn("#tfSpeed").write("1");
        clickOn("#btnSubmit");
        messageLabel = lookup("#lblError").query();
        assertEquals("Вага має бути числом.", messageLabel.getText());

        doubleClickOn("#tfWeight");
        write("");
        clickOn("#tfWeight").write("-1");
        clickOn("#btnSubmit");
        messageLabel = lookup("#lblError").query();
        assertEquals("Вага не може бути від’ємною.", messageLabel.getText());

        doubleClickOn("#tfWeight");
        write("");
        clickOn("#tfWeight").write("1");
        clickOn("#btnSubmit");
        messageLabel = lookup("#lblError").query();
        assertEquals("Ємність має бути числом.", messageLabel.getText());

        doubleClickOn("#tfCapacity");
        write("");
        clickOn("#tfCapacity").write("-1");
        clickOn("#btnSubmit");
        messageLabel = lookup("#lblError").query();
        assertEquals("Ємність не може бути від’ємною.", messageLabel.getText());

        doubleClickOn("#tfCapacity");
        write("");
        clickOn("#tfCapacity").write("1");
        clickOn("#btnSubmit");
    }

    private boolean eqWags(Wagon[] wagons1, Wagon[] wagons2) {
        if (wagons1.length == wagons2.length) {
            for (int i = 0; i < wagons1.length; i++) {
                if (!eqWag(wagons1[i], wagons2[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean eqWag(Wagon wagon1, Wagon wagon2) {
        if (wagon1.getClass().getName().equals(wagon2.getClass().getName())) {
            if (wagon1.getName().equals(wagon2.getName())) {
                if (wagon1.getSpeed() == wagon2.getSpeed()) {
                    if (wagon1.getWeight() == wagon2.getWeight()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}