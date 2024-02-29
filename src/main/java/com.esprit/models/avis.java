package com.esprit.models;

import java.util.Objects;

public class avis {
    private int id;
    private NoteCours note;
    private String commentaire;


    public int getIdc() {
        return idc;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    private int idc;

    private int idu;


    public avis(int id, NoteCours note, String commentaire) {
        this.id = id;
        this.note = note;
        this.commentaire = commentaire;
    }
    public avis(NoteCours note, String commentaire) {
        this.note = note;
        this.commentaire = commentaire;
    }

    public avis() {
    }

    public avis(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public NoteCours getNote() {
        return note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNote(NoteCours note) {
        this.note = note;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "avis{" +
                "id=" + id +
                ", note=" + note +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        avis avis = (avis) o;
        return id == avis.id && note == avis.note && Objects.equals(commentaire, avis.commentaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, note, commentaire);
    }
}
