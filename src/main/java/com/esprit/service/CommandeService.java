package com.esprit.service;

import com.esprit.models.Commande;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandeService implements ICommandeService{

    Connection cnx = DataSource.getInstance().getConnection();
    @Override
    public void ajouterCommande(Commande c) throws SQLException {
        String req= "INSERT INTO `commande`(`Date_commande`,`Montant_totale`, `Quantite`, `Id_produit`, `id`)"+" VALUES (?,?,?,?,?)";
        PreparedStatement pst = cnx.prepareStatement(req);
        pst.setString(1,c.getDate_commande());
        pst.setFloat(2, c.getMontant_totale());
        pst.setInt(3, c.getQuantite());
        pst.setInt(4, c.getId_produit());
        pst.setInt(5, c.getId());
        pst.executeUpdate();
        System.out.println("Commande added Succesfully");
    }

    @Override
    public void modifierCommande(Commande c) throws SQLException {
        String req = "UPDATE commande set Date_commande = ?, Montant_totale = ?, Quantite = ? where Id_commande = ?;";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(4, c.getId_commande());
            pst.setString(1,c.getDate_commande());
            pst.setFloat(2, c.getMontant_totale());
            pst.setInt(3, c.getQuantite());
            pst.executeUpdate();
            System.out.println("Commande modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimerCommande(int Id_commande) {
        String req = "DELETE FROM commande WHERE Id_commande=?";

        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, Id_commande);
            pst.executeUpdate();

            System.out.println("Commande Deleted succesfully");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Commande> afficherCommande() {

        List<Commande> commandes = new ArrayList<>();

        String req = "SELECT * FROM commande";
        try {

            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                commandes.add(new Commande(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return commandes;
    }

    @Override
    public List<Commande> afficherCommandeByUserId(int id_user) {

        List<Commande> commandes = new ArrayList<>();

        String req = "SELECT * FROM commande WHERE id = ? ";
        try {

            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id_user);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                commandes.add(new Commande(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return commandes;
    }



    public String getUsernameById(int id){
        String username="";
        String req = "SELECT * FROM user WHERE id = ?";
        try{
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                username= rs.getString(3);
            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return username;
    }

    public int getUserIdByName(String name){
        int id=-1;
        String req = "SELECT * FROM user WHERE name= ?";
        try{
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1,name);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                id= rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }



    public String getProductNameById(int Id_produit){
        String NomProduit = "";
        String req = "SELECT * FROM produits WHERE Id_produit = ?";
        try{
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1,Id_produit);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                NomProduit= rs.getString(2);
            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return NomProduit;
    }
    public int getProductIdByName(String Nom_produit){
        int id=-1;
        String req = "SELECT * FROM produits WHERE Nom= ?";
        try{
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1,Nom_produit);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                id= rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }


    public String getUserEmailById(int id){
        String email="";
        String req = "SELECT * FROM user WHERE id = ?";
        try{
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                email= rs.getString(5);
            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return email;
    }

    public int getUserIdByEmail(String email){
        int id=-1;
        String req = "SELECT * FROM user WHERE email= ?";
        try{
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1,email);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                id= rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public String getProductImageById(int Id_produit){
        String ImageProd = "";
        String req = "SELECT * FROM produits WHERE Id_produit = ?";
        try{
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1,Id_produit);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                ImageProd= rs.getString(8);
            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return ImageProd;
    }
}
