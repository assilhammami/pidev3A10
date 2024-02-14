package com.esprit.models;

import java.sql.Date;
import java.util.Objects;

public class Archive {
    private int id ;
    private String  description;
    private int nb_offre;
    private Date date_acceptation;

    public Archive(int id, String description, int nb_offre, Date date_acceptation) {
        this.id = id;
        this.description = description;
        this.nb_offre = nb_offre;
        this.date_acceptation = date_acceptation;
    }

    public Archive(String description, int nb_offre, Date date_acceptation) {
        this.description = description;
        this.nb_offre = nb_offre;
        this.date_acceptation = date_acceptation;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNb_offre() {
        return nb_offre;
    }

    public void setNb_offre(int nb_offre) {
        this.nb_offre = nb_offre;
    }

    public Date getDate_acceptation() {
        return date_acceptation;
    }

    public void setDate_acceptation(Date date_acceptation) {
        this.date_acceptation = date_acceptation;
    }


    @Override
    public String toString() {
        return "Archive{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", nb_offre=" + nb_offre +
                ", date_acceptation=" + date_acceptation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Archive archive)) return false;
        return getId() == archive.getId() && getNb_offre() == archive.getNb_offre() && Objects.equals(getDescription(), archive.getDescription()) && Objects.equals(getDate_acceptation(), archive.getDate_acceptation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getNb_offre(), getDate_acceptation());
    }
}
