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
}
