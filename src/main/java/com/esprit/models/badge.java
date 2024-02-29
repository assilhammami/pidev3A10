package com.esprit.models;

public class badge {
    private int idbadge;
    private TypeBadge type;
    private String description;
    private String dateobtention;
    //private String icone;
    private int idutilisateur;
    public enum TypeBadge {
        VERIFIE,
        EVENEMENT,

    }

    public badge(int idbadge, TypeBadge type, String description, String dateobtention, int idutilisateur) {
        this.idbadge = idbadge;
        this.type = type;
        this.description = description;
        this.dateobtention = dateobtention;
        this.idutilisateur = idutilisateur;
    }

    public badge(TypeBadge type, String description, String dateobtention, int idutilisateur) {
        this.type = type;
        this.description = description;
        this.dateobtention = dateobtention;
        this.idutilisateur = idutilisateur;
    }

    public int getIdbadge() {
        return idbadge;
    }

    public void setIdbadge(int idbadge) {
        this.idbadge = idbadge;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeBadge getType() {
        return type;
    }

    public void setType(TypeBadge type) {
        this.type = type;
    }

    public String getDateobtention() {
        return dateobtention;
    }

    public void setDateobtention(String dateobtention) {
        this.dateobtention = dateobtention;
    }

    public int getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(int idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    @Override
    public String toString() {
        return "badge{" +
                "idbadge=" + idbadge +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", dateobtention='" + dateobtention + '\'' +
                ", idutilisateur=" + idutilisateur +
                '}';
    }
}
