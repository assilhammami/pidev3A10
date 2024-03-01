package com.esprit.services;


import com.esprit.models.NoteCours;
import com.esprit.models.avis;
import com.esprit.models.cours;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvisService implements IService<avis>{

    private Connection connection;

    public AvisService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(avis avis) {
        String req = "INSERT into avis(note, commentaire, id_cour, id_user) values (?, ?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);

            pst.setInt(4, avis.getIdu());
            pst.setInt(3, avis.getIdc());
            pst.setString(2, avis.getCommentaire());
            pst.setInt(1, avis.getNote().getValue());
            pst.executeUpdate();
            System.out.println("avis ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(avis avis) {
        String req = "UPDATE avis set note = ?, commentaire = ? where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(3, avis.getId());
            pst.setString(2, avis.getCommentaire());
            pst.setInt(1, avis.getNote().getValue());
            pst.executeUpdate();
            System.out.println("avis modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE from avis where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
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
                int noteValue = rs.getInt("note");
                int i = 1;
                NoteCours note = NoteCours.values()[ 1 ];
                String commentaire = rs.getString("commentaire");
                aviss.add(new avis(id, note, commentaire));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return aviss;
    }
    public List<avis> getByIDCours(int idd) {
        List<avis> avis = new ArrayList<>();

        String req = "SELECT * FROM avis WHERE id_cour="+idd;
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int noteValue = rs.getInt("note");
                int i = 1;
                NoteCours note = NoteCours.values()[ 1 ];
                String commentaire = rs.getString("commentaire");
                avis.add(new avis(id, note, commentaire));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return avis;
    }
}

