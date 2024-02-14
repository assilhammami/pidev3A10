package com.esprit.services;

import com.esprit.models.Publication;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicationService implements IService<Publication> {

    private Connection connection;

    public PublicationService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Publication publication) {
        String req = "INSERT into publication(titre, description, datepublication, idartiste, image) values (?, ?, ?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(2, publication.getDescription());
            pst.setString(1, publication.getTitre());
            pst.setString(3, publication.getDatepublication());
            pst.setInt(4, publication.getIdartiste());
            pst.setString(5, publication.getImage());
            pst.executeUpdate();
            System.out.println("Publication ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Publication publication) {
        String req = "UPDATE publication set titre = ?, description = ? where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(3, publication.getId());
            pst.setString(1, publication.getTitre());
            pst.setString(2, publication.getDescription());
            pst.executeUpdate();
            System.out.println("Publication modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Publication publication) {
        String req = "DELETE from publication where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, publication.getId());
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
                publication.add(new Publication(rs.getInt("id"),rs.getString("titre"),rs.getString("description"),rs.getString("datepublication"),rs.getInt("idartiste"),rs.getString("image")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return publication;
    }
}
