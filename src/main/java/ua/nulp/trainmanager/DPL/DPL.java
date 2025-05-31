package ua.nulp.trainmanager.DPL;

import ua.nulp.trainmanager.DPL.database.DBRead;
import ua.nulp.trainmanager.DPL.params.UsParams;
import ua.nulp.trainmanager.DPL.train.Train;
import ua.nulp.trainmanager.DPL.wagons.Wagon;
import ua.nulp.trainmanager.util.Logger;

public class DPL {
    public static boolean FW = false;
    public static boolean FT = false;
    public static UsParams usParams = new UsParams();
    public static Wagon[] wagons = DBRead.readWagons("");
    public static Train[] trains = DBRead.readTrains("");
    public static int id = 0;

    public static void addWagon(Wagon wagon) {
        Wagon[] newWagons = new Wagon[wagons.length + 1];
        for (int i = 0; i < wagons.length; i++) {
            newWagons[i] = wagons[i];
        }
        newWagons[wagons.length] = wagon;
        wagons = newWagons;
        Logger.info("Додано вагон: " + wagon.getString());
    }

    public static void addTrain(Train train) {
        Train[] newTrains = new Train[trains.length + 1];
        for (int i = 0; i < trains.length; i++) {
            newTrains[i] = trains[i];
        }
        newTrains[trains.length] = train;
        trains = newTrains;
        Logger.info("Додано поїзд: " + train.getString());
    }

    public static void delWagon(int id) {
        Wagon[] newWagons = new Wagon[wagons.length - 1];
        for (int i = 0, j = 0; i < wagons.length; i++) {
            if (i != id) {
                newWagons[j] = wagons[i];
                j++;
            }
        }
        Logger.info("Видалено вагон: " + wagons[id].getString());
        wagons = newWagons;
    }

    public static void delTrain(int id) {
        Train[] newTrains = new Train[trains.length - 1];
        for (int i = 0, j = 0; i < trains.length; i++) {
            if (i != id) {
                newTrains[j] = trains[i];
                j++;
            }
        }
        Logger.info("Видалено поїзд: " + trains[id].getString());
        trains = newTrains;
    }
}
