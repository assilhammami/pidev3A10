package com.esprit.services;

import com.esprit.models.cours;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursService implements IService<cours> {

    private Connection connection;

    public CoursService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(cours cours) {
        String req = "INSERT into cours(nom, description, date_pub, image) values (?, ?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(4, cours.getImage());
            pst.setDate(3, cours.getDate_pub());
            pst.setString(2, cours.getDescription());
            pst.setString(1, cours.getNom());
            pst.executeUpdate();
            System.out.println("cours ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(cours cours) {
        String req = "UPDATE cours set nom = ?, description = ?, date_pub = ?, image = ? where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(5, cours.getId());
            pst.setString(4, cours.getImage());
            pst.setDate(3, cours.getDate_pub());
            pst.setString(2, cours.getDescription());
            pst.setString(1, cours.getNom());
            pst.executeUpdate();
            System.out.println("cours modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE from cours where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1,id);
            pst.executeUpdate();
            System.out.println("cours supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<cours> afficher() {
        List<cours> courss = new ArrayList<>();

        String req = "SELECT * FROM cours";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                Date date_pub = rs.getDate("date_pub");
                String image = rs.getString("image");
                courss.add(new cours(id,nom,description,date_pub,image));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return courss;
    }

    public boolean isNomUnique(String nom) {
        String req = "SELECT * FROM cours WHERE nom = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, nom);
            ResultSet rs = pst.executeQuery();
            return !rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<cours> getByIDUser(int idd) {
        List<cours> cours = new ArrayList<>();

        String req = "SELECT * FROM cours WHERE id="+idd;
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                Date date_pub = rs.getDate("date_pub");
                String image = rs.getString("image");
                cours.add(new cours(id,nom,description,date_pub,image));}
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cours;
    }

}
