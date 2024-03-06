package com.esprit.services;

import com.esprit.models.Archive;
import com.esprit.models.StatusTravail;
import com.esprit.models.Travail;
import com.esprit.utils.DataSource;
import javafx.util.Pair;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArchiveService2 implements IService<Archive> {

    private Connection connection;

    public ArchiveService2() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Archive archive) {
        String req = "INSERT into archive(idU,idT,description,image,dateCreation) values (?,?,?,?,?);"; //declaration de la requete
        try {
            PreparedStatement pst = connection.prepareStatement(req); //preparation de la requete
            pst.setInt(2, archive.getTravail().getId());
            pst.setString(3, archive.getDescription());
            pst.setString(4, archive.getPath());
            pst.setDate(5, archive.getDateCreation());
            pst.setInt(1, archive.getIdU());
            pst.executeUpdate(); // exécution de la requete
            System.out.println("Archive ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public int countRecentArchives() {
        String req = "SELECT COUNT(*) FROM archive WHERE dateCreation > ?";
        int count = 0;

        try {
            PreparedStatement pst = connection.prepareStatement(req);
            // Subtract 24 hours from the current time
            pst.setTimestamp(1, new Timestamp(System.currentTimeMillis() - 24 * 3600 * 1000));
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return count;
    }
    public List<Pair<String, Archive>> getRecentArchivesWithUserName() {
        List<Pair<String, Archive>> recentArchives = new ArrayList<>();
        String req = "SELECT a.*, u.nom as userName FROM archive a " +
                "JOIN user u ON a.idU = u.id " +  // Replace 'users' and 'name' with your actual user table and column names
                "WHERE a.dateCreation > ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now().minusHours(24)));
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int idU = rs.getInt("idU");
                String description = rs.getString("description");
                String path = rs.getString("image");
                Date dateCreation = rs.getDate("dateCreation");
                int idT = rs.getInt("idT");
                String userName = rs.getString("userName");

                TravailService2 travailService = new TravailService2();
                Travail travail = travailService.getByID(idT);

                Archive archive = new Archive(idU, description, path, dateCreation, travail);
                recentArchives.add(new Pair<>(userName, archive));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return recentArchives;
    }

    @Override
    public void modifier(Archive archive) {
        String req = "UPDATE archive set description = ?, image = ?, dateCreation = ? where idU = ? and idT = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(5, archive.getTravail().getId());
            pst.setString(1, archive.getDescription());
            pst.setString(2, archive.getPath());
            pst.setDate( 3, archive.getDateCreation());
            pst.setInt(4, archive.getIdU());
            pst.executeUpdate();
            System.out.println("Archive modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void supprimer(int id) {
        String req = "DELETE from archive where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Archive supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Archive> afficher() {
        List<Archive> archives = new ArrayList<>();

        String req = "SELECT * FROM archive";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int idU = rs.getInt("idU");
                String description = rs.getString("description");
                String path = rs.getString("image");
                int idT = rs.getInt("idT");
                TravailService2 service = new TravailService2();
                Travail t = service.getByID(idT);
                archives.add(new Archive(idU, description, path, t));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return archives;
    }

    public List<Archive> getByIDUser(int id) {
        List<Archive> archives = new ArrayList<>();

        String req = "SELECT * FROM archive WHERE idU="+id;
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int idU = rs.getInt("idU");
                String description = rs.getString("description");
                String path = rs.getString("image");
                int idT = rs.getInt("idT");
                TravailService2 service = new TravailService2();
                Travail t = service.getByID(idT);
                archives.add(new Archive(idU, description, path, t));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return archives;
    }
    public int countArchivesForTravail(int travailId) {
        // SQL query to count archives for a specific travail
        String req = "SELECT COUNT(*) FROM archive WHERE idT = ?";
        int count = 0;

        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, travailId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return count;
    }
}
