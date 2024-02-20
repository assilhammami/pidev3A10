package com.esprit.models;


import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class Travail {
    private int id;
    private String description;
    private int prix ;
    private String type ;
    private StatusTravail status;
    private Date date_demande ;
    private Date date_fin;

    public Travail(int id, String description, int prix, String type, StatusTravail status, Date date_demande, Date date_fin) {
        this.id = id;
        this.description = description;
        this.prix = prix;
        this.type = type;
        this.status = status;
        this.date_demande = date_demande;
        this.date_fin = date_fin;
    }

    public Travail( String description, int prix, String type, StatusTravail status, Date date_demande, Date date_fin) {

        this.description = description;
        this.prix = prix;
        this.type = type;
        this.status = status;
        this.date_demande = date_demande;
        this.date_fin = date_fin;
    }


    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getPrix() {
        return prix;
    }

    public String getType() {
        return type;
    }

    public StatusTravail getStatus() {
        return status;
    }

    public Date getDate_demande() {
        return date_demande;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(StatusTravail status) {
        this.status = status;
    }

    public void setDate_demande(Date date_demande) {
        this.date_demande = date_demande;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Travail travail = (Travail) o;
        return id == travail.id && prix == travail.prix && Objects.equals(description, travail.description) && Objects.equals(type, travail.type) && status == travail.status && Objects.equals(date_demande, travail.date_demande) && Objects.equals(date_fin, travail.date_fin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, prix, type, status, date_demande, date_fin);
    }

    @Override
    public String toString() {
        return "Travail{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", date_demande=" + date_demande +
                ", date_fin=" + date_fin +
                '}';
    }

}
