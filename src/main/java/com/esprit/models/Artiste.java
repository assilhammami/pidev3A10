package com.esprit.models;

import java.sql.Blob;

public final class Artiste extends User {
    public Artiste(int id, String photo_de_profil_filepath, String nom, String prenom, String email, String mot_de_passe, String username, int num_telephone, String date_de_naissance) {
        super(id, photo_de_profil_filepath, nom, prenom, email, mot_de_passe, username, num_telephone, date_de_naissance);
        this.type = UserType.ARTISTE.toString();
    }

    public Artiste(String photo_de_profil_filepath, String nom, String prenom, String email, String mot_de_passe, String username, int num_telephone, String date_de_naissance) {
        super(photo_de_profil_filepath, nom, prenom, email, mot_de_passe, username, num_telephone, date_de_naissance);
        this.type = UserType.ARTISTE.toString();
    }


    public Artiste(int id, String photo_de_profile_path, String nom, String prenom, String email, String mot_de_passe, String username, int num_telephone, String type, String date_de_naissance, Boolean active) {
        super(id, photo_de_profile_path, nom, prenom, email, mot_de_passe, username, num_telephone, type, date_de_naissance, active);
        this.type = UserType.ARTISTE.toString();
    }
    public Artiste(  String photo_de_profile_path, String nom, String prenom, String email, String mot_de_passe, String username, int num_telephone, String type, String date_de_naissance, Boolean active) {
        super( photo_de_profile_path, nom, prenom, email, mot_de_passe, username, num_telephone, type, date_de_naissance, active);
        this.type = UserType.ARTISTE.toString();
    }



    @Override
    public String toString() {
        return "Artiste{" +
                "id=" + id +
                ", photo_de_profile=" + photo_de_profile +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", username='" + username + '\'' +
                ", num_telephone=" + num_telephone +
                ", type='" + type + '\'' +
                ", date_de_naissance='" + date_de_naissance + '\'' +
                ", Active=" + Active +
                '}';
    }
}
