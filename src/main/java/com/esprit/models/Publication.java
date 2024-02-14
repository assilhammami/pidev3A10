package com.esprit.models;



;

public class Publication {

    private int id;
    private String titre;
    private String description;
    private String datepublication;
    private int idartiste;
    private String image;

    public Publication(int id, String titre, String description, String datepublication, int idartiste, String image) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.datepublication = datepublication;
        this.idartiste = idartiste;
        this.image = image;
    }


    public Publication(String titre, String description, String datepublication, int idartiste, String image) {
        this.titre = titre;
        this.description = description;
        this.datepublication = datepublication;
        this.idartiste = idartiste;
        this.image = image;
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

    public String getDatepublication() {
        return datepublication;
    }

    public void setDatepublication(String datepublication) {
        this.datepublication = datepublication;
    }

    public int getIdartiste() {
        return idartiste;
    }


    public void setIdartiste(int idartiste) {
        this.idartiste = idartiste;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", date publication=" + datepublication +
                ", id artiste=" + idartiste +
                '}';
    }
}
