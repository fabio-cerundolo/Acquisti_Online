package com.example;

public class Cliente {

    private int id;
    private String nome;
    private String cognome;


    public Cliente (String nome, String cognome) {
        this.nome=nome;
        this.cognome=cognome;
    }

    public Cliente() {
    }

    public int getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String string) {
    }

    public void setNome(String string) {
    }
}