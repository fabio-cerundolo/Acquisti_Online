package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AcquistiDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/acquistionline";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private Connection connection;

    public AcquistiDAO() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getFullNameForClientId(int clientId) {
        String selectQuery = "SELECT nome, cognome FROM clienti WHERE id_cliente = ?";
        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setInt(1, clientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String cognome = resultSet.getString("cognome");
                return nome + " " + cognome;
            } else {
                return "Nome Cognome";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Errore";
        }
    }

    /*public List<Acquisto> getAcquistiByClientId(int clientId) {
        // Implement logic to retrieve purchases by client ID
        // Replace Acquisto with your actual class representing purchases
        return new ArrayList<>();
    }

    public void saveAcquisto(Acquisto acquisto) {
        // Implement logic to save a purchase
        // Replace Acquisto with your actual class representing purchases
    }

    // Other placeholder methods

    public List<Prodotto> getAllProducts() {
        // Implement logic to retrieve all products
        // Replace Prodotto with your actual class representing products
        return new ArrayList<>();
    }

    public void saveProdotto(Prodotto prodotto) {
        // Implement logic to save a product
        // Replace Prodotto with your actual class representing products
    }

    public void updateProdotto(Prodotto prodotto) {
        // Implement logic to update a product
        // Replace Prodotto with your actual class representing products
    }

    public void deleteProdotto(int productId) {
        // Implement logic to delete a product by ID
    }*/

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
