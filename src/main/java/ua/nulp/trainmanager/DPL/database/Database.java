package ua.nulp.trainmanager.DPL.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String DEFAULT_PATH = "src/main/resources/ua/nulp/trainmanager/";
    public static String DB_NAME = "app.db";
    private static final String PATH = start();
    private static final String DB_URL = "jdbc:sqlite:" + PATH + "data/";


    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL + DB_NAME);
    }

    private static String start() {
        String path = System.getProperty("PATH_TO_DB");
        if (path == null) {
            path = DEFAULT_PATH;
        }
        return path;
    }
}
