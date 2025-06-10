package ua.nulp.trainmanager.UI.controllers;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ua.nulp.trainmanager.DPL.DPL;
import ua.nulp.trainmanager.DPL.database.DBInit;
import ua.nulp.trainmanager.DPL.database.DBWrite;
import ua.nulp.trainmanager.DPL.database.Database;
import ua.nulp.trainmanager.DPL.wagons.Wagon;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class WagonsMenuUIControllerTest extends ApplicationTest {

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
        DPL.wagons = new Wagon[0];
        DPL.addWagon("TL1", 120, 60, 600, 10, 0);
        DPL.addWagon("TC1", 100, 120, 90, 0, 0);
        DPL.addWagon("TP1", 140, 45,100, 50, 75);
        DPL.addWagon("TL2", 120, 60, 600, 10, 0);
        DPL.addWagon("TC2", 100, 120, 90, 0, 0);
        DPL.addWagon("TP2", 140, 45,100, 50, 75);

        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/ua/nulp/trainmanager/UI/WagonsMenuUI.fxml")
        );
        stage.setScene(new javafx.scene.Scene(loader.load()));
        stage.show();

    }

    @Test
    void testAddBTN() {
        clickOn("#addBTN");

        Window modalWindow = listTargetWindows()
                .stream()
                .filter(window -> window instanceof Stage)
                .filter(window -> ((Stage) window).getTitle().equals("Новий вагон"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Модальне вікно не знайдено"));

        assertEquals("Новий вагон", ((Stage) modalWindow).getTitle());
    }

    @Test void testPrevBTN () {
        clickOn("#prevBTN");
        Label header = lookup("#header").query();
        assertEquals("Головне меню", header.getText());
    }

    @Test void filt1() {
        DPL.FW = true;
        DPL.usParams.FiltWParams(0, 0, 0, 0, 0, 0, 0, 0,"", 0);
    }

    @Test void filt2() {
        DPL.FW = true;
        DPL.usParams.FiltWParams(1, 200, 200, 1, 200, 1, 1, 1,"TL1", 1);
    }

    @Test void filt3() {
        DPL.FW = true;
        DPL.usParams.FiltWParams(0, 50, 0, 0, 0, 0, 0, 0,"", 2);
    }

    @Test void filt4() {
        DPL.FW = true;
        DPL.usParams.FiltWParams(200, 0, 0, 0, 0, 0, 0, 0,"", 3);
    }

    @Test void filt5() {
        DPL.FW = true;
        DPL.usParams.FiltWParams(0, 0, 100, 750, 0, 120, 0, 0,"", 0);
    }

    @Test void filt6() {
        DPL.FW = true;
        DPL.usParams.FiltWParams(0, 0, 0, 0, 1, 200, 0, 200,"", 0);
    }

    @Test void filt7() {
        DPL.FW = true;
        DPL.usParams.FiltWParams(0, 0, 0, 0, 0, 1, 0, 0,"", 3);
    }

    @Test void filt8() {
        DPL.FW = true;
        DPL.usParams.FiltWParams(0, 0, 0, 0, 0, 0, 100, 0,"", 3);
    }

    @Test void filt9() {
        DPL.FW = true;
        DPL.usParams.FiltWParams(0, 0, 0, 0, 0, 0, 1, 0,"", 3);
    }

    @Test void filt10() {
        DPL.FW = true;
        DPL.usParams.FiltWParams(0, 0, 0, 0, 0, 0, 0, 1,"", 2);
    }

    @Test void filt11() {
        DPL.FW = true;
        DPL.usParams.FiltWParams(0, 0, 0, 0, 0, 0, 0, 500,"", 0);
    }

    @Test void testFiltBTN() {
        clickOn("#filtBTN");
        Label header = lookup("#header").query();
        assertEquals("Фільтр вагонів", header.getText());
    }

    @Test void testDelBTN() {
        Wagon[] wagons = new Wagon[5];
        for (int i = 0; i < 2; i++) {
            wagons[i] = DPL.wagons[i];
        }
        for (int i = 2; i < 5; i++) {
            wagons[i] = DPL.wagons[i + 1];
        }
        String query = "#wagBTNdel" + DPL.wagons[2].getUid();
        DPL.FW = false;
        clickOn(query);
        assertTrue(eqWags(wagons, DPL.wagons));
        DBWrite.insertTrainWagons(0, DPL.wagons[0].getUid());
        query = "#wagBTNdel" + DPL.wagons[0].getUid();
        clickOn(query);
        assertTrue(eqWags(wagons, DPL.wagons));
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