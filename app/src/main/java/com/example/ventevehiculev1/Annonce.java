package com.example.ventevehiculev1;

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
    //private Voiture voiture;

    public Annonce(String id,String title){
        this.id = id;
        this.title=title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
