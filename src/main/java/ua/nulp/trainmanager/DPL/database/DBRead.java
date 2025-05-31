package ua.nulp.trainmanager.DPL.database;

import ua.nulp.trainmanager.DPL.train.Train;
import ua.nulp.trainmanager.DPL.wagons.Cargo;
import ua.nulp.trainmanager.DPL.wagons.Loc;
import ua.nulp.trainmanager.DPL.wagons.Passengers;
import ua.nulp.trainmanager.DPL.wagons.Wagon;
import ua.nulp.trainmanager.util.Logger;

import java.sql.*;

public class DBRead {
    public static Wagon[] readWagons (String sId) {
        String sql1 =
                "SELECT wag.id, wag.name, wag.speed, wag.weight, l.traction, l.consumption, wag.type" +
                " FROM wagons" + sId + " AS wag" +
                " JOIN loc" + sId + " AS l ON wag.id = l.id" +
                " WHERE wag.type = ?";

        String sql2 =
                "SELECT wag.id, wag.name, wag.speed, wag.weight, c.capacity, wag.type" +
                " FROM wagons" + sId + " AS wag" +
                " JOIN cargo" + sId + " AS c ON wag.id = c.id" +
                " WHERE wag.type = ?";

        String sql3 =
                "SELECT wag.id, wag.name, wag.speed, wag.weight, pass.capacity, pass.comfort, pass.amountOfLuggage, wag.type" +
                " FROM wagons" + sId + " AS wag" +
                " JOIN passengers" + sId + " AS pass ON wag.id = pass.id" +
                " WHERE wag.type = ?";

        Wagon[] wagons = new Wagon[0];

        try (   Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql1)) {
            pstmt.setInt(1, 1);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                wagons = addWagon(wagons,new Loc(rs.getString("name"), rs.getInt("speed"), rs.getInt("weight"), rs.getInt("traction"), rs.getInt("consumption")));
            }
        } catch (SQLException e) {
            Logger.error("Помилка при зчитуванні локомотивів", "");
        }

        try (   Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql2)) {
            pstmt.setInt(1, 2);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                wagons = addWagon(wagons, new Cargo(rs.getString("name"), rs.getInt("speed"), rs.getInt("weight"), rs.getInt("capacity")));
            }
        } catch (SQLException e) {
            Logger.error("Помилка при зчитуванні вантажних вагонів", "");
        }

        try (   Connection conn = Database.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql3)) {
            pstmt.setInt(1, 3);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                wagons = addWagon(wagons, new Passengers(rs.getString("name"), rs.getInt("speed"), rs.getInt("weight"), rs.getInt("capacity"), rs.getInt("comfort"), rs.getInt("amountOfLuggage")));
            }
        } catch (SQLException e) {
            Logger.error("Помилка при зчитуванні пасажирських вагонів", "");
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

    public static Train[] readTrains(String sId) {
        String sql =
                "SELECT id, name FROM trains" + sId;

        Train[] trains = new Train[0];

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                Wagon[] wagons = readWagons(sId + String.valueOf(id));

                trains = addTrain(trains, new Train(name, wagons));
            }
        } catch (SQLException e) {
            Logger.error("Помилка при зчитуванні поїздів", "");
        }
        return trains;
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
