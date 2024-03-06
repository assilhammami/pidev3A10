package com.esprit.models;
import java.awt.*;
import java.lang.String;
import java.sql.Date;
import java.util.Objects;

public class cours {
private int id;
private String nom;
private String description;
private Date date_pub;
private String image;
    private int likes; // New field for likes
    private int dislikes;

    public cours() {


    }
    public cours(String nom, String description, Date date_pub, String image) {
        this.nom = nom;
        this.description = description;
        this.date_pub=date_pub;
        this.image = image;
    }

    public cours(int id, String nom, String description, Date date_pub, String image) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date_pub = date_pub;
        this.image = image;
    }

    public cours(int id, String nom, String description, Date datePub, String image, int likes, int dislike) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date_pub = datePub;
        this.image = image;
        this.likes = likes ;
        this.dislikes = dislike ;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
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

    public Date getDate_pub() {
        return date_pub;
    }

    public String getImage() {
        return image;
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

    public void setDate_pub(Date date_pub) {
        this.date_pub = date_pub;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "cours{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", date_pub=" + date_pub +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        cours cours = (cours) o;
        return id == cours.id && Objects.equals(nom, cours.nom) && Objects.equals(description, cours.description) && Objects.equals(date_pub, cours.date_pub) && Objects.equals(image, cours.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, description, date_pub, image);
    }

    }

