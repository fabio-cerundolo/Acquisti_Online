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
                    // System.out.println("ID: " + prodotto.getIdProdotto() + " | "
                    //     + "Nome: " + prodotto.getNomeProdotto() + " | "
                    //     + prodotto.getPrezzoProdotto()+"$ | "
                    //     + "Quantità: "+prodotto.getQuantitaProdotto()+ " | "
                    //     + "Descrizione:"+prodotto.getDescrizioneProdotto() );
                    System.out.format("-%8s %-20s %-10s %-15s %-20s",  "ID: "+ prodotto.getIdProdotto(),
                        "| Nome: " + prodotto.getNomeProdotto(),
                        "| "+ prodotto.getPrezzoProdotto()+"$ ",
                        "| Quantità: "+prodotto.getQuantitaProdotto(),
                        "| Descrizione:"+prodotto.getDescrizioneProdotto() );
                    System.out.println();
                }
                System.out.print("Inserisci l'ID del prodotto da aggiungere al carrello: ");
                int selectedProductId = scanner.nextInt();

                // Find the selected product by ID
                Prodotto selectedProduct = null;
                for (Prodotto prodotto : products) {
                    if (prodotto.getIdProdotto() == selectedProductId) {
                        selectedProduct = prodotto;
                        break;
                    }
                }

                if (selectedProduct != null) {
                    System.out.println("Prodotto aggiunto al carrello.");

                    // Get additional information for the cart
                    System.out.print("Tipo di pagamento: ");
                    String tipoPagamento = scanner.next();

                    // Create a shopping cart and add the selected product
                    Carrello carrello = new Carrello(fullName);
                    carrello.aggiungiProdotto(selectedProduct);

                    // Display the products in the cart
                    System.out.println("Prodotti nel carrello di " + carrello.getClienteNome() + " " + carrello.getClienteCognome() + ":");
                    for (Prodotto prodotto : carrello.getProdottiNelCarrello()) {
                        System.out.println("ID Prodotto: " + prodotto.getIdProdotto());
                        System.out.println("Nome: " + prodotto.getNomeProdotto());
                        System.out.println("Prezzo unitario: " + prodotto.getPrezzoProdotto());
                        System.out.println("Tipo di pagamento: "+tipoPagamento);
                        System.out.println("Totale: " + prodotto.getPrezzoProdotto());
                        System.out.println();
                    }
                } else {
                    System.out.println("Prodotto non trovato.");
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
