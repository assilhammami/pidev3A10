package com.esprit.models;
import com.esprit.utils.DataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;

public  sealed class User permits Admin,Artiste,Client {

    protected int id;
    protected String photo_de_profile;
    protected String nom;
    protected String prenom;
    protected String email;
    protected String mot_de_passe;
    protected String username;
    protected int num_telephone;
    protected String type;
    protected String date_de_naissance;
    protected Boolean Active;

    public User(int id, String photo_de_profil_filepath, String nom, String prenom, String email, String mot_de_passe, String username, int num_telephone, String date_de_naissance) {
        this.id = id;
        this.photo_de_profile = photo_de_profil_filepath;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.username = username;
        this.num_telephone = num_telephone;
        this.type = UserType.CLIENT.toString();
        this.date_de_naissance = date_de_naissance;
        this.Active=false;
    }



    public User(String photo_de_profil_filepath, String nom, String prenom, String email, String mot_de_passe, String username, int num_telephone, String date_de_naissance) {



        this.photo_de_profile = photo_de_profil_filepath;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.username = username;
        this.num_telephone = num_telephone;
        this.type = UserType.CLIENT.toString();
        this.date_de_naissance = date_de_naissance;
        this.Active=false;
    }

    public User(String photo_de_profile_path, String nom, String prenom, String email, String mot_de_passe, String username, int num_telephone, String type, String date_de_naissance, Boolean active) {
        this.photo_de_profile = photo_de_profile_path;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.username = username;
        this.num_telephone = num_telephone;
        this.type = type;
        this.date_de_naissance = date_de_naissance;
        this.Active = active;
    }

    public User(int id, String photo_de_profile_path, String nom, String prenom, String email, String mot_de_passe, String username, int num_telephone, String type, String date_de_naissance, Boolean active) {
        this.id = id;
       this.photo_de_profile = photo_de_profile_path;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.username = username;
        this.num_telephone = num_telephone;
        this.type = type;
        this.date_de_naissance = date_de_naissance;
        this.Active = active;
    }









    public User() {

    }

    public Boolean getActive() {
        return Active;
    }

    public void setActive(Boolean active) {
        Active = active;
    }

    public String getPhoto_de_profile() {
        return photo_de_profile;
    }

    public void setPhoto_de_profile(String photo_de_profile) {
        this.photo_de_profile = photo_de_profile;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNum_telephone() {
        return num_telephone;
    }

    public void setNum_telephone(int num_telephone) {
        this.num_telephone = num_telephone;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate_de_naissance() {
        return date_de_naissance;
    }

    public void setDate_de_naissance(String date_de_naissance) {
        this.date_de_naissance = date_de_naissance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
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