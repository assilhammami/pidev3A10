package com.esprit.services;

import com.esprit.models.commentaire;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class commentaireService implements IService<commentaire> {

    private Connection connection;

    public commentaireService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(commentaire commentaire) {
        String req = "INSERT into commentaire(contenu, datecommentaire, note, idpublication) values (?, ?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(2, commentaire.getDatecommentaire());
            pst.setString(1, commentaire.getContenu());
            pst.setInt(3, commentaire.getNote());
            pst.setInt(4, commentaire.getIdpublication());
            pst.executeUpdate();
            System.out.println("Commentaie ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(commentaire commentaire) {
        String req = "UPDATE commentaire set contenu = ?, note = ? where idcommentaire = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(3, commentaire.getIdcommentaire());
            pst.setString(1, commentaire.getContenu());
            pst.setInt(2, commentaire.getNote());
            pst.executeUpdate();
            System.out.println("Commentaire modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(commentaire commentaire) {
        String req = "DELETE from commentaire where idcommentaire = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, commentaire.getIdcommentaire());
            pst.executeUpdate();
            System.out.println("Commentaire supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<commentaire> afficher() {
        List<commentaire> publication = new ArrayList<>();

        String req = "SELECT * from commentaire";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                publication.add(new commentaire(rs.getInt("idcommentaire"),rs.getString("contenu"),rs.getString("datecommentaire"),rs.getInt("note"),rs.getInt("idpublication")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return publication;
    }
}
