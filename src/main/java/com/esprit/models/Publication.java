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
