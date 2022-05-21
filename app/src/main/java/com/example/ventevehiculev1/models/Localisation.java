package com.example.ventevehiculev1.models;

public class Localisation {
    public int id;
    public String region;
    public String departement;
    public String ville;
    public double codePostal;


    public Localisation(int id, String region, String departement, String ville, double codePostal) {
        this.id = id;
        this.region = region;
        this.departement = departement;
        this.ville = ville;
        this.codePostal = codePostal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public double getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(double codePostal) {
        this.codePostal = codePostal;
    }
}
