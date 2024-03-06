package com.esprit.service;

import com.esprit.models.Commande;
import com.esprit.models.Panier;

import java.sql.SQLException;
import java.util.List;

public interface IPanierService {
    public void ajouterAuPanier(Panier p) throws SQLException;
    public void supprimerDuPanier(int id_user);
    public List<Panier> getUserCart(int id_user);
    public int buySelected(int id_user);
    public void setSelectedByPanierId(int panier_id, boolean toogleSelect);
}
