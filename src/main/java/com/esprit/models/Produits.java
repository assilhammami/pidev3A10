package com.esprit.models;

public class Produits {
    int Id_produit;
    String Nom;
    String Description ;
    float Prix;
    int Stock;
    String Date_Creation;
    String Categorie;
    String Image_produit;

    public Produits() {

    }

    public Produits(String nom, String description, float prix, int stock, String date_Creation, String categorie, String image_produit) {
        Nom = nom;
        Description = description;
        Prix = prix;
        Stock = stock;
        Date_Creation = date_Creation;
        Categorie = categorie;
        Image_produit = image_produit;
    }

    public Produits(int id_produit, String nom, String description, float prix, int stock, String date_Creation, String categorie, String image_produit) {
        Id_produit = id_produit;
        Nom = nom;
        Description = description;
        Prix = prix;
        Stock = stock;
        Date_Creation = date_Creation;
        Categorie = categorie;
        Image_produit = image_produit;
    }


    public Produits(int id_produit, String nom, String description, float prix, int stock, String date_Creation, String categorie) {
        Id_produit = id_produit;
        Nom = nom;
        Description = description;
        Prix = prix;
        Stock = stock;
        Date_Creation = date_Creation;
        Categorie = categorie;
    }

    public Produits(String nom, String description, float prix, int stock, String date_Creation, String categorie) {
        Nom = nom;
        Description = description;
        Prix = prix;
        Stock = stock;
        Date_Creation = date_Creation;
        Categorie = categorie;
    }

    public int getId_produit() {
        return Id_produit;
    }

    public void setId_produit(int id_produit) {
        Id_produit = id_produit;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public float getPrix() {
        return Prix;
    }

    public void setPrix(float prix) {
        Prix = prix;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public String getDate_Creation() {
        return Date_Creation;
    }

    public void setDate_Creation(String date_Creation) {
        Date_Creation = date_Creation;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String categorie) {
        Categorie = categorie;
    }

    public String getImage_produit() {
        return Image_produit;
    }

    public void setImage_produit(String image_produit) {
        Image_produit = image_produit;
    }

    @Override
    public String toString() {
        return "Produits{" +
                "Id_produit=" + Id_produit +
                ", Nom='" + Nom + '\'' +
                ", Description='" + Description + '\'' +
                ", Prix=" + Prix +
                ", Stock=" + Stock +
                ", Date_Creation='" + Date_Creation + '\'' +
                ", Categorie='" + Categorie + '\'' +
                ", Image_produit='" + Image_produit + '\'' +
                '}';
    }
}
