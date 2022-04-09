package com.example.ventevehiculev1;

public class Voiture {
    private int id;
    private String marque;
    private String modele;
    private String categorie;
    private String energie;
    private double kilometrage;
    private String boiteVitesse;
    private int nbPortes;
    private int puissance;
    private int CV;


    public Voiture(int id, String marque, String modele, String categorie, String energie, double kilometrage, String boiteVitesse, int nbPortes, int puissance, int CV) {
        this.id = id;
        this.marque = marque;
        this.modele = modele;
        this.categorie = categorie;
        this.energie = energie;
        this.kilometrage = kilometrage;
        this.boiteVitesse = boiteVitesse;
        this.nbPortes = nbPortes;
        this.puissance = puissance;
        this.CV = CV;
    }
}
