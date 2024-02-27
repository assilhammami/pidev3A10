package com.esprit.models;

import java.sql.Blob;
import java.sql.Date;

public final class Admin extends User {
    public Admin(int id, String photo_de_profil_filepath, String nom, String prenom, String email, String mot_de_passe, String username, int num_telephone, String date_de_naissance) {
        super(id, photo_de_profil_filepath, nom, prenom, email, mot_de_passe, username, num_telephone, date_de_naissance);
        this.type=UserType.ADMIN.toString();
        this.Active=true;
    }

    public Admin(String photo_de_profil_filepath, String nom, String prenom, String email, String mot_de_passe, String username, int num_telephone, String date_de_naissance) {
        super(photo_de_profil_filepath, nom, prenom, email, mot_de_passe, username, num_telephone, date_de_naissance);
        this.type=UserType.ADMIN.toString();
        this.Active=true;}



    @Override
    public String toString() {
        return "Admin{" +
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
