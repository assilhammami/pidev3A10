package com.esprit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private Connection connection;
    private static com.esprit.utils.DataSource instance;

    private final String URL = "jdbc:mysql://localhost:3306/pidev";
    private final String USER = "root";
    private  final String PASSWORD = "";

    private DataSource() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection a été établie");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static com.esprit.utils.DataSource getInstance() {
        if(instance == null){
            instance = new com.esprit.utils.DataSource();
        }
        return instance;
    }
    public Connection getConnection() {
        return connection;
    }
}
