package com.esprit.services;

import com.esprit.models.Archive;
import com.esprit.models.StatusTravail;
import com.esprit.models.Travail;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArchiveService2 implements IService<Archive> {

    private Connection connection;

    public ArchiveService2() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Archive archive) {
        String req = "INSERT into archive(description, nb_offre,date_acceptation) values (?,?,?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setDate(3, archive.getDate_acceptation());
            pst.setDouble(2, archive.getNb_offre());
            pst.setString(1, archive.getDescription());
            pst.executeUpdate();
            System.out.println("Archive ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Archive archive) {
        String req = "UPDATE archive set description = ?, nb_offre = ?, , date_aceptation = ? where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(4, archive.getId());
            pst.setString(1, archive.getDescription());
            pst.setDouble(2, archive.getNb_offre());
            pst.setDate(3, archive.getDate_acceptation());
            pst.executeUpdate();
            System.out.println("Archive modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void supprimer(Archive archive) {
        String req = "DELETE from archive where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, archive.getId());
            pst.executeUpdate();
            System.out.println("Archive supprmiée !");
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
                int id = rs.getInt("id");
                String description = rs.getString("description");
                int nb_offre = rs.getInt("nb_offre");
                Date date_acceptation = rs.getDate("date_acceptation");

                archives.add(new Archive(id, description, nb_offre,date_acceptation));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return archives;
    }

}
