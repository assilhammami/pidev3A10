package com.esprit.service;



import com.esprit.models.Produits;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitsService implements IProduitsService {
    Connection cnx = DataSource.getInstance().getConnection();



    public void ajouterProduits(Produits p) throws SQLException {
        try (Connection cnx = DataSource.getInstance().getConnection()) {

            String req= "INSERT INTO `produits`(`Nom`,`Description`, `Prix`, `Stock`, `Date_Creation`, `Categorie`)"+" VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, p.getNom());
            pst.setString(2, p.getDescription());
            pst.setFloat(3, p.getPrix());
            pst.setInt(4, p.getStock());
            pst.setString(5, p.getDate_Creation());
            pst.setString(6, p.getCategorie());
            pst.executeUpdate();
            System.out.println("Produit added Succesfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void modifierProduits(Produits p) throws SQLException {
        String req = "UPDATE produits set Nom = ?, Description = ?, Prix = ?, Stock = ?, Date_Creation = ?, Categorie = ?  where Id_produit = ?;";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(7, p.getId_produit());
            pst.setString(1, p.getNom());
            pst.setString(2, p.getDescription());
            pst.setFloat(3, p.getPrix());
            pst.setInt(4, p.getStock());
            pst.setString(5, p.getDate_Creation());
            pst.setString(6, p.getCategorie());
            pst.executeUpdate();
            System.out.println("Produit modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
    public void supprimerProduits(int Id_produit) {

        String req = "DELETE FROM produits WHERE Id_produit=?";

        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, Id_produit);
            pst.executeUpdate();

            System.out.println("Product Deleted succesfully");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public List<Produits> afficherProduit() {


        List<Produits> produit = new ArrayList<>();

        String req = "SELECT * FROM produits";
        try {

            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                produit.add(new Produits(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getInt(5), rs.getString(6), rs.getString(7)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return produit;
    }
}

