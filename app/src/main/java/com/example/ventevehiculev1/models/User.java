package com.example.ventevehiculev1.models;

public class User {
    private int id;
    private String nom;
    private String prenom;
    private String password;
    private String mdp;
    private String type;

    public User(int id, String nom, String prenom, String password, String mdp, String type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.mdp = mdp;
        this.type = type;
    }
}
