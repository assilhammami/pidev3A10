package com.esprit.models;
import java.lang.String;
public class cours {
private int id;
private String nom;
private String description;
private  FormatCours format;

    public cours(int id, String nom, String description,FormatCours format) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.format=format;
    }

    public cours(String nom, String description, FormatCours format) {
        this.nom = nom;
        this.description = description;
        this.format = format;
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

    public FormatCours getFormat() {
        return format;
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

    public void setFormat(FormatCours format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "cours{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", format=" + format +
                '}';
    }
}
