package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AcquistiDAO acquistiDAO = new AcquistiDAO();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Benvenuto nel sistema di gestione acquisti online!");

        System.out.print("Inserisci l'ID del cliente: ");
        int clientId = scanner.nextInt();

        String fullName = acquistiDAO.getFullNameForClientId(clientId);
        if (!fullName.equals("Nome Cognome") && !fullName.equals("Errore")) {
            System.out.println("Benvenuto," + fullName);
        } else {
            System.out.println("Cliente non trovato.");
        }

        acquistiDAO.close();
        scanner.close();
    }
}
