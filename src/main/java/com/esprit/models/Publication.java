package com.esprit.models;


import javafx.scene.control.Label;

import java.time.LocalDate;

;

public class Publication {

    private int id;
    private String image;
    private String titre;
    private String description;
    private LocalDate datepublication;
    private int iduser;
    private boolean favori;

    public void setFavori(boolean favori) {
        this.favori = favori;
    }

    public boolean isFavori() {
        return favori;
    }

    public Publication(String image, String titre, String description, LocalDate datepublication, int iduser, boolean favori) {
        this.image = image;
        this.titre = titre;
        this.description = description;
        this.datepublication = datepublication;
        this.iduser = iduser;
        this.favori = false;
    }

    public Publication(String image, String titre, String description, int iduser, boolean favori) {
        this.image = image;
        this.titre = titre;
        this.description = description;
        this.iduser = iduser;
        this.favori = false;
    }


    public Publication(int id, String image, String titre, String description, LocalDate datepublication, int iduser) {
        this.id = id;
        this.image = image;
        this.titre = titre;
        this.description = description;
        this.datepublication =LocalDate.now();
        this.iduser = iduser;
    }

    public Publication(String image, String titre, String description, LocalDate datepublication, int iduser) {
        this.image = image;
        this.titre = titre;
        this.description = description;
        this.datepublication =LocalDate.now();
        this.iduser = iduser;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public Publication(String image, String titre, String description, int iduser) {
        this.image = image;
        this.titre = titre;
        this.description = description;
        this.datepublication =LocalDate.now();
        this.iduser = iduser;
    }

    public Publication(int id, String image, String titre, String description) {
        this.id = id;
        this.image = image;
        this.titre = titre;
        this.description = description;
        this.datepublication =LocalDate.now();

    }

    public Publication(int id, String image, String titre, String description, LocalDate datepublication) {
        this.id = id;
        this.image = image;
        this.titre = titre;
        this.description = description;
        this.datepublication = datepublication;

    }

    public Publication(String image, String titre, String description) {
        this.image = image;
        this.titre = titre;
        this.description = description;
        this.datepublication =LocalDate.now();
    }

    public Publication() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDatepublication() {
        return datepublication;
    }

    public void setDatepublication(LocalDate datepublication) {
        this.datepublication = datepublication;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }







    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", datepublication=" + datepublication +
                '}';
    }
}
