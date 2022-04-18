package com.example.ventevehiculev1;

public class Annonce {
    /*
    private int id;
    private int id_User;
    private int id_Voiture;
    private int prix;
    private int id_Localisation;
    */
    //private String id;
    private String title;
    //private Voiture voiture;

    public Annonce(String title){
        this.title=title;
    }
    public Annonce(){

    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
