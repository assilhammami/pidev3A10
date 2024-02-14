package com.esprit.models;

public class Reservation {

    private int id;
    private String date;
    private String statut;
    private int nbplaces;

    public Reservation(int id, String date, String statut, int nbplaces) {
        this.id = id;
        this.date = date;
        this.statut = statut;
        this.nbplaces = nbplaces;
    }

    public Reservation(String date, String statut, int nbplaces) {
        this.date = date;
        this.statut = statut;
        this.nbplaces = nbplaces;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getStatut() {
        return statut;
    }

    public int getNbplaces() {
        return nbplaces;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setNbplaces(int nbplaces) {
        this.nbplaces = nbplaces;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", statut='" + statut + '\'' +
                ", nbplaces=" + nbplaces +
                '}';
    }
}
