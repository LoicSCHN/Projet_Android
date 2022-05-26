package com.example.ventevehiculev1.models;

import java.util.ArrayList;

public class Annonce {
    /*
    private int id;
    private int id_User;
    private int id_Voiture;
    private int prix;
    private int id_Localisation;
    */
    private String id;
    private String title;
    private String id_proprietaire;
    private Voiture voiture;
    private ArrayList<String> photo;
    private String type;
    private double prix;

    public Annonce(String id,String title,String id_proprietaire,ArrayList<String> photo,Voiture voiture){
        this.id = id;
        this.title=title;
        this.id_proprietaire = id_proprietaire;
        this.photo = photo;
        this.voiture = voiture;
    }
    public Annonce(){

    }
    public ArrayList<String> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<String> photo) {
        this.photo = photo;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_proprietaire() {
        return id_proprietaire;
    }

    public void setId_proprietaire(String id_proprietaire) {
        this.id_proprietaire = id_proprietaire;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Voiture getVoiture(){return voiture;}

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }
    @Override
    public String toString() {
        return "Annonce{" +
                "id='" + id + '\'' +
                ", nom='" + title + '\'' +
                '}';
    }
}

