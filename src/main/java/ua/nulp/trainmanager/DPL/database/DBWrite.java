package ua.nulp.trainmanager.DPL.database;

import ua.nulp.trainmanager.DPL.train.Train;
import ua.nulp.trainmanager.DPL.wagons.Wagon;
import ua.nulp.trainmanager.util.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBWrite {
    public static void saveWagons(Wagon[] wagons, String sId) {
        String[] tables = {"wagons", "loc", "cargo", "passengers"};
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {
            for (String table : tables) {
                stmt.executeUpdate("DELETE FROM " + table + sId);
            }
        } catch (SQLException e) {
            Logger.error("Помилка при очищені таблиць", "");
        }

        for (int i = 0; i < wagons.length; i++) {
            insertWagon(wagons[i], i, sId);
        }
        Logger.info("Інформація про вагони оновлена");
    }

    public static void saveTrains(Train[] trains, String sId) {
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM trains" + sId);
        } catch (SQLException e) {
            Logger.error("Помилка при очищені таблиць", "");
        }

        for (int i = 0; i < trains.length; i++) {
            insertTrain(trains[i], i, sId);
        }
    }

    private static void insertWagon(Wagon wagon, int id, String sId) {

        String sql1 = "INSERT INTO wagons" + sId + "(id, name, speed, weight, type) VALUES(?, ?, ?, ?, ?)";
        int type;
        String sql2;

        if (wagon.getClass().getName().equals("ua.nulp.trainmanager.DPL.wagons.Loc")) {
            type = 1;
            sql2 = "INSERT INTO loc" + sId + "(id, traction, consumption) VALUES(?, ?, ?)";
        } else if (wagon.getClass().getName().equals("ua.nulp.trainmanager.DPL.wagons.Cargo")) {
            type = 2;
            sql2 = "INSERT INTO cargo" + sId + "(id, capacity) VALUES(?, ?)";
        } else {
            type = 3;
            sql2 = "INSERT INTO passengers" + sId + "(id, capacity, comfort, amountOfLuggage) VALUES (?, ?, ?, ?)";
        }

        try (Connection conn = Database.connect();
             var pstmt = conn.prepareStatement(sql1)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, wagon.getName());
            pstmt.setInt(3, wagon.getSpeed());
            pstmt.setInt(4, wagon.getWeight());
            pstmt.setInt(5, type);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.error("Помилка при додавані в кор. таблицю вагонів", "");
        }

        switch (type) {
            case 1: {
                try (Connection conn = Database.connect();
                     var pstmt = conn.prepareStatement(sql2)) {
                    pstmt.setInt(1, id);
                    pstmt.setInt(2, wagon.getTraction());
                    pstmt.setInt(3, wagon.getConsumption());
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    Logger.error("Помилка при додаванні в таблицю локомотивів", "");
                }
                break;
            }
            case 2: {
                try (Connection conn = Database.connect();
                     var pstmt = conn.prepareStatement(sql2)) {
                    pstmt.setInt(1, id);
                    pstmt.setInt(2, wagon.getCapacity());
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    Logger.error("Помилка при додаванні в таблицю грузових вагонів", "");
                }
                break;
            }
            case 3: {
                try (Connection conn = Database.connect();
                     var pstmt = conn.prepareStatement(sql2)) {
                    pstmt.setInt(1, id);
                    pstmt.setInt(2, wagon.getCapacity());
                    pstmt.setInt(3, wagon.getComfort());
                    pstmt.setInt(4, wagon.getAmountOfLuggage());
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    Logger.error("Помилка при додаванні в таблицю пасажирських вагонів", "");
                }
                break;
            }
        }
    }

    private static void insertTrain(Train train, int id, String sId) {
        String sql1 = "INSERT INTO trains"+ sId +"(id, name) VALUES(?, ?)";

        try (Connection conn = Database.connect();
             var pstmt = conn.prepareStatement(sql1)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, train.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.error("Помилка при записі в таблицю поїздів", "");
        }
        DBInit.createTableTrain((sId + String.valueOf(id)));
        saveWagons(train.getWagons(), sId + String.valueOf(id));
    }
}
