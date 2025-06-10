package ua.nulp.trainmanager.UI.controllers;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ua.nulp.trainmanager.DPL.DPL;
import ua.nulp.trainmanager.DPL.database.DBInit;
import ua.nulp.trainmanager.DPL.database.Database;
import ua.nulp.trainmanager.DPL.train.Train;
import ua.nulp.trainmanager.DPL.wagons.Wagon;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TrainRedactorUIControllerTest extends ApplicationTest {

    private void clearTable() {
        Database.DB_NAME = "test.db";
        String sql1 = "DELETE FROM wagons";
        String sql2 = "DELETE FROM trains";
        String sql3 = "DELETE FROM trains_wagons";

        try (Connection conn = Database.connect();
             var stmt = conn.createStatement()){
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);
        } catch (SQLException e) {

        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Database.DB_NAME = "test.db";
        DBInit.createTable();
        clearTable();
        DPL.trains = new Train[0];
        DPL.wagons = new Wagon[0];
        DPL.addWagon("TL", 120, 60, 600, 10, 0);
        DPL.addWagon("TC", 100, 120, 90, 0, 0);
        DPL.addWagon("TP", 140, 45,100, 50, 75);
        DPL.addTrain("test");
        DPL.id = 0;
        DPL.trains[0].add(DPL.wagons[0]);
        DPL.trains[0].add(DPL.wagons[1]);
        DPL.trains[0].add(DPL.wagons[2]);

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