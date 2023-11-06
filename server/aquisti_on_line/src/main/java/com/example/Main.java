package com.example;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        AcquistiDAO acquistiDAO = new AcquistiDAO();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Benvenuto nel sistema di gestione acquisti online!");

        int clientId = getClientId(scanner, acquistiDAO);

        if (clientId != -1) {
            String fullName = acquistiDAO.getFullNameForClientId(clientId);
            System.out.println("Nome del cliente: " + fullName);

            List<Prodotto> products = acquistiDAO.getAllProducts();

            if (!products.isEmpty()) {
                Stack<Menu> menuStack = new Stack<>();
                menuStack.push(new MainMenu(scanner, products));

                while (!menuStack.isEmpty()) {
                    Menu currentMenu = menuStack.peek();
                    int choice = currentMenu.displayMenuAndGetChoice();

                    if (choice == 0) {
                        // Pop the current menu and go back
                        menuStack.pop();
                    } else {
                        // Execute the chosen option
                        currentMenu.handleChoice(choice, menuStack, products, fullName);
                    }
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

    private static int getClientId(Scanner scanner, AcquistiDAO acquistiDAO) {
        int clientId = -1;
        while (clientId == -1) {
            System.out.print("Inserisci l'ID del cliente: ");
            clientId = scanner.nextInt();
            if (!acquistiDAO.isValidClientId(clientId)) {
                System.out.println("ID del cliente non valido. Riprova.");
                clientId = -1;
            }
        }
        return clientId;
    }
}

abstract class Menu {
    protected static String title;
    protected static List<String> options;
    protected Scanner scanner;
    protected Carrello cart;
    public Menu(Scanner scanner, String title, List<String> options) {
        Menu.title = title;
        Menu.options = options;
        this.scanner = scanner;
        this.cart = new Carrello(title);
        this.scanner = scanner;
    }

    public int displayMenuAndGetChoice() {
        System.out.println(title);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.println("0. Torna indietro");
        System.out.print("Scelta: ");
        return scanner.nextInt();
    }

    public abstract void handleChoice(int choice, Stack<Menu> menuStack, List<Prodotto> products, String fullName);
}

class MainMenu extends Menu {
    private List<Prodotto> products;

    public MainMenu(Scanner scanner, List<Prodotto> products) {
        super(scanner, "Menu Principale", List.of(
            "Visualizza Prodotti",
            "Carrello",
            "Effettua Pagamento"
        ));
        this.products = products;
    }

    @Override
    public void handleChoice(int choice, Stack<Menu> menuStack, List<Prodotto> products, String fullName) {
        switch (choice) {
            case 1:
                menuStack.push(new ProductsMenu(scanner, this.products));
                break;
            case 2:  // Opzione "Visualizza Carrello"
                CartMenu cartMenu = new CartMenu(scanner, fullName, cart, products);
                menuStack.push(cartMenu);
                break;



                
            case 3:
                menuStack.push(new PaymentMenu(scanner, fullName));
                break;
            default:
                System.out.println("Scelta non valida. Riprova.");
                break;
        }
    }
}

class ProductsMenu extends Menu {
    private List<Prodotto> products;

    public ProductsMenu(Scanner scanner, List<Prodotto> products) {
        super(scanner, title, options);
        this.products = products;
    }

    @Override
    public int displayMenuAndGetChoice() {
        System.out.println(title);
        for (int i = 0; i < products.size(); i++) {
            Prodotto product = products.get(i);
            System.out.println((i + 1) + ". ID Prodotto: " + product.getIdProdotto());
            System.out.println("   Nome: " + product.getNomeProdotto());
            System.out.println("   Prezzo: " + product.getPrezzoProdotto());
        }
        System.out.println("0. Torna indietro");
        System.out.print("Scelta: ");
        return scanner.nextInt();
    }

    @Override
    public void handleChoice(int choice, Stack<Menu> menuStack, List<Prodotto> products, String fullName) {
        if (choice == 0) {
            // Torna indietro al menu principale
            menuStack.pop();
        } else if (choice > 0 && choice <= this.products.size()) {
            // L'utente ha selezionato un prodotto
            Prodotto selectedProduct = products.get(choice - 1);

            // Esempio: aggiungi il prodotto al carrello
            cart.aggiungiProdotto(selectedProduct);
            System.out.println("Prodotto aggiunto al carrello.");
        } else {
            System.out.println("Scelta non valida. Riprova.");
        }
    }
}





class CartMenu extends Menu {
    public CartMenu(Scanner scanner, String fullName, Carrello cart, List<Prodotto> products) {
        super(scanner, "Carrello di " + fullName, List.of(
            "Visualizza Carrello",
            "Aggiungi Prodotto al Carrello"
        ));
        this.cart = cart;
    }

    @Override
    public void handleChoice(int choice, Stack<Menu> menuStack, List<Prodotto> products, String fullName) {
        switch (choice) {
            case 1:
    // Implementa la logica per visualizzare il contenuto del carrello
    List<Prodotto> prodottiNelCarrello = cart.getProdottiNelCarrello();

    if (prodottiNelCarrello.isEmpty()) {
        System.out.println("Il carrello è vuoto.");
    } else {
        System.out.println("Prodotti nel carrello di " + fullName + ":");
        for (Prodotto prodotto : prodottiNelCarrello) {
            System.out.println("ID Prodotto: " + prodotto.getIdProdotto());
            System.out.println("Nome: " + prodotto.getNomeProdotto());
            System.out.println("Prezzo unitario: " + prodotto.getPrezzoProdotto());
            System.out.println("Tipo di pagamento: " + cart.getTipoPagamento());
            double prezzoProdotto = prodotto.getPrezzoProdotto();
            System.out.println("Totale parziale: " + prezzoProdotto);
            System.out.println();
        }
    }
    break;

            case 2:
                // Implementa la logica per aggiungere prodotti al carrello
                System.out.print("Inserisci l'ID del prodotto da aggiungere al carrello: ");
                displayProducts(products);
                int selectedProductId = scanner.nextInt();
                Prodotto selectedProduct = findProductById(products, selectedProductId);

                if (selectedProduct != null) {
                    cart.aggiungiProdotto(selectedProduct);
                    System.out.println("Prodotto aggiunto al carrello.");
                } else {
                    System.out.println("Prodotto non trovato.");
                }
                break;
            case 0:
                // Torna indietro al menu precedente (MainMenu)
                menuStack.pop();
                break;
            default:
                System.out.println("Scelta non valida. Riprova.");
                break;
        }
    }
        private void displayProducts(List<Prodotto> products) {
        System.out.println("Prodotti disponibili:");
        for (Prodotto prodotto : products) {
            System.out.println("ID Prodotto: " + prodotto.getIdProdotto());
            System.out.println("Nome: " + prodotto.getNomeProdotto());
            System.out.println("Prezzo: " + prodotto.getPrezzoProdotto());
            System.out.println();
        }
    }

    private Prodotto findProductById(List<Prodotto> products, int productId) {
        for (Prodotto prodotto : products) {
            if (prodotto.getIdProdotto() == productId) {
                return prodotto;
            }
        }
        return null;
    }
}


class PaymentMenu extends Menu {
    public PaymentMenu(Scanner scanner, String fullName) {
        super(scanner, "Effettua Pagamento per " + fullName, List.of(
            "Tipo di Pagamento",
            "Conferma Pagamento"
        ));
    }

    @Override
    public void handleChoice(int choice, Stack<Menu> menuStack, List<Prodotto> products, String fullName) {
        switch (choice) {
            case 1:
                // Implement logic to set payment type
                System.out.print("Inserisci il tipo di pagamento: ");
                String tipoPagamento = scanner.next();
                cart.setTipoPagamento(tipoPagamento);
                System.out.println("Tipo di pagamento impostato a: " + tipoPagamento);
                break;
            case 2:
                // Implement logic to confirm and process payment
                double total = 0.0;
                System.out.println("Prodotti nel carrello di " + fullName + ":");
                for (Prodotto prodotto : cart.getProdottiNelCarrello()) {
                    System.out.println("ID Prodotto: " + prodotto.getIdProdotto());
                    System.out.println("Nome: " + prodotto.getNomeProdotto());
                    System.out.println("Prezzo unitario: " + prodotto.getPrezzoProdotto());
                    System.out.println("Tipo di pagamento: " + cart.getTipoPagamento());
                    double prezzoProdotto = prodotto.getPrezzoProdotto();
                    System.out.println("Totale parziale: " + prezzoProdotto);
                    System.out.println();
                    total += prezzoProdotto;
                }
                System.out.println("Totale: " + total + " " + cart.getTipoPagamento());
                // Implementa qui la logica per confermare e processare il pagamento
                break;
            default:
                System.out.println("Scelta non valida. Riprova.");
                break;
        }
    }
}

