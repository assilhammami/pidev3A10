package com.esprit.service;

import com.esprit.models.Commande;
import com.esprit.models.Produits;
import com.esprit.utils.DataSource;
import com.esprit.utils.SendEmail;

import javax.mail.MessagingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbonnementShopService {
    Connection cnx = DataSource.getInstance().getConnection();
    public boolean isSubscribed(int id_user){
        String req = "SELECT * FROM user WHERE id = ?";
        try{
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1,id_user);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                if(rs.getBoolean(12)){
                    return true;
                }
            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    public void toogleSubscription(int id_user ,boolean toogleSubscribe){
        String req = "UPDATE user set Subscribed_shop = ? where id = ?;";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setBoolean(1, toogleSubscribe);
            pst.setInt(2, id_user);
            pst.executeUpdate();
            System.out.println("Subscribe changed!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendEmailToSubscribedShop(Produits p) {
        String req = "SELECT * FROM user WHERE Subscribed_shop=true";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                //send email api
                String email = rs.getString(5);
                String msg = "********** Davincci *********\n******* New Product has been released *******\nProdcut Name: "+p.getNom()+"\n\n"+p.getDescription();
                SendEmail.sendMail(email, "New Product", msg);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
