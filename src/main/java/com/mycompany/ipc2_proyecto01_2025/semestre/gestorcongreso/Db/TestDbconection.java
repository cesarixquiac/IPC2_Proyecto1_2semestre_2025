/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ipc2_proyecto01_2025.semestre.gestorcongreso.Db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author cesar
 */
public class TestDbconection {
    public static void main(String[] args) {
        try {
            // Conexión
            Connection con = DatabaseConnectionSingleStone.getInstance().getConnection();
            
            if(con != null) {
                System.out.println("Conexión exitosa a la base de datos!");

                //Consulta simple
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT id, nombre_completo, correo FROM usuario LIMIT 5");

                System.out.println("Usuarios existentes:");
                while(rs.next()) {
                    System.out.println(
                        "ID: " + rs.getInt("id") +
                        " | Nombre: " + rs.getString("nombre_completo") +
                        " | Correo: " + rs.getString("correo")
                    );
                }
            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
