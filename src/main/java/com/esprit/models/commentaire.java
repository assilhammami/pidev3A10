package com.esprit.models;

public class commentaire {
    private int idcommentaire;
    private String contenu;
    private String datecommentaire;
    private int note;
    private int idpublication;

    public commentaire(int idcommentaire, String contenu, String datecommentaire, int note, int idpublication) {
        this.idcommentaire = idcommentaire;
        this.contenu = contenu;
        this.datecommentaire = datecommentaire;
        this.note = note;
        this.idpublication = idpublication;
    }

    public commentaire(String contenu, String datecommentaire, int note, int idpublication) {
        this.contenu = contenu;
        this.datecommentaire = datecommentaire;
        this.note = note;
        this.idpublication = idpublication;
    }

    public int getIdcommentaire() {
        return idcommentaire;
    }

    public void setIdcommentaire(int idcommentaire) {
        this.idcommentaire = idcommentaire;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDatecommentaire() {
        return datecommentaire;
    }

    public void setDatecommentaire(String datecommentaire) {
        this.datecommentaire = datecommentaire;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getIdpublication() {
        return idpublication;
    }

    public void setIdpublication(int idpublication) {
        this.idpublication = idpublication;
    }

    @Override
    public String toString() {
        return "commentaire{" +
                "idcommentaire=" + idcommentaire +
                ", contenu='" + contenu + '\'' +
                ", datecommentaire='" + datecommentaire + '\'' +
                ", note=" + note +
                ", idpublication=" + idpublication +
                '}';
    }
}
