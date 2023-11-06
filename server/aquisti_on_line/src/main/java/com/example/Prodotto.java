package com.example;

public class Prodotto {
    
    private int idProdotto;
    private String nomeProdotto;
    private String descrizioneProdotto;
    private double prezzoProdotto;
    private int quantitaProdotto;

    public Prodotto(int idProdotto,String nomeProdotto, String descrizioneProdotto, double prezzoProdotto, int quantitaProdotto) {
        this.idProdotto=idProdotto;
        this.nomeProdotto=nomeProdotto;
        this.descrizioneProdotto=descrizioneProdotto;
        this.prezzoProdotto=prezzoProdotto;
        this.quantitaProdotto=quantitaProdotto;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public String getDescrizioneProdotto() {
        return descrizioneProdotto;
    }

    public double getPrezzoProdotto() {
        return prezzoProdotto;
    }

    public int getQuantitaProdotto() {
        return quantitaProdotto;
    }

    @Override
    public String toString() {
        return getNomeProdotto() + " " + getDescrizioneProdotto() + " " + getPrezzoProdotto();
    }
}
