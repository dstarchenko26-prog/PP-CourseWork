package ua.nulp.trainmanager.DPL;

import ua.nulp.trainmanager.DPL.database.DBDelete;
import ua.nulp.trainmanager.DPL.database.DBRead;
import ua.nulp.trainmanager.DPL.database.DBWrite;
import ua.nulp.trainmanager.DPL.params.UsParams;
import ua.nulp.trainmanager.DPL.train.Train;
import ua.nulp.trainmanager.DPL.wagons.Cargo;
import ua.nulp.trainmanager.DPL.wagons.Loc;
import ua.nulp.trainmanager.DPL.wagons.Passengers;
import ua.nulp.trainmanager.DPL.wagons.Wagon;
import ua.nulp.trainmanager.util.Logger;

public class DPL {
    public static boolean FW = false;
    public static boolean FT = false;
    public static UsParams usParams = new UsParams();
    public static Wagon[] wagons = DBRead.readWagons();
    public static Train[] trains = DBRead.readTrains();
    public static int id = 0;

    public static void addWagon(String name, int speed, int weight, int param1, int param2, int param3) {
        Wagon[] newWagons = new Wagon[wagons.length + 1];
        for (int i = 0; i < wagons.length; i++) {
            newWagons[i] = wagons[i];
        }
        int uid = DBWrite.insertWagon(name, speed, weight, param1, param2, param3);
        Wagon wagon = null;
        if (param3 != 0) {
            wagon = new Passengers(uid, name, speed, weight, param1, param2, param3);
        } else if (param2 != 0) {
            wagon = new Loc(uid, name, speed, weight, param1, param2);
        } else {
            wagon = new Cargo(uid, name, speed, weight, param1);
        }
        newWagons[wagons.length] = wagon;
        wagons = newWagons;
        Logger.info("Додано вагон: " + wagon.getString());
    }

    public static Wagon[] getWagonsByUid(int[] uids) {
        Wagon[] rsWagons = new Wagon[uids.length];
        for (int i = 0; i < uids.length; i++) {
            for (int j = 0; j < wagons.length; j++) {
                if (uids[i] == wagons[j].getUid()) {
                    rsWagons[i] = wagons[j];
                    break;
                }
            }
        }
        return rsWagons;
    }

    public static boolean delWagon(int uid) {
        int pointer_index = -1;
        for (int i = 0; i < wagons.length; i++) {
            if (wagons[i].getUid() == uid) {
                pointer_index = i;
                break;
            }
        }

        if (pointer_index == -1) {
            return false;
        }

        if (DBDelete.delWagon(uid)) {
            Wagon[] newWagons = new Wagon[wagons.length - 1];
            for (int i = 0, j = 0; i < wagons.length; i++) {
                if (i != pointer_index) {
                    newWagons[j] = wagons[i];
                    j++;
                }
            }
            Logger.info("Видалено вагон: " + wagons[pointer_index].getString());
            wagons = newWagons;
            return true;
        }
        return false;
    }

    public static void addTrain(String name) {
        Train[] newTrains = new Train[trains.length + 1];
        for (int i = 0; i < trains.length; i++) {
            newTrains[i] = trains[i];
        }
        int uid = DBWrite.insertTrain(name);
        Train train = new Train(uid, name);
        newTrains[trains.length] = train;
        trains = newTrains;
        Logger.info("Додано поїзд: " + train.getString());
    }

    public static int getTrainId(int uid) {
        int i = 0;
        for (; i < trains.length; i++) {
            if (trains[i].getUid() == uid) {
                break;
            }
        }
        return i;
    }

    public static void delTrain(int uid) {
        int pointer_index = getTrainId(uid);

        DBDelete.delTrain(uid);
        Train[] newTrains = new Train[trains.length - 1];
        for (int i = 0, j = 0; i < trains.length; i++) {
            if (i != pointer_index) {
                newTrains[j] = trains[i];
                j++;
            }
        }
        Logger.info("Видалено поїзд: " + trains[pointer_index].getString());
        trains = newTrains;
    }
}
