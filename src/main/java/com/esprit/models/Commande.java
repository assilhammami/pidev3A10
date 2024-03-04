package com.esprit.models;

public class Commande {
    int Id_commande;
    String Date_commande;
    float Montant_totale;
    int Quantite;
    int Id_produit;
    int id;

    public Commande(int id_commande, String date_commande, float montant_totale, int quantite, int id_produit, int id) {
        Id_commande = id_commande;
        Date_commande = date_commande;
        Montant_totale = montant_totale;
        Quantite = quantite;
        Id_produit = id_produit;
        this.id = id;
    }

    public Commande(String date_commande, float montant_totale, int quantite, int id_produit, int id) {
        Date_commande = date_commande;
        Montant_totale = montant_totale;
        Quantite = quantite;
        Id_produit = id_produit;
        this.id = id;
    }

    public Commande(String dateCommande, float montantTotal, int qte, String nomProduit) {
    }

    public int getId_commande() {
        return Id_commande;
    }

    public void setId_commande(int id_commande) {
        Id_commande = id_commande;
    }

    public String getDate_commande() {
        return Date_commande;
    }

    public void setDate_commande(String date_commande) {
        Date_commande = date_commande;
    }

    public float getMontant_totale() {
        return Montant_totale;
    }

    public void setMontant_totale(float montant_totale) {
        Montant_totale = montant_totale;
    }

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int quantite) {
        Quantite = quantite;
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

    @Override
    public String toString() {
        return "Commande{" +
                "Id_commande=" + Id_commande +
                ", Date_commande='" + Date_commande + '\'' +
                ", Montant_totale=" + Montant_totale +
                ", Quantite=" + Quantite +
                ", Id_produit=" + Id_produit +
                ", id=" + id +
                '}';
    }
}
