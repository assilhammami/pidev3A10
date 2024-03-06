package com.esprit.models;

import java.sql.Date;
import java.util.Objects;

public class Archive {
    private int idU ;
    private String  description;
    private Travail travail;
    private String path;

    public Archive(int idU, String description, String path, Date dateCreation, Travail travail) {
        this.idU = idU;
        this.description = description;
        this.path = path;
        this.travail = travail;
        this.dateCreation = dateCreation ;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    Date dateCreation;

    public Archive(int id, String description, String path, Travail travail) {
        this.idU = id;
        this.description = description;
        this.path = path;
        this.travail = travail;
    }

    public Archive() {

    }


    public int getIdU() {
        return idU;
    }

    public void setIdU(int id) {
        this.idU = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Travail getTravail() {
        return this.travail;
    }

    public void setTravail(Travail travail) {
        this.travail = travail;
    }

    public String getPath() {return this.path;}

    public void setPath(String path) { this.path = path;}


    @Override
    public String toString() {
        return "Archive{" +
                "id=" + idU +
                ", description='" + description + '\'' +
                ", IDT=" + travail.getId() +
                ", Path " + path +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Archive archive)
            return travail.getId() == archive.getTravail().getId() && archive.getIdU() == this.idU;
        return false;
    }

    /*@Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getNb_offre(), getDate_acceptation());
    }*/
}
