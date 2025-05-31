package ua.nulp.trainmanager.DPL;

import org.junit.jupiter.api.Test;
import ua.nulp.trainmanager.DPL.train.Train;
import ua.nulp.trainmanager.DPL.wagons.Cargo;
import ua.nulp.trainmanager.DPL.wagons.Wagon;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DPLTest {

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

    @Test
    void addWagon() {
        DPL.wagons = new Wagon[1];
        Wagon[] wagons = new Wagon[2];
        Wagon wag = new Cargo("t", 1, 2, 3);
        wagons[0] = wag;
        wagons[1] = wag;
        DPL.wagons[0] = wag;
        DPL.addWagon(wag);
        assertTrue(eqWags(DPL.wagons, wagons));
    }

    @Test
    void addTrain() {
        DPL.trains = new Train[1];
        Train[] trains = new Train[2];
        Train tr = new Train("t");
        trains[0] = tr;
        trains[1] = tr;
        DPL.trains[0] = tr;
        DPL.addTrain(tr);
        assertTrue(eqTrs(DPL.trains, trains));
    }

    @Test
    void delWagon() {
        DPL.wagons = new Wagon[2];
        Wagon[] wagons = new Wagon[1];
        Wagon cargo = new Cargo("t", 1, 2, 3);
        DPL.wagons[0] = cargo;
        DPL.wagons[1] = cargo;
        wagons[0] = cargo;
        DPL.delWagon(0);
        assertTrue(eqWags(DPL.wagons, wagons));
    }

    @Test
    void delTrain() {
        DPL.trains = new Train[2];
        Train[] trains = new Train[1];
        Train train = new Train("t");
        DPL.trains[0] = train;
        DPL.trains[1] = train;
        trains[0] = train;
        DPL.delTrain(0);
        assertTrue(eqTrs(DPL.trains, trains));
    }
}