package ua.nulp.trainmanager.DPL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.nulp.trainmanager.DPL.database.*;
import ua.nulp.trainmanager.DPL.train.Train;
import ua.nulp.trainmanager.DPL.wagons.Cargo;
import ua.nulp.trainmanager.DPL.wagons.Loc;
import ua.nulp.trainmanager.DPL.wagons.Passengers;
import ua.nulp.trainmanager.DPL.wagons.Wagon;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DPLTest {
    Wagon wagons[] = new Wagon[3];
    Train train1;
    Train train2;

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
        if (wagons1.length == wagons2.length && wagons1.length != 0) {
            for (int i = 0; i < wagons1.length; i++) {
                if (!eqWag(wagons1[i], wagons2[i])) {
                    return false;
                }
            }
            return true;
        } else if (wagons1.length == 0 && wagons1.length == wagons2.length) {
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

    @BeforeEach
    public void start() {
        Database.DB_NAME = "test.db";
        DBInit.createTable();
        clearTable();
        Wagon loc = new Loc(DBWrite.insertWagon("test", 120, 50, 150, 10, 0), "test", 120, 50, 150, 10);
        Wagon cargo = new Cargo(DBWrite.insertWagon("test", 100, 50, 40, 0, 0), "test", 100, 50, 40);
        Wagon pass = new Passengers(DBWrite.insertWagon("test", 140, 50, 150, 50, 50),"test", 140, 50, 150, 50, 50);
        wagons[0] = loc;
        wagons[1] = cargo;
        wagons[2] = pass;
        train1 = new Train(DBWrite.insertTrain("test1"), "test1");
        train2 = new Train(DBWrite.insertTrain("test2"), "test2");
    }

    @AfterEach
    public void finish() {
        clearTable();
    }

    @Test
    void addWagon() {
        DPL.wagons = new Wagon[0];
        clearTable();
        DPL.addWagon("test", 120, 50, 150, 10, 0);
        DPL.addWagon("test", 100, 50, 40, 0, 0);
        DPL.addWagon("test", 140, 50, 150, 50, 50);
        assertTrue(eqWags(DPL.wagons, wagons));
    }

    @Test
    void getWagonsByUid () {
        DPL.wagons = wagons;
        int uids[] = new int[0];
        Wagon[] rW = DPL.getWagonsByUid(uids);
        Wagon[] eW = new Wagon[0];
        assertTrue(eqWags(rW, eW));
        uids = new int[1];
        uids[0] = wagons[2].getUid();
        rW = DPL.getWagonsByUid(uids);
        eW = new Wagon[1];
        eW[0] = wagons[2];
        assertTrue(eqWags(rW, eW));
        uids = new int[3];
        uids[0] = wagons[0].getUid();
        uids[1] = wagons[1].getUid();
        uids[2] = wagons[2].getUid();
        rW = DPL.getWagonsByUid(uids);
        eW = new Wagon[3];
        eW[0] = wagons[0];
        eW[1] = wagons[1];
        eW[2] = wagons[2];
        assertTrue(eqWags(rW, eW));
        DPL.wagons = new Wagon[0];
        rW = DPL.getWagonsByUid(uids);
    }

    @Test
    void delWagon() {
        DPL.wagons = wagons;
        DPL.delWagon(0);
        assertTrue(eqWags(DPL.wagons, wagons));
        DPL.delWagon(wagons[0].getUid());
        wagons = DBRead.readWagons();
        assertTrue(eqWags(wagons, DPL.wagons));
        DBWrite.insertTrainWagons(0, wagons[0].getUid());
        DPL.delWagon(wagons[0].getUid());
        assertTrue(eqWags(wagons, DPL.wagons));
    }

    @Test
    void addTrain() {
        DPL.trains = new Train[1];
        Train[] trains = new Train[2];
        DPL.trains[0] = train1;
        trains[0] = train1;
        trains[1] = train2;
        DBDelete.delTrain(train2.getUid());
        DPL.addTrain("test2");
        assertTrue(eqTrs(DPL.trains, trains));
    }

    @Test
    void getTrainId() {
        DPL.trains = new Train[2];
        DPL.trains[0] = train1;
        DPL.trains[1] = train2;
        assertEquals(2, DPL.getTrainId(-1));
        assertEquals(1, DPL.getTrainId(train2.getUid()));
    }

    @Test
    void delTrain() {
        DPL.trains = new Train[2];
        Train[] trains = new Train[1];
        DPL.trains[0] = train1;
        DPL.trains[1] = train2;
        trains[0] = train1;
        DPL.delTrain(DPL.trains[1].getUid());
        assertTrue(eqTrs(DPL.trains, trains));
    }
}