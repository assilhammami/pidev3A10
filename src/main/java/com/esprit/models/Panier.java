package com.esprit.models;

public class Panier {
    int id_panier;
    String Nom_produit;
    String Image_produit;
    float Prix_commande;
    int Quantite_produit;
    boolean Selected;
    int Id_produit;
    int id;

    public Panier(int id_panier, String nom_produit, String image_produit, float prix_commande, int quantite_produit, boolean selected, int id_produit, int id) {
        this.id_panier = id_panier;
        Nom_produit = nom_produit;
        Image_produit = image_produit;
        Prix_commande = prix_commande;
        Quantite_produit = quantite_produit;
        Selected = selected;
        Id_produit = id_produit;
        this.id = id;
    }

    public Panier(String nom_produit, String image_produit, float prix_commande, int quantite_produit, boolean selected, int id_produit, int id) {
        Nom_produit = nom_produit;
        Image_produit = image_produit;
        Prix_commande = prix_commande;
        Quantite_produit = quantite_produit;
        Selected = selected;
        Id_produit = id_produit;
        this.id = id;
    }

    public Panier() {
    }

    public int getId_panier() {
        return id_panier;
    }

    public void setId_panier(int id_panier) {
        this.id_panier = id_panier;
    }

    public String getNom_produit() {
        return Nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        Nom_produit = nom_produit;
    }

    public String getImage_produit() {
        return Image_produit;
    }

    public void setImage_produit(String image_produit) {
        Image_produit = image_produit;
    }

    public float getPrix_commande() {
        return Prix_commande;
    }

    public void setPrix_commande(float prix_commande) {
        Prix_commande = prix_commande;
    }

    public int getQuantite_produit() {
        return Quantite_produit;
    }

    public void setQuantite_produit(int quantite_produit) {
        Quantite_produit = quantite_produit;
    }

    public int getId_produit() {
        return Id_produit;
    }

    public void setId_produit(int id_produit) {
        Id_produit = id_produit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id_panier=" + id_panier +
                ", Nom_produit='" + Nom_produit + '\'' +
                ", Image_produit='" + Image_produit + '\'' +
                ", Prix_commande=" + Prix_commande +
                ", Quantite_produit=" + Quantite_produit +
                ", Selected=" + Selected +
                ", Id_produit=" + Id_produit +
                ", id=" + id +
                '}';
    }
}
