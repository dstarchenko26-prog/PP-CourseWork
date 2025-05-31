package ua.nulp.trainmanager.DPL.database;

import ua.nulp.trainmanager.util.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DBInit {
    public static void createTable(String sId) {
        createTableWagon(sId);
        createTableLoc(sId);
        createTableCargo(sId);
        createTablePassengers(sId);
        createTableTrains(sId);
    }

    protected static void createTableTrain(String sId) {
        createTableWagon(sId);
        createTableLoc(sId);
        createTableCargo(sId);
        createTablePassengers(sId);
    }

    private static void createTableWagon(String sId) {
        String sql = "CREATE TABLE IF NOT EXISTS wagons"+ sId +" (" +
                "uid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id INTEGER NOT NULL, " +
                "name TEXT NOT NULL, " +
                "speed INTEGER NOT NULL, " +
                "weight INTEGER NOT NULL, " +
                "type INTEGER NOT NULL)";

        try (Connection conn = Database.connect();
             var stmt = conn.createStatement()) {
            stmt.execute(sql);
            Logger.info("Перевірена/створена корінна таблиця вагонів");
        } catch (SQLException e) {
            Logger.error("Помилка при створенні кор. таблиці вагонів", "");
        }
    }

    private static void createTableLoc(String sId) {
        String sql = "CREATE TABLE IF NOT EXISTS loc"+ sId +" (" +
                "id INTEGER PRIMARY KEY, " +
                "traction INTEGER NOT NULL, " +
                "consumption INTEGER NOT NULL)";

        try (Connection conn = Database.connect();
             var stmt = conn.createStatement()) {
            stmt.execute(sql);
            Logger.info("Перевірена/створена таблиця локомотивів");
        } catch (SQLException e) {
            Logger.error("Помилка при створенні таблиці локомотивів", "");
        }
    }

    private static void createTableCargo(String sId) {
        String sql = "CREATE TABLE IF NOT EXISTS cargo"+ sId +" (" +
                "id INTEGER PRIMARY KEY, " +
                "capacity INTEGER NOT NULL)";

        try (Connection conn = Database.connect();
             var stmt = conn.createStatement()) {
            stmt.execute(sql);
            Logger.info("Перевірена/створена таблиця грузових вагонів");
        } catch (SQLException e) {
            Logger.error("Помилка при створенні таблиці грузових вагонів", "");
        }
    }

    private static void createTablePassengers(String sId) {
        String sql = "CREATE TABLE IF NOT EXISTS passengers"+ sId +" (" +
                "id INTEGER PRIMARY KEY, " +
                "capacity INTEGER NOT NULL, " +
                "comfort INTEGER NOT NULL, " +
                "amountOfLuggage INTEGER NOT NULL)";

        try (Connection conn = Database.connect();
             var stmt = conn.createStatement()) {
            stmt.execute(sql);
            Logger.info("Перевірена/створена таблиця пасажирських вагонів");
        } catch (SQLException e) {
            Logger.error("Помилка при створенні таблиці пасажирських вагонів", "");
        }
    }

    private static void createTableTrains (String sId) {
        String sql = "CREATE TABLE IF NOT EXISTS trains" + sId +" (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL)";

        try (Connection conn = Database.connect();
             var stmt = conn.createStatement()) {
            stmt.execute(sql);
            Logger.info("Перевірена/створена таблиця поїздів");
        } catch (SQLException e) {
            Logger.error("Помилка при створенні таблиці поїздів", "");
        }
    }
}
