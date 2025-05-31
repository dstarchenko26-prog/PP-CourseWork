package ua.nulp.trainmanager.DPL.wagons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WagonTest {

    class Wag extends Wagon {
        Wag(String name, int speed, int weight) {
            super(name, speed, weight);
        }
    }

    private boolean equals(Wagon wagon1, Wagon wagon2) {
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

    Wagon wag = new Wag("test", 10, 20);
    Wagon loc = new Loc("test", 10, 20, 30, 40);
    Wagon cargo = new Cargo("test", 10, 20, 30);
    Wagon pass = new Passengers("test", 10, 20, 30, 40, 50);

    @Test
    void getTraction() {
        assertEquals(30, loc.getTraction());
        assertEquals(0, cargo.getTraction());
        assertEquals(0, pass.getTraction());
    }

    @Test
    void getConsumption() {
        assertEquals(40, loc.getConsumption());
        assertEquals(0, cargo.getConsumption());
        assertEquals(0, pass.getConsumption());
    }

    @Test
    void getComfort() {
        assertEquals(0, loc.getComfort());
        assertEquals(0, cargo.getComfort());
        assertEquals(40, pass.getComfort());
    }

    @Test
    void getCapacity() {
        assertEquals(0, loc.getCapacity());
        assertEquals(30, cargo.getCapacity());
        assertEquals(30, pass.getCapacity());
    }

    @Test
    void getAmountOfLuggage() {
        assertEquals(0, loc.getAmountOfLuggage());
        assertEquals(0, cargo.getAmountOfLuggage());
        assertEquals(50, pass.getAmountOfLuggage());
    }

    @Test
    void get() {
        assertEquals(null, wag.get());
        assertTrue(equals(loc, loc.get()));
        assertTrue(equals(cargo, cargo.get()));
        assertTrue(equals(pass, pass.get()));
    }

    @Test
    void getString() {
        assertEquals(null, wag.getString());
        assertEquals("Локомотив: test Вага: 20 т. Швидкість: 10 км./год. Тяга: 30 т. Розхід: 40 л./(т. * год.)", loc.getString());
        assertEquals("Вагон: test Вага: 20 т. Швидкість: 10 км./год. Місткість: 30 т.", cargo.getString());
        assertEquals("Вагон: test Вага: 20 т. Швидкість: 10 км./год. Рівень комфорту: 40 Пасажирських місць: 30 Місць для багажу: 50", pass.getString());
    }
}

