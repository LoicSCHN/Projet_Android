package com.example.ventevehiculev1;

public class Voiture {

    private String marque;
    private String modele;
    private String categorie;
    private String energie;
    private String kilometrage;
    private String boiteVitesse;
    private String nbPortes;
    private String puissance;

    public Voiture(String marque, String modele, String categorie, String energie, String kilometrage, String boiteVitesse, String nbPortes, String puissance) {

        this.marque = marque;
        this.modele = modele;
        this.categorie = categorie;
        this.energie = energie;
        this.kilometrage = kilometrage;
        this.boiteVitesse = boiteVitesse;
        this.nbPortes = nbPortes;
        this.puissance = puissance;
    }



    public Voiture(){}

    public String getMarque() {
        return marque;
    }

    public String getModele() {
        return modele;
    }

    public String getBoiteVitesse() {
        return boiteVitesse;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getKilometrage() {
        return kilometrage;
    }

    public String getEnergie() {
        return energie;
    }

    public String getNbPortes() {
        return nbPortes;
    }

    public String getPuissance() {
        return puissance;
    }

    public void setBoiteVitesse(String boiteVitesse) {
        this.boiteVitesse = boiteVitesse;
    }

    public void setKilometrage(String kilometrage) {
        this.kilometrage = kilometrage;
    }

    public void setNbPortes(String nbPortes) {
        this.nbPortes = nbPortes;
    }

    public void setPuissance(String puissance) {
        this.puissance = puissance;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setEnergie(String energie) {
        this.energie = energie;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }
}
