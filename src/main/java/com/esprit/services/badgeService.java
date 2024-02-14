package com.esprit.services;

import com.esprit.models.badge;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class badgeService implements IService<badge> {

    private Connection connection;

    public badgeService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(badge badge) {
        String req = "INSERT into badge(type, description, dateobtention, idutilisateur) values (?, ?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(2, badge.getDescription());
            pst.setString(1, badge.getType().name());
            pst.setString(3, badge.getDateobtention());
            pst.setInt(4, badge.getIdutilisateur());
            pst.executeUpdate();
            System.out.println("Badge ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(badge badge) {
        String req = "UPDATE badge set type = ?, description = ? where idbadge = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(3, badge.getIdbadge());
            pst.setString(1, badge.getType().name());
            pst.setString(2, badge.getDescription());
            pst.executeUpdate();
            System.out.println("Badge modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(badge badge) {
        String req = "DELETE from badge where idbadge = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, badge.getIdbadge());
            pst.executeUpdate();
            System.out.println("Badge supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<badge> afficher() {
        List<badge> publication = new ArrayList<>();

        String req = "SELECT * from badge";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                publication.add(new badge(rs.getInt("idbadge"), badge.TypeBadge.valueOf(rs.getString("type")),rs.getString("description"),rs.getString("dateobtention"),rs.getInt("idutilisateur")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return publication;
    }
}
