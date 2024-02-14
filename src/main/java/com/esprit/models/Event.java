package com.esprit.models;

public class Event {

    private int id;
    private String nom;

    private String date;
    private String description;
    private int capacity;
    private String place;

    public Event(int id, String nom, String date, String description, int capacity, String place) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.description = description;
        this.capacity = capacity;
        this.place = place;
    }

    public Event(String nom, String date, String description, int capacity, String place) {
        this.nom = nom;
        this.date = date;
        this.description = description;
        this.capacity = capacity;
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getPlace() {
        return place;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", place='" + place + '\'' +
                '}';
    }
}
