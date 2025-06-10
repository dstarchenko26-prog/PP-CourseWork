package ua.nulp.trainmanager.DPL.database;

import ua.nulp.trainmanager.util.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DBInit {
    public static void createTable() {
        createTableWagon();
        createTableTrains();
        createTableTrainsWagons();
    }

    private static void createTableWagon() {
        String sql = "CREATE TABLE IF NOT EXISTS wagons (" +
                "uid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "speed INTEGER NOT NULL, " +
                "weight INTEGER NOT NULL, " +
                "param1 INTEGER NOT NULL, " +
                "param2 INTEGER NOT NULL, " +
                "param3 INTEGER NOT NULL)";

        try (Connection conn = Database.connect();
             var stmt = conn.createStatement()) {
            stmt.execute(sql);
            Logger.info("Перевірена/створена таблиця вагонів");
        } catch (SQLException e) {
            Logger.error("Помилка при створенні таблиці вагонів", "");
        }
    }

    private static void createTableTrains () {
        String sql = "CREATE TABLE IF NOT EXISTS trains (" +
                "uid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL)";

        try (Connection conn = Database.connect();
             var stmt = conn.createStatement()) {
            stmt.execute(sql);
            Logger.info("Перевірена/створена таблиця поїздів");
        } catch (SQLException e) {
            Logger.error("Помилка при створенні таблиці поїздів", "");
        }
    }

    private static void createTableTrainsWagons () {
        String sql = "CREATE TABLE IF NOT EXISTS trains_wagons (" +
                "id_connect INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "uid_t INTEGER NOT NULL, " +
                "uid_w INTEGER NOT NULL)";

        try (Connection conn = Database.connect();
            var stmt = conn.createStatement()){
            stmt.execute(sql);
            Logger.info("Перевірена/створена таблиця приналежності вагонів");
        } catch (SQLException e) {
            Logger.error("Помилка при створені таблиці приналежності вагонів", "");
        }
    }
}
