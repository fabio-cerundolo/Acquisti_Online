package com.example;

import java.util.ArrayList;
import java.util.List;

public class Carrello {
    private List<Prodotto> selectedProducts;
    private String clienteNome;
    private String clienteCognome;
    private String tipoPagamento;

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Carrello(String tipoPagamento) {
        selectedProducts = new ArrayList<>();
        this.tipoPagamento = tipoPagamento;
    }

    public void aggiungiProdotto(Prodotto prodotto) {
        selectedProducts.add(prodotto);
    }

    public void rimuoviProdotto(Prodotto prodotto) {
        selectedProducts.remove(prodotto);
    }

    public List<Prodotto> getProdottiNelCarrello() {
        return selectedProducts;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public String getClienteCognome() {
        return clienteCognome;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }
}
