package ua.nulp.trainmanager.DPL.params;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsParamsTest {
    UsParams param = new UsParams();

    @Test
    void filtWParams() {
        param.FiltWParams(1, 2, 3, 4, 5,6,7,8,"test",9);
        assertEquals(1, param.minSpeed);
        assertEquals(2, param.maxSpeed);
        assertEquals(3, param.maxWeight);
        assertEquals(4, param.minTrac);
        assertEquals(5, param.maxCons);
        assertEquals(6, param.minPass);
        assertEquals(7, param.minComf);
        assertEquals(8, param.minCargo);
        assertEquals("test", param.name);
        assertEquals(9, param.type);
    }

    @Test
    void filtTParams() {
        param.FiltTParams(1,2,3,4,5,6,7,"test");
        assertEquals(1, param.minSpeed);
        assertEquals(2, param.maxSpeed);
        assertEquals(3, param.maxWeight);
        assertEquals(4, param.minTrac);
        assertEquals(5, param.maxCons);
        assertEquals(6, param.minPass);
        assertEquals(7, param.minCargo);
        assertEquals("test", param.name);
    }
}