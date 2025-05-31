package ua.nulp.trainmanager.UI.controllers;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ua.nulp.trainmanager.DPL.DPL;
import ua.nulp.trainmanager.DPL.wagons.Cargo;
import ua.nulp.trainmanager.DPL.wagons.Loc;
import ua.nulp.trainmanager.DPL.wagons.Passengers;
import ua.nulp.trainmanager.DPL.wagons.Wagon;

import static org.junit.jupiter.api.Assertions.*;

class WagonsMenuUIControllerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {

        DPL.wagons = new Wagon[6];
        Loc loc = new Loc("TL", 120, 60, 600, 10);
        Cargo cargo = new Cargo("TC", 100, 120, 90);
        Passengers pass = new Passengers("TP", 140, 45,100, 50, 75);
        DPL.wagons[0] = loc;
        DPL.wagons[1] = pass;
        DPL.wagons[2] = cargo;
        DPL.wagons[3] = loc;
        DPL.wagons[4] = pass;
        DPL.wagons[5] = cargo;

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
        DPL.usParams.FiltWParams(1, 200, 200, 1, 200, 1, 1, 1,"TL", 1);
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
        for (int i = 0; i < 5; i++) {
            wagons[i] = DPL.wagons[i + 1];
        }
        clickOn("#wagBTNdel0");
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