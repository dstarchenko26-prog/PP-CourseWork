package ua.nulp.trainmanager.DPL.database;

import ua.nulp.trainmanager.DPL.train.Train;
import ua.nulp.trainmanager.DPL.wagons.Cargo;
import ua.nulp.trainmanager.DPL.wagons.Loc;
import ua.nulp.trainmanager.DPL.wagons.Passengers;
import ua.nulp.trainmanager.DPL.wagons.Wagon;
import ua.nulp.trainmanager.util.Logger;
import ua.nulp.trainmanager.DPL.DPL;

import java.sql.*;

public class DBRead {
    public static Wagon[] readWagons () {
        String sql = "SELECT uid, name, speed, weight, param1, param2, param3 FROM wagons";
        Wagon[] wagons = new Wagon[0];

        try (   Connection conn = Database.connect();
                var stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()) {
                int uid = rs.getInt("uid");
                String name = rs.getString("name");
                int speed = rs.getInt("speed");
                int weight = rs.getInt("weight");
                int p1 = rs.getInt("param1");
                int p2 = rs.getInt("param2");
                int p3 = rs.getInt("param3");
                if (p3 != 0) {
                    wagons = addWagon(wagons, new Passengers(uid, name, speed, weight, p1, p2, p3));
                } else if (p2 != 0) {
                    wagons = addWagon(wagons, new Loc(uid, name, speed, weight, p1, p2));
                } else {
                    wagons = addWagon(wagons, new Cargo(uid, name, speed, weight, p1));
                }
            }
        } catch (SQLException e) {
            Logger.error("Помилка при зчитуванні вагонів", "");
            return null;
        }
        Logger.info("Вагони зчитано");
        return wagons;
    }

    private static Wagon[] addWagon(Wagon[] wagons, Wagon wagon) {
        Wagon[] newWagons = new Wagon[wagons.length + 1];
        for (int i = 0; i < wagons.length; i++) {
            newWagons[i] = wagons[i];
        }
        newWagons[wagons.length] = wagon;
        return newWagons;
    }

    public static Train[] readTrains() {
        String sql =
                "SELECT uid, name FROM trains";

        Train[] trains = new Train[0];

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("uid");
                String name = rs.getString("name");

                Wagon[] wagons = DPL.getWagonsByUid(readTrainsWagons(id));

                trains = addTrain(trains, new Train(id, name, wagons));
            }
        } catch (SQLException e) {
            Logger.error("Помилка при зчитуванні поїздів", "");
        }
        return trains;
    }

    private static int[] readTrainsWagons(int uid) {
        String sql = "SELECT uid_t, uid_w" +
                " FROM trains_wagons" +
                " WHERE uid_t = ?";
        int uids[] = new int[0];

        try (   Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, uid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                uids = addUid(uids, rs.getInt("uid_w"));
            }
        } catch (SQLException e) {
            Logger.error("Помилка при зчитуванні приналежності вагонів", "");
        }
        return  uids;
    }

    private static int[] addUid(int[] uids, int uid) {
        int[] newUids = new int[uids.length + 1];
        for (int i = 0; i < uids.length; i++) {
            newUids[i] = uids[i];
        }
        newUids[uids.length] = uid;
        return newUids;
    }

    private static Train[] addTrain(Train[] trains, Train train) {
        Train[] newTrains = new Train[trains.length + 1];
        for (int i = 0; i < trains.length; i++) {
            newTrains[i] = trains[i];
        }
        newTrains[trains.length] = train;
        return newTrains;
    }
}
