package com.example.ventevehiculev1.models;

public class Date {
    public int id;
    public String jourDebut;
    public String jourFin;

    public Date(int id,String jourDebut, String jourFin) {
        this.id = id;
        this.jourDebut = jourDebut;
        this.jourFin = jourFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJourDebut() {
        return jourDebut;
    }

    public void setJourDebut(String jourDebut) {
        this.jourDebut = jourDebut;
    }

    public String getJourFin() {
        return jourFin;
    }

    public void setJourFin(String jourFin) {
        this.jourFin = jourFin;
    }
}
