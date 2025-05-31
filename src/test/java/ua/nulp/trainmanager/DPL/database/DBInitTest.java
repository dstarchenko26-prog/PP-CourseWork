package ua.nulp.trainmanager.DPL.database;

import org.junit.jupiter.api.Test;

class DBInitTest {

    @Test
    void createTable() {
        DBInit.createTable("test");
    }

    @Test
    void createTableTrain() {
        DBInit.createTableTrain("test");
    }
}