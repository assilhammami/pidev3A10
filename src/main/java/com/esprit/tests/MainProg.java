package com.esprit.tests;

import com.esprit.models.Commande;
import com.esprit.models.Produits;
import com.esprit.service.CommandeService;
import com.esprit.service.ProduitsService;

import java.sql.SQLException;

public class MainProg {
    public static void main(String[] args) throws SQLException {
        ProduitsService ps = new ProduitsService();

        // ******* PRODUITS ********
        Produits p = new Produits(8,"NAV","bbibvsrvbduvbdvbdrbv s",500,1,"Janvier 1978","Tableaux");
        //ps.ajouterProduits(p);
        //ps.modifierProduits(p);
        //ps.supprimerProduits(8);
        //System.out.println(ps.afficherProduit());

        // ******* COMMANDES ********
        CommandeService cs = new CommandeService();
        Commande c = new Commande(3,"13/02/2024",2500,1,1,21);

        // cs.ajouterCommande(c);
        //cs.modifierCommande(c);
        //cs.supprimerCommande(2);
        // System.out.println(cs.afficherCommande());
    }
}
