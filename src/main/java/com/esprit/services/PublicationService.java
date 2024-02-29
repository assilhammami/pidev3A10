package com.esprit.services;

import com.esprit.models.Publication;
import com.esprit.utils.DataSource;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PublicationService implements IService<Publication> {

    private Connection connection;

    public PublicationService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Publication publication) {
        String req = "INSERT into publication(titre, description, datepublication, image) values (?, ?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(2, publication.getDescription());
            pst.setString(1, publication.getTitre());
            pst.setObject(3, java.sql.Date.valueOf(publication.getDatepublication()));

            pst.setString(4, publication.getImage());
            pst.executeUpdate();
            System.out.println("Publication ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Publication publication) {
        String req = "UPDATE publication set titre = ?, description = ?, image= ?   where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(4, publication.getId());
            pst.setString(1, publication.getTitre());
            pst.setString(2, publication.getDescription());
            pst.setString(3, publication.getImage());
            pst.executeUpdate();
            System.out.println("Publication modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE from publication where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Publication supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Publication> afficher() {
        List<Publication> publication = new ArrayList<>();

        String req = "SELECT * from publication";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                publication.add(new Publication(rs.getInt("id"),rs.getString("image"),rs.getString("titre"),rs.getString("description"),rs.getObject("datepublication", LocalDate.class)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return publication;
    }
    public Publication getPublicationFromDatabase(int publicationId) {
        String req = "SELECT * FROM publication WHERE id = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, publicationId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Créez et retournez la publication à partir des résultats de la requête
                return new Publication(
                        rs.getInt("id"),
                        rs.getString("image"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getObject("datepublication", LocalDate.class)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // En cas d'erreur ou si la publication n'est pas trouvée, retournez null ou une valeur par défaut
        return null;
    }
    public List<Publication> getAllPublications() {
        List<Publication> publications = new ArrayList<>();

        String req = "SELECT * FROM publication";
        try (
             PreparedStatement pst = connection.prepareStatement(req);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                // Créez chaque publication à partir des résultats de la requête et ajoutez-la à la liste
                publications.add(new Publication(
                        rs.getInt("id"),
                        rs.getString("image"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getObject("datepublication", LocalDate.class)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return publications;
    }
    public List<String> getPublicationTitlesFromDatabase() {
        List<String> publicationTitles = new ArrayList<>();

        String query = "SELECT titre FROM publication"; // Supposons que le titre est une colonne dans votre table

        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String titre = rs.getString("titre");
                publicationTitles.add(titre);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions de manière appropriée dans une application réelle
        }

        return publicationTitles;
    }
    public Publication getPublicationByTitle(String title) {
        String req = "SELECT * FROM publication WHERE titre = ?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, title);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Publication(
                            rs.getInt("id"),
                            rs.getString("image"),
                            rs.getString("titre"),
                            rs.getString("description"),
                            rs.getObject("datepublication", LocalDate.class)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getPublicationIdByTitle(String title) {
        String sql = "SELECT id FROM publication WHERE titre = ?";
        try (
                     PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, title);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Gérez les exceptions correctement dans votre application
        }

        return -1; // Retourne -1 si le titre n'est pas trouvé ou s'il y a une erreur
    }




}
