package com.esprit.services;

import com.esprit.models.Publication;
import com.esprit.models.favori;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class favoriService implements IService<favori> {
    private Connection connection;

    public favoriService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(favori favori) {
        String req ="INSERT INTO favori (idpublication, iduser) VALUES (?, ?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(2, favori.getIduser());
            pst.setInt(1, favori.getIdpublication());
            pst.executeUpdate();
            System.out.println("Publication ajoutée à favori !");

    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
    public void modifier(favori favori) {

    }

    @Override
    public void supprimer(int id) {

    }
    public void supprimer(favori favori){
        String req = "DELETE from favori where idfavori = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, favori.getIdfavori());
            pst.executeUpdate();
            System.out.println("Commentaire supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean estDejaFavori(int idUtilisateur, int idPublication) {
        String req = "SELECT * FROM favori WHERE iduser = ? AND idpublication = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, idUtilisateur);
            pst.setInt(2, idPublication);
            ResultSet rs = pst.executeQuery();
            return rs.next(); // Retourne true si une ligne est trouvée, sinon false
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Récupère un objet favori en fonction de l'ID de la publication et de l'ID de l'utilisateur
    public favori getFavoriParPublicationEtUtilisateur(int iduser, int idpublication) {
        String req = "SELECT * FROM favori WHERE iduser = ? AND idpublication = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, iduser);
            pst.setInt(2, idpublication);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int idfavori = rs.getInt("idfavori");
                // Ajoutez ici d'autres attributs si nécessaire
                return new favori(idfavori, idpublication, iduser);
            } else {
                return null; // Aucun favori trouvé
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<favori> afficher() {
        return null;
    }
}
