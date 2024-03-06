package com.esprit.service;



import com.esprit.models.Commande;
import com.esprit.models.Produits;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProduitsService implements IProduitsService {
    Connection cnx = DataSource.getInstance().getConnection();



    public void ajouterProduits(Produits p) throws SQLException {


            String req= "INSERT INTO `produits`(`Nom`,`Description`, `Prix`, `Stock`, `Date_Creation`, `Categorie`, `Image_produit`)"+" VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, p.getNom());
            pst.setString(2, p.getDescription());
            pst.setFloat(3, p.getPrix());
            pst.setInt(4, p.getStock());
            pst.setString(5, p.getDate_Creation());
            pst.setString(6, p.getCategorie());
            pst.setString(7, p.getImage_produit());
            pst.executeUpdate();
            System.out.println("Produit added Succesfully");

            //metier abonne:
            AbonnementShopService abonne = new AbonnementShopService();
            abonne.sendEmailToSubscribedShop(p);
    }

    public void modifierProduits(Produits p) throws SQLException {
        String req = "UPDATE produits set Nom = ?, Description = ?, Prix = ?, Stock = ?, Date_Creation = ?, Categorie = ?, Image_produit = ?  where Id_produit = ?;";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(8, p.getId_produit());
            pst.setString(1, p.getNom());
            pst.setString(2, p.getDescription());
            pst.setFloat(3, p.getPrix());
            pst.setInt(4, p.getStock());
            pst.setString(5, p.getDate_Creation());
            pst.setString(6, p.getCategorie());
            pst.setString(7, p.getImage_produit());
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
                produit.add(new Produits(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return produit;
    }

    public void acheterProduit(int Id_produit, int ID_utilisateur) {

        int qte = 0;
        float prix = 0;
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateCommande = dateObj.format(formatter);

        String req = "SELECT * FROM produits WHERE Id_produit like '" + Id_produit + "'";
        try {
            Statement st = cnx.createStatement();
            ResultSet p = st.executeQuery(req);


            if (p.next()) {
                qte = p.getInt(5);
                prix = p.getFloat(4);
            }

            if (qte >= 1) {

                CommandeService commande = new CommandeService();
                Commande c = new Commande(dateCommande, prix, 1, Id_produit, ID_utilisateur);
                commande.ajouterCommande(c);

                ProduitsService produit = new ProduitsService();
                Produits prod = new Produits(Id_produit, p.getString(2), p.getString(3), p.getFloat(4), p.getInt(5)-1,p.getString(6), p.getString(7),p.getString(8));

                produit.modifierProduits(prod);
            } else {
                System.out.println("Hors stock!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public int productReturnQuantity(int id_prod){
        String req = "SELECT * FROM produits where Id_produit = ?";
        try {

            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id_prod);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getInt(5) ;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public void removeQuantityFromProduct(int id_prod, int quantity){
        int newQuantity = productReturnQuantity(id_prod) - quantity;
        System.out.println(newQuantity+" = "+productReturnQuantity(id_prod)+" - "+quantity);
        String req = "UPDATE produits set Stock = ? where Id_produit = ?;";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, newQuantity);
            pst.setInt(2, id_prod);
            pst.executeUpdate();
            System.out.println("Product changed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

