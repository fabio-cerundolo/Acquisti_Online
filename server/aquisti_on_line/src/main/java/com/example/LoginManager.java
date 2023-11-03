package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginManager {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/acquistionline?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private Connection connection;

    public LoginManager() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int login(Scanner scanner) {
        System.out.print("Inserisci l'ID del cliente: ");
        int clientId = scanner.nextInt();
        
        String selectQuery = "SELECT id_cliente FROM clienti WHERE id_cliente = ?";
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id_cliente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    

    public String getFullName(int clientId) {
        String selectQuery = "SELECT nome, cognome FROM clienti WHERE id_cliente = ?";
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String cognome = resultSet.getString("cognome");
                return nome + " " + cognome;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Nome Cognome";
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
