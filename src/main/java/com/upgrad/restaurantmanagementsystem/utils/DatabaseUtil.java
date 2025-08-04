package com.upgrad.restaurantmanagementsystem.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {

    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;

    static {
        try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input != null) {
                prop.load(input);
                URL = prop.getProperty("db.url");
                USERNAME = prop.getProperty("db.username");
                PASSWORD = prop.getProperty("db.password");
            } else {
                throw new RuntimeException("Unable to find application.properties");
            }
            Class.forName("org.postgresql.Driver");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}