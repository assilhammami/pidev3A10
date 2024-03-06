package com.esprit.models;

public class Reservation {

    private int id;
    private String date;
    private Status statut;
    private int nbplaces;
    private int user;

    public void setEvent(int event) {
        this.event = event;
    }

    private int event;
    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getEvent() {
        return event;
    }

    public Reservation() {

    }

    public Reservation(String date, Status statut, int nbplaces, int user, int event) {
        this.date = date;
        this.statut = statut;
        this.nbplaces = nbplaces;
        this.user = user;
        this.event = event;
    }

    public Reservation(int id, String date, Status statut, int nbplaces) {
        this.id = id;
        this.date = date;
        this.statut = statut;
        this.nbplaces = nbplaces;
    }
    public Reservation(int id, String date, Status statut, int nbplaces,int eventid ) {
        this.id = id;
        this.date = date;
        this.statut = statut;
        this.nbplaces = nbplaces;
        this.event=eventid;
    }

    public Reservation(String date, Status statut, int nbplaces ) {
        this.date = date;
        this.statut = statut;
        this.nbplaces = nbplaces;
    }


    public Reservation(String date, Status statut, int nbplaces ,int eventid ) {
        this.date = date;
        this.statut = statut;
        this.nbplaces = nbplaces;
        this.event=eventid;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Status getStatut() {
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

    public void setStatut(Status statut) {
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
