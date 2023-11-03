package com.example;

import java.util.Arrays;
import java.util.List;
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
            System.out.println("Nome del cliente: " + fullName);

            List<Prodotto> products = acquistiDAO.getAllProducts();

            if (!products.isEmpty()) {
                System.out.println("Prodotti disponibili:");
                for (Prodotto prodotto : products) {
                    System.out.printf("%-10s %-20s %-15s", "ID: " + prodotto.getIdProdotto(),
                    "|  Nome: " + prodotto.getNomeProdotto(),
                    "|  Prezzo: " + prodotto.getPrezzoProdotto());
                    System.out.println();
                }

                Carrello carrello = new Carrello(fullName);

                boolean addToCart = true;

                while (addToCart) {
                    System.out.print("Inserisci l'ID del prodotto da aggiungere al carrello (oppure 'P' per procedere al pagamento): ");
                    String userInput = scanner.next();

                    if (userInput.equalsIgnoreCase("P")) {
                        addToCart = false;
                    } else {
                        int selectedProductId = Integer.parseInt(userInput);
                        Prodotto selectedProduct = findProductById(products, selectedProductId);

                        if (selectedProduct != null) {
                            carrello.aggiungiProdotto(selectedProduct);
                            System.out.println("Prodotto " + selectedProduct.toString() + " aggiunto al carrello.");
                        } else {
                            System.out.println("Prodotto non trovato.");
                        }
                    }
                }
// Prompt the user to enter the payment type
                        System.out.print("Inserisci il tipo di pagamento: ");
                        String tipoPagamento = scanner.next();
                        carrello.setTipoPagamento(tipoPagamento);
                // Display the products in the cart and calculate the total
                double total = 0.0;
                System.out.println("Prodotti nel carrello di " + fullName + ":");
                for (Prodotto prodotto : carrello.getProdottiNelCarrello()) {
                    System.out.println("ID Prodotto: " + prodotto.getIdProdotto());
                    System.out.println("Nome: " + prodotto.getNomeProdotto());
                    System.out.println("Prezzo unitario: " + prodotto.getPrezzoProdotto());
                    System.out.println("Tipo di pagamento: " + carrello.getTipoPagamento());
                    double prezzoProdotto = prodotto.getPrezzoProdotto();
                    System.out.println("Totale parziale: " + prezzoProdotto);
                    System.out.println();
                    total += prezzoProdotto;
                }
                System.out.println("Totale: " + total + " " + carrello.getTipoPagamento());
            } else {
                System.out.println("Nessun prodotto disponibile.");
            }
        } else {
            System.out.println("Cliente non trovato.");
        }

        acquistiDAO.close();
        scanner.close();
    }

    private static Prodotto findProductById(List<Prodotto> products, int productId) {
        for (Prodotto prodotto : products) {
            if (prodotto.getIdProdotto() == productId) {
                return prodotto;
            }
        }
        return null;
    }
}
