package com.esprit.utils;

import java.sql.*;

public class DataSource {

    private Connection connection;
    private static DataSource instance;

    private final String URL = "jdbc:mysql://localhost:3306/esprit";
    private final String USER = "root";
    private  final String PASSWORD = "";

    private DataSource() {
        try {
<<<<<<< HEAD
            connection = DriverManager.getConnection(URL, USER, PASSWORD); // connexion au base
=======
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
>>>>>>> 2a5c9c04de20e210453822606aa0fa2e3fa3f9d9
            System.out.println("Connection a été établie");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

<<<<<<< HEAD
    public static DataSource getInstance() { // un seul canal de communication
=======
    public static DataSource getInstance() {
>>>>>>> 2a5c9c04de20e210453822606aa0fa2e3fa3f9d9
        if(instance == null){
            instance = new DataSource();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
