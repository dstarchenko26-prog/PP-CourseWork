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
    void save() {
        Database.DB_NAME = "test.db";
        DBInit.createTable();

        Wagon[] wagons = new Wagon[3];
        Loc loc = new Loc(0,"l1", 1, 2, 3, 4);
        Cargo cargo = new Cargo(1, "c1", 1, 2, 3);
        Passengers pass = new Passengers(2, "p1", 1, 2, 3, 4, 5);

        wagons[0] = loc;
        wagons[1] = cargo;
        wagons[2] = pass;

        for (int i = 0; i < wagons.length; i++) {
            if (wagons[i].getAmountOfLuggage() != 0) {
                DBWrite.insertWagon(wagons[i].getName(), wagons[i].getSpeed(), wagons[i].getWeight(), wagons[i].getCapacity(), wagons[i].getComfort(), wagons[i].getAmountOfLuggage());
            } else if (wagons[i].getTraction() != 0) {
                DBWrite.insertWagon(wagons[i].getName(), wagons[i].getSpeed(), wagons[i].getWeight(), wagons[i].getTraction(), wagons[i].getConsumption(), 0);
            } else {
                DBWrite.insertWagon(wagons[i].getName(), wagons[i].getSpeed(), wagons[i].getWeight(), wagons[i].getCapacity(), 0, 0);
            }
        }

        Wagon[] readWagons = DBRead.readWagons();
        assertTrue(eqWags(readWagons, wagons));

        Train[] trains = new Train[2];
        trains[0] = new Train(0,"t1", readWagons);
        trains[1] = new Train(1,"t1", readWagons);

        for (int i = 0; i < trains.length; i++) {
            DBWrite.insertTrain(trains[i].getName());
        }

        int uids[] = new int[2];

        for (int i = 0; i < trains.length; i++) {
            uids[i] = DBRead.readTrains()[i].getUid();
        }

        for (int i = 0; i < trains.length; i++) {
            for (int j = 0; j < trains[i].getWagons().length; j++) {
                DBWrite.insertTrainWagons(uids[i], trains[i].getWagons()[j].getUid());
            }
        }

        Train[] readTrains = DBRead.readTrains();

        assertTrue(eqTrs(readTrains, trains));

        for (int i = 0; i < readTrains[1].getWagons().length; i++) {
            DBDelete.delTrainWagon(readTrains[1].getUid(), readTrains[1].getWagons()[i].getUid());
        }

        DBDelete.delWagon(readWagons[0].getUid());

        for (int i = 0; i < readTrains.length; i++) {
            DBDelete.delTrain(readTrains[i].getUid());
        }

        for (int i = 0; i < readWagons.length; i++) {
            DBDelete.delWagon(readWagons[i].getUid());
        }
    }
}