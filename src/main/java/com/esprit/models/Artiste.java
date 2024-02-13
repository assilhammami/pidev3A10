package com.esprit.models;

public final class Artiste extends Personne{

    public String specialite;
    public String debutCarriere;
    public Artiste(int id ,String profilepicture, String name, String lastname, String email, String password, String username,String specialite,String debutdecarriere) {
        super(id,profilepicture, name, lastname, email, password, username);
        this.type=UserType.ARTISTE;
        this.specialite=specialite;
        this.debutCarriere=debutdecarriere;

    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getDebutCarriere() {
        return debutCarriere;
    }

    public void setDebutCarriere(String debutCarriere) {
        this.debutCarriere = debutCarriere;
    }

    @Override
    public String toString() {
        return "Artiste{" +
                "specialite='" + specialite + '\'' +
                ", debutCarriere='" + debutCarriere + '\'' +
                ", profilepicture='" + profilepicture + '\'' +
                ", username='" + username + '\'' +
                ", type=" + type +
                '}';
    }
}
