package com.esprit.service;

import com.esprit.models.Commande;
import com.esprit.models.Panier;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PanierService implements IPanierService{
    Connection cnx = DataSource.getInstance().getConnection();
    @Override
    public void ajouterAuPanier(Panier p) throws SQLException{
        String req= "INSERT INTO `panier`(`Nom_produit`,`Image_produit`, `Prix_commande`, `Quantite_produit`, `Selected`, `Id_produit`, `id`)"+" VALUES (?,?,?,?,?,?,?)";
        PreparedStatement pst = cnx.prepareStatement(req);
        pst.setString(1,p.getNom_produit());
        pst.setString(2,p.getImage_produit());
        pst.setFloat(3,p.getPrix_commande());
        pst.setInt(4,p.getQuantite_produit());
        pst.setBoolean(5,p.isSelected());
        pst.setInt(6,p.getId_produit());
        pst.setInt(7,p.getId());
        pst.executeUpdate();
        System.out.println("Added to cart!");

    }
    @Override
    public void supprimerDuPanier(int id_user){
        String req = "DELETE FROM panier WHERE Selected=true AND id=?";

        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id_user);
            pst.executeUpdate();

            System.out.println("Order Deleted From Cart succesfully");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    @Override
    public List<Panier> getUserCart(int id_user){
        List<Panier> myCart = new ArrayList<>();

        String req = "SELECT * FROM panier WHERE id = ? ";
        try {

            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id_user);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                myCart.add(new Panier(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getFloat(4), rs.getInt(5), rs.getBoolean(6), rs.getInt(7), rs.getInt(8)));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return myCart;
    }
    @Override
    public void buySelected(int id_user){
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date_commande = dateObj.format(formatter);
        String req = "SELECT * FROM panier WHERE Selected=true AND id=?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id_user);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                float prix_commande = rs.getFloat("Prix_commande");
                int quantite_produit = rs.getInt("Quantite_produit");
                int id_produit = rs.getInt("Id_produit");
                int id = rs.getInt("id");
                Commande c = new Commande(date_commande, prix_commande, quantite_produit, id_produit, id);
                CommandeService cs = new CommandeService();
                cs.ajouterCommande(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void setSelectedByPanierId(int panier_id, boolean toogleSelect){
        String req = "UPDATE panier set Selected = ? where Id_panier = ?;";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setBoolean(1, toogleSelect);
            pst.setInt(2, panier_id);
            pst.executeUpdate();
            System.out.println("Select changed!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
