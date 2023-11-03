package com.example;

import java.util.Scanner;

public class Main {
        public static void main(String[] args) {
        LoginManager loginManager = new LoginManager();
        Scanner scanner = new Scanner(System.in);
       // int scelta = scanner.nextInt();

        int clientId = -1;
        while (clientId == -1) {
            clientId = loginManager.login(scanner);
            if (clientId == -1) {
                System.out.println("ID del cliente non valido o inesistente. Riprova.");
            }
        }

        String fullName = loginManager.getFullName(clientId);
        System.out.println("Benvenuto, " + fullName);
        // Chiudi la connessione al database
        loginManager.close();
        scanner.close();
    }
}
