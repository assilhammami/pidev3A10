package com.esprit.services;

import com.esprit.models.commentaire;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class commentaireService implements IService<commentaire> {

    private Connection connection;

    public commentaireService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(commentaire commentaire) {
        String req = "INSERT into commentaire(contenu, datecommentaire, note, idpublication,iduser) values (?, ?, ?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(2, commentaire.getDatecommentaire().toString());
            pst.setString(1, commentaire.getContenu());
            pst.setInt(3, commentaire.getNote());
            pst.setInt(4, commentaire.getIdpublication());
            pst.setInt(5, commentaire.getIduser());
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
    public void supprimer(int id) {

    }


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
                publication.add(new commentaire(rs.getString("contenu"),rs.getObject("datecommentaire", LocalDate.class),rs.getInt("note"),rs.getInt("idpublication"),rs.getInt("iduser")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return publication;
    }







        public String getUsernameById(int userid) {
            String username = null;
            String query = "SELECT user.username " +
                    "FROM commentaire " +
                    "INNER JOIN user ON commentaire.iduser = user.id " +
                    "WHERE commentaire.iduser = ?";

            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setInt(1, userid);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    username = rs.getString("username");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return username;
        }


    public List<commentaire> getCommentairesByPublicationId(int idPublication1) throws SQLException {
        List<commentaire> commentaires = new ArrayList<>();

        String req = "SELECT * FROM commentaire WHERE idpublication = ?";

        try (PreparedStatement pst = connection.prepareStatement(req)){
            pst.setInt(1, idPublication1);
            try(ResultSet rs = pst.executeQuery()) {



            while (rs.next()) {
                int idCommentaire = rs.getInt("idcommentaire");
                String contenu = rs.getString("contenu");
                int note = rs.getInt("note");
                int idUser = rs.getInt("iduser");
                LocalDate dateCommentaire = rs.getDate("datecommentaire").toLocalDate();

                commentaire com = new commentaire(idCommentaire, contenu,dateCommentaire, note, idPublication1, idUser );
                commentaires.add(com);

            }

        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions de manière appropriée dans une application réelle
        }

        return commentaires;
    }
    }
}
