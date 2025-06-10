package ua.nulp.trainmanager.DPL.train;

import org.junit.jupiter.api.*;
import ua.nulp.trainmanager.DPL.database.DBInit;
import ua.nulp.trainmanager.DPL.database.DBWrite;
import ua.nulp.trainmanager.DPL.database.Database;
import ua.nulp.trainmanager.DPL.wagons.Cargo;
import ua.nulp.trainmanager.DPL.wagons.Loc;
import ua.nulp.trainmanager.DPL.wagons.Passengers;
import ua.nulp.trainmanager.DPL.wagons.Wagon;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TrainTest {
    Wagon[] wagons = new Wagon[3];
    Train train1;
    Train train2;
    Train train3;

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
        train2.add(wagons[0]);
        train2.add(wagons[1]);
        train2.add(wagons[2]);
        train3 = new Train(DBWrite.insertTrain("test3"), "test3");
    }

    @AfterEach
    public void finish() {
        clearTable();
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
    void getName() {
        assertEquals("test1", train1.getName());
        assertEquals("test2", train2.getName());
    }

    @Test
    void getSpeed() {
        assertEquals(0, train1.getSpeed());
        assertEquals(100, train2.getSpeed());
    }

    @Test
    void getPass() {
        assertEquals(0, train1.getPass());
        assertEquals(150, train2.getPass());
    }

    @Test
    void getCargo() {
        assertEquals(0, train1.getCargo());
        assertEquals(40, train2.getCargo());
    }

    @Test
    void getConsumption() {
        assertEquals(0, train1.getConsumption());
        assertEquals(1500, train2.getConsumption());
    }

    @Test
    void getWagons() {
        assertFalse(train1.getWagons().equals(wagons));
    }

    @Test
    void getWeight() {
        assertEquals(0, train1.getWeight());
        assertEquals(150, train2.getWeight());
    }

    @Test
    void getTraction() {
        assertEquals(0, train1.getTraction());
        assertEquals(150, train2.getTraction());
    }

    @Test
    void getLuggage() {
        assertEquals(0, train1.getLuggage());
        assertEquals(50, train2.getLuggage());
    }

    @Test
    void getString() {
        assertEquals("test1: Швидкість: 0 км./год. Вага: 0 т. Тяга: 0 т. Росхід: 0 л./год\n" +
                "Пасажиромісткість: 0 Місць для багажу: 0 Грузомісткість: 0т.", train1.getString());
        assertEquals("test2: Швидкість: 100 км./год. Вага: 150 т. Тяга: 150 т. Росхід: 1500 л./год\n" +
                "Локомотив: test Вага: 50 т. Швидкість: 120 км./год. Тяга: 150 т. Розхід: 10 л./(т. * год.)\n" +
                "Вагон: test Вага: 50 т. Швидкість: 100 км./год. Місткість: 40 т.\n" +
                "Вагон: test Вага: 50 т. Швидкість: 140 км./год. Рівень комфорту: 50 Пасажирських місць: 150 Місць для багажу: 50\n" +
                "Пасажиромісткість: 150 Місць для багажу: 50 Грузомісткість: 40т.", train2.getString());
    }

    @Test
    void preAdd() {
        assertTrue(train1.preAdd(wagons[0]));
        assertTrue(train2.preAdd(wagons[0]));
        assertFalse(train1.preAdd(wagons[2]));
        assertFalse(train2.preAdd(wagons[1]));
        train3.add(wagons[0]);
        assertTrue(train3.preAdd(wagons[1]));
    }

    @Test
    void getUid() {
        int id = train1.getUid();
    }

    @Test
    void preDel() {
        assertFalse(train2.preDel(0));
        assertTrue(train2.preDel(1));
        Wagon[] wagTest = new Wagon[2];
        wagTest[0] = wagons[0];
        wagTest[1] = wagons[0];
        train3 = new Train(0,"test", wagTest);
        assertTrue(train3.preDel(1));
    }

    @Test
    void del() {
        train1.add(wagons[0]);
        train1.del(0);
        assertEquals(0, train1.getWagons().length);
    }

    @Test
    void sort() {
        Loc loc1 = new Loc(0,"TLT", 100, 50, 1000, 10);
        Loc loc2 = new Loc(1,"TLS", 150, 40, 600, 15);
        Cargo cargo1 = new Cargo(2,"TCB", 120, 150, 100);
        Cargo cargo2 = new Cargo(3,"TCS", 140, 100, 60);
        Passengers pass1 = new Passengers(4,"TPBC", 80, 50, 100, 75, 100);
        Passengers pass2 = new Passengers(5,"TPSC", 80, 50, 70, 75, 100);
        Passengers pass3 = new Passengers(6,"TPSS", 80, 50, 70, 50, 100);
        Passengers pass4 = new Passengers(7,"TPBS", 80, 50, 100, 50, 100);
        Wagon[] etWagons = {loc1, loc1, loc2, loc2, loc2, pass1, pass1, pass2, pass4, pass3, cargo1, cargo1, cargo2};
        Wagon[] wagons = new Wagon[13];
        wagons[0] = pass1;
        wagons[1] = loc1;
        wagons[2] = loc2;
        wagons[3] = loc1;
        wagons[4] = loc2;
        wagons[5] = pass1;
        wagons[6] = cargo1;
        wagons[7] = loc2;
        wagons[8] = cargo2;
        wagons[9] = cargo1;
        wagons[10] = pass4;
        wagons[11] = pass3;
        wagons[12] = pass2;

        Train train = new Train(0,"test", wagons);
        train.sort();
        assertTrue(eqWags(train.getWagons(), etWagons));
    }
}