package com.esprit.service;

import com.esprit.models.Commande;

import java.sql.SQLException;
import java.util.List;

public interface ICommandeService {
    public void ajouterCommande(Commande c) throws SQLException;
    public void modifierCommande(Commande c) throws SQLException;
    public void supprimerCommande(int Id_produit);
    public List<Commande> afficherCommande();
    public String getUsernameById(int id);
    public int getUserIdByName(String name);
    public String getProductNameById(int Id_produit);
    public int getProductIdByName(String Nom_produit);

    public List<Commande> afficherCommandeByUserId(int id_user);
}
