package com.example.ventevehiculev1.models;

public class User {
    private String id;
    private String nom;
    private String prenom;
    private String password;
    private String mdp;
    private boolean type;
    private boolean abo;

    public User() {
    }

    public User(String id, String nom, String prenom, Boolean type, Boolean abo) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.type = type;
        this.abo = abo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public boolean isAbo() {
        return abo;
    }

    public void setAbo(boolean abo) {
        this.abo = abo;
    }
}
