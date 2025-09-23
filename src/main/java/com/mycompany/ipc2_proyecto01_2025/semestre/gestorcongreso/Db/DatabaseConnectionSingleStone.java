/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2_proyecto01_2025.semestre.gestorcongreso.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author cesar
 */
public class DatabaseConnectionSingleStone {
    
    private static final String IP = "localhost";
    private static final int PUERTO = 3310;
    private static final String SCHEMA = "gestor_congreso";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + SCHEMA;
    
    private static  DatabaseConnectionSingleStone instance;

    private Connection connection;
    
    private DatabaseConnectionSingleStone() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            
            System.out.println("Error al conectarse");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DatabaseConnectionSingleStone getInstance() throws ClassNotFoundException {
        if (instance == null) {
            instance = new DatabaseConnectionSingleStone();
        }
        return instance;
    }



    
}
