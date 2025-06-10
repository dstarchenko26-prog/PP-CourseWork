package ua.nulp.trainmanager.DPL.database;

import ua.nulp.trainmanager.util.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBWrite {
    public static int insertWagon(String name, int speed, int weight, int param1, int param2, int param3) {
        String sql = "INSERT INTO wagons(name, speed, weight, param1, param2, param3) VALUES(?, ?, ?, ?, ?, ?)";
        int id = -1;

        try (Connection conn = Database.connect();
             var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, speed);
            pstmt.setInt(3, weight);
            pstmt.setInt(4, param1);
            pstmt.setInt(5, param2);
            pstmt.setInt(6, param3);
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                     id = generatedKeys.getInt(1);
                } else {
                    Logger.error("При додаванні вагона не вдалось повернути uid", "");
                }
            }
        } catch (SQLException e) {
            Logger.error("Помилка при додавані в таблицю вагонів", "");
        }
        return id;
    }

    public static int insertTrain(String name) {
        String sql = "INSERT INTO trains(name) VALUES(?)";
        int id = -1;
        try (Connection conn = Database.connect();
             var pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    Logger.error("При додаванні поїзда не вдалось повернути uid", "");
                }
            }
        } catch (SQLException e) {
            Logger.error("Помилка при записі в таблицю поїздів", "");
        }
        return id;
    }

    public static void insertTrainWagons(int uid_t, int uid_w) {
        String sql = "INSERT INTO trains_wagons(uid_t, uid_w) VALUES(?, ?)";
        try (Connection conn = Database.connect();
            var pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, uid_t);
            pstmt.setInt(2, uid_w);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.error("Помилка при записі в таблицю приналежності вагонів", "");
        }
    }
}
