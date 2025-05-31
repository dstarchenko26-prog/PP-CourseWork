package ua.nulp.trainmanager.DPL.database;

import org.junit.jupiter.api.Test;
import ua.nulp.trainmanager.DPL.train.Train;
import ua.nulp.trainmanager.DPL.wagons.Cargo;
import ua.nulp.trainmanager.DPL.wagons.Loc;
import ua.nulp.trainmanager.DPL.wagons.Passengers;
import ua.nulp.trainmanager.DPL.wagons.Wagon;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DBWriteTest {

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

    @Test
    void saveWagons() {
        Wagon[] wagons = new Wagon[3];
        Loc loc = new Loc("l1", 1, 2, 3, 4);
        Cargo cargo = new Cargo("c1", 1, 2, 3);
        Passengers pass = new Passengers("p1", 1, 2, 3, 4, 5);

        wagons[0] = loc;
        wagons[1] = cargo;
        wagons[2] = pass;

        DBWrite.saveWagons(wagons, "test");
        Wagon[] readWagons = DBRead.readWagons("test");
        assertTrue(eqWags(readWagons, wagons));

    }

    @Test
    void saveTrains() {
        Wagon[] wagons = new Wagon[3];
        Loc loc = new Loc("l1", 1, 2, 3, 4);
        Cargo cargo = new Cargo("c1", 1, 2, 3);
        Passengers pass = new Passengers("p1", 1, 2, 3, 4, 5);

        wagons[0] = loc;
        wagons[1] = cargo;
        wagons[2] = pass;

        Train[] trains = new Train[1];
        trains[0] = new Train("t1", wagons);

        DBWrite.saveTrains(trains, "test");
        Train[] readTrains = DBRead.readTrains("test");

        assertTrue(eqTrs(readTrains, trains));
    }
}