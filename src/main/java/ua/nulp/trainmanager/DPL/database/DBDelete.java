package ua.nulp.trainmanager.DPL.database;

import ua.nulp.trainmanager.util.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBDelete {
    public static boolean delWagon(int uid_w) {
        String sql1 = "SELECT * FROM trains_wagons WHERE uid_w = ?";

        try (Connection conn1 = Database.connect();
             PreparedStatement pstmt1 = conn1.prepareStatement(sql1)){
            pstmt1.setInt(1, uid_w);
            ResultSet rs = pstmt1.executeQuery();
            while (rs.next()) {
                return false;
            }

            String sql2 = "DELETE FROM wagons WHERE uid = ?";
            try (Connection conn2 = Database.connect();
                PreparedStatement pstmt2 = conn2.prepareStatement(sql2)) {
                pstmt2.setInt(1, uid_w);
                pstmt2.executeUpdate();
                Logger.info("Видалення вагона з бд пройшло успішно");
                return true;
            } catch (SQLException e) {
                Logger.error("Помилка при видаленні вагона з бази даних", "");
            }
        } catch (SQLException e) {
            Logger.error("Помилка при перевірці умови для видалення вагона", "");
        }
        return false;
    }

    public static void delTrain(int uid_t) {
        String sql1 = "DELETE FROM trains_wagons WHERE uid_t = ?";
        String sql2 = "DELETE FROM trains WHERE uid = ?";
        try (Connection conn = Database.connect();
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt1.setInt(1, uid_t);
            pstmt2.setInt(1, uid_t);
            pstmt1.executeUpdate();
            pstmt2.executeUpdate();
            Logger.info("Видалення поїзда із залежностями з бд пройшло успішно");
        } catch (SQLException e) {
            Logger.error("Помилка при видаленні поїзда з бази даних", "");
        }
    }

    public static void delTrainWagon(int uid_t, int uid_w) {
        String sql1 = "SELECT id_connect, uid_t, uid_w FROM trains_wagons WHERE uid_t = ? AND uid_w = ?";
        String sql2 = "DELETE FROM trains_wagons WHERE id_connect = ?";

        try (Connection conn1 = Database.connect();
             PreparedStatement pstmt1 = conn1.prepareStatement(sql1);
             PreparedStatement pstmt2 = conn1.prepareStatement(sql2)) {
            pstmt1.setInt(1, uid_t);
            pstmt1.setInt(2, uid_w);
            ResultSet rs = pstmt1.executeQuery();
            while (rs.next()) {
                pstmt2.setInt(1, rs.getInt("id_connect"));
                pstmt2.executeUpdate();
                Logger.info("Залежність між поїздом і вагоном видалена");
                return;
            }
        } catch (SQLException e) {
            Logger.error("Помилка при видаленні приналежності вагона", "");
        }
    }
}
