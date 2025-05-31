package ua.nulp.trainmanager.DPL.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String PATH = start();
    private static final String DB_URL = "jdbc:sqlite:" + PATH + "data/app.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private static String start() {
        String path = System.getProperty("PATH_TO_DB");
        if (path == null) {
            path = "src/main/resources/ua/nulp/trainmanager/";
        }
        return path;
    }
}
