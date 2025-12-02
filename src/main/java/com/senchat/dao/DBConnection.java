package com.senchat.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/senchat";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex){
            ex.printStackTrace(); // mostra a causa real
            throw new RuntimeException("Error connection DB");
        }
    }

}
