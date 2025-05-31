package ua.nulp.trainmanager.UI.controllers;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ua.nulp.trainmanager.DPL.DPL;
import ua.nulp.trainmanager.DPL.train.Train;
import ua.nulp.trainmanager.DPL.wagons.Cargo;
import ua.nulp.trainmanager.DPL.wagons.Loc;
import ua.nulp.trainmanager.DPL.wagons.Passengers;
import ua.nulp.trainmanager.DPL.wagons.Wagon;

import static org.junit.jupiter.api.Assertions.*;

class TrainRedactorUIControllerTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        DPL.wagons = new Wagon[3];
        Loc loc = new Loc("TL", 120, 60, 600, 10);
        Cargo cargo = new Cargo("TC", 100, 120, 90);
        Passengers pass = new Passengers("TP", 140, 45,100, 50, 75);
        DPL.wagons[0] = loc;
        DPL.wagons[1] = pass;
        DPL.wagons[2] = cargo;
        DPL.id = 0;
        DPL.trains = new Train[1];
        Train tr = new Train("test", DPL.wagons);
        DPL.trains[0] = tr;

        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/ua/nulp/trainmanager/UI/TrainRedactorUI.fxml")
        );
        stage.setScene(new javafx.scene.Scene(loader.load()));
        stage.show();
    }

    @Test
    void testPrevBTN () {
        clickOn("#prevBTN");
    }

    @Test
    void testSortBTN () {
        clickOn("#sortBTN");
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

    @Test
    void testAddBTN () {
        clickOn("#addBTN");
        Label header = lookup("#header").query();
        assertEquals("Вибір вагона", header.getText());
    }

    @Test
    void test1DelBTN () {
        clickOn("#wagBTNdel0");
        Label er = lookup("#lblError").query();
        assertEquals("Вага перевищить тягу", er.getText());
    }

    @Test
    void test2DelBTN () {
        Wagon[] wagons = DPL.wagons;
        Wagon[] test = new Wagon[2];
        test[0] = wagons[0];
        test[1] = wagons[2];
        clickOn("#wagBTNdel1");
        assertTrue(eqWags(test, DPL.trains[0].getWagons()));
    }
}