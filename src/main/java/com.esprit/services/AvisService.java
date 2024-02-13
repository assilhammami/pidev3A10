package com.esprit.services;


import com.esprit.models.avis;
import com.esprit.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvisService implements IService<avis>{

    private Connection connection;

    public AvisService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(avis avis) {
        String req = "INSERT into avis(id, note, commentaire_pos, commentaire_neg) values (?, ?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(4, avis.getCommentaire_neg());
            pst.setString(3, avis.getCommentaire_pos());
            pst.setInt(2, avis.getNote());
            pst.setInt(1, avis.getId());
            pst.executeUpdate();
            System.out.println("avis ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(avis avis) {
        String req = "UPDATE avis set note = ?, commentaire_pos = ?, commentaire_neg = ? where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(4, avis.getId());
            pst.setString(3, avis.getCommentaire_neg());
            pst.setString(2, avis.getCommentaire_pos());
            pst.setInt(1, avis.getNote());
            pst.executeUpdate();
            System.out.println("avis modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(avis avis) {
        String req = "DELETE from avis where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, avis.getId());
            pst.executeUpdate();
            System.out.println("avis supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }





    @Override
    public List<avis> afficher() {
        List<avis> aviss = new ArrayList<>();

        String req = "SELECT * FROM avis";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int note = rs.getInt("note");
                String commentaire_pos = rs.getString("commentaire_pos");
                String commentaire_neg = rs.getString("commentaire_neg");
                aviss.add(new avis(id, note, commentaire_pos, commentaire_neg));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return aviss;
    }
}

