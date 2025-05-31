package ua.nulp.trainmanager.UI.controllers;

import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import ua.nulp.trainmanager.DPL.DPL;
import ua.nulp.trainmanager.DPL.train.Train;
import ua.nulp.trainmanager.DPL.wagons.Cargo;
import ua.nulp.trainmanager.DPL.wagons.Loc;
import ua.nulp.trainmanager.DPL.wagons.Passengers;
import ua.nulp.trainmanager.DPL.wagons.Wagon;

import static org.junit.jupiter.api.Assertions.*;

class TrainsMenuUIControllerTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {

        DPL.trains = new Train[3];
        DPL.wagons = new Wagon[6];
        Loc loc = new Loc("TL", 120, 60, 600, 10);
        Cargo cargo = new Cargo("TC", 100, 120, 90);
        Passengers pass = new Passengers("TP", 140, 45,100, 50, 75);
        DPL.wagons[0] = loc;
        DPL.wagons[1] = pass;
        DPL.wagons[2] = cargo;
        Train t1 = new Train("t1");
        Train t2 = new Train("t2");
        Train t3 = new Train("t3");
        t1.add(loc);
        t1.add(loc);
        t1.add(loc);
        t2.add(loc);
        t2.add(cargo);
        t2.add(cargo);
        t2.add(pass);
        t3.add(loc);
        t3.add(cargo);
        t3.add(pass);
        t3.add(pass);
        DPL.trains[0] = t1;
        DPL.trains[1] = t2;
        DPL.trains[2] = t3;

        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/ua/nulp/trainmanager/UI/TrainsMenuUI.fxml")
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
                .filter(window -> ((Stage) window).getTitle().equals("Новий поїзд"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Модальне вікно не знайдено"));

        assertEquals("Новий поїзд", ((Stage) modalWindow).getTitle());
    }

    @Test void testPrevBTN () {
        clickOn("#prevBTN");
        Label header = lookup("#header").query();
        assertEquals("Головне меню", header.getText());
    }

    @Test void testFiltBTN() {
        clickOn("#filtBTN");
        Label header = lookup("#header").query();
        assertEquals("Фільтр поїздів", header.getText());
    }

    @Test void testDelBTN() {
        Train[] trains = new Train[2];
        for (int i = 0; i < 2; i++) {
            trains[i] = DPL.trains[i + 1];
        }
        clickOn("#trBTNdel0");
        assertTrue(eqTrs(trains, DPL.trains));
    }

    @Test void testRedactBTN() {
        clickOn("#trBTNredact0");

        Window modalWindow = listTargetWindows()
                .stream()
                .filter(window -> window instanceof Stage)
                .filter(window -> ((Stage) window).getTitle().equals("Редактор"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Модальне вікно не знайдено"));

        assertEquals("Редактор", ((Stage) modalWindow).getTitle());
    }

    @Test void filt1() {
        DPL.FT = true;
        DPL.usParams.FiltTParams(0, 0, 0, 0, 0, 0, 0, "");
    }
    @Test void filt2() {
        DPL.FT = true;
        DPL.usParams.FiltTParams(1, 200, 1000, 1, 500000, 1, 1, "t2");
    }

    @Test void filt3() {
        DPL.FT = true;
        DPL.usParams.FiltTParams(110, 0, 0, 2000, 0, 0, 0, "");
    }

    @Test void filt4() {
        DPL.FT = true;
        DPL.usParams.FiltTParams(0, 110, 340, 0, 0, 0, 100, "");
    }

    @Test void filt5() {
        DPL.FT = true;
        DPL.usParams.FiltTParams(0, 0, 0, 0, 3400, 10, 0, "");
    }

    private boolean eqTrs(Train[] ts1, Train[] ts2) {
        if (ts1.length == ts2.length) {
            for (int i = 0; i < ts1.length; i++) {
                if (!eqTr(ts1[i], ts2[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean eqTr(Train t1, Train t2) {
        if (t1.getName().equals(t2.getName())) {
            return eqWags(t1.getWagons(), t2.getWagons());
        }
        return false;
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