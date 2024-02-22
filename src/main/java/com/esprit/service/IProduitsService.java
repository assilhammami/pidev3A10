package com.esprit.service;



import com.esprit.models.Produits;

import java.sql.SQLException;
import java.util.List;

public interface IProduitsService {
    public void ajouterProduits(Produits p) throws SQLException;
    public void modifierProduits(Produits p) throws SQLException;
    public void supprimerProduits(int Id_produit);
    public List<Produits> afficherProduit();
    public void acheterProduit(int Id_produit, int ID_utilisateur);
}
