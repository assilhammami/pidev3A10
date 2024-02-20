package com.esprit.models;
import java.lang.String;
public class cours {
private int id;
private String nom;
private String description;
private Num_chapitre num_chap;

    public cours(int id, String nom, String description, Num_chapitre num_chap) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.num_chap=num_chap;
    }

    public cours(String nom, String description, Num_chapitre num_chap) {
        this.nom = nom;
        this.description = description;
        this.num_chap = num_chap;
    }

    public cours(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public Num_chapitre getNum_chap() {
        return num_chap;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNum_chap(Num_chapitre num_chap) {
        this.num_chap = num_chap;
    }

    @Override
    public String toString() {
        return "cours{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", num_chap=" + num_chap +
                '}';
    }
}
