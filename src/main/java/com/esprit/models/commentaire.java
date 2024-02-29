package com.esprit.models;

import java.time.LocalDate;

public class commentaire {
    private int idcommentaire;
    private String contenu;
    private LocalDate datecommentaire;
    private int note;
    private int idpublication;
    private  int iduser;

    public commentaire(String contenu, int note, int idpublication, int iduser) {
        this.contenu = contenu;
        this.note = note;
        this.idpublication = idpublication;
        this.datecommentaire =LocalDate.now();
        this.iduser = iduser;
    }

    public commentaire(int idcommentaire, String contenu, LocalDate datecommentaire, int note, int idpublication, int iduser) {
        this.idcommentaire = idcommentaire;
        this.contenu = contenu;
        this.datecommentaire = LocalDate.now();;
        this.note = note;
        this.idpublication = idpublication;
        this.iduser = iduser;
    }


    public commentaire(int idcommentaire, String contenu, int note) {
        this.idcommentaire = idcommentaire;
        this.contenu = contenu;
        this.note = note;
        this.datecommentaire =LocalDate.now();
    }

    public commentaire(int idcommentaire, String contenu, LocalDate datecommentaire, int note) {
        this.idcommentaire = idcommentaire;
        this.contenu = contenu;
        this.datecommentaire = LocalDate.now();
        this.note = note;
    }

    public commentaire(String contenu, int note) {
        this.contenu = contenu;
        this.note = note;
        this.datecommentaire =LocalDate.now();
    }

    public commentaire(int idcommentaire, String contenu, LocalDate datecommentaire, int note, int idpublication) {
        this.idcommentaire = idcommentaire;
        this.contenu = contenu;
        this.datecommentaire = datecommentaire;
        this.note = note;
        this.idpublication = idpublication;
    }

    public commentaire(String contenu, LocalDate datecommentaire, int note, int idpublication, int iduser) {
        this.contenu = contenu;
        this.datecommentaire = datecommentaire;
        this.note = note;
        this.idpublication = idpublication;
        this.iduser = iduser;
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

    public LocalDate getDatecommentaire() {
        return datecommentaire;
    }

    public void setDatecommentaire(LocalDate datecommentaire) {
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

    public commentaire(String contenu, int note, int idpublication) {
        this.contenu = contenu;
        this.note = note;
        this.idpublication = idpublication;
        this.datecommentaire =LocalDate.now();

    }

    @Override
    public String toString() {
        return "commentaire{" +
                "idcommentaire=" + idcommentaire +
                ", contenu='" + contenu + '\'' +
                ", datecommentaire='" + datecommentaire + '\'' +
                ", note=" + note +

                '}';
    }


    public  int getIduser() {
       return iduser;
    }

    public void setId_user(int iduser) {
        this.iduser = iduser;
    }
}
