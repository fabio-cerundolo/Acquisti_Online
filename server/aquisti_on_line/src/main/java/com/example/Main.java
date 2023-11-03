package com.example;

import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AcquistiDAO acquistiDAO = new AcquistiDAO();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Benvenuto nel sistema di gestione acquisti online!");

        System.out.print("Inserisci l'ID del cliente: ");
        int clientId = scanner.nextInt();

        String fullName = acquistiDAO.getFullNameForClientId(clientId);

        if (!fullName.equals("Nome Cognome") && !fullName.equals("Errore")) {
            System.out.println("Nome del cliente: " + fullName);

            // Retrieve and display all products
            List<Prodotto> products = acquistiDAO.getAllProducts();

            if (!products.isEmpty()) {
                System.out.println("Prodotti disponibili:");
                for (Prodotto prodotto : products) {
                    System.out.println("ID Prodotto: " + prodotto.getIdProdotto());
                    System.out.println("Nome: " + prodotto.getNomeProdotto());
                    System.out.println("Prezzo: " + prodotto.getPrezzoProdotto());
                    System.out.println("Quantità: "+prodotto.getQuantitaProdotto());
                    System.out.println("Descrizione:"+prodotto.getDescrizioneProdotto());
                }
            } else {
                System.out.println("Nessun prodotto disponibile.");
            }
        } else {
            System.out.println("Cliente non trovato.");
        }

        acquistiDAO.close();
        scanner.close();
    }
}
