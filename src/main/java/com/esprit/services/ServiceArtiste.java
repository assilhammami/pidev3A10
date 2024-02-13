package com.esprit.services;


import com.esprit.models.Artiste;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceArtiste implements IService<Artiste> {

    private Connection connection;

    public ServiceArtiste() {
        connection = DataSource.getInstance().getConnection();
    }

    public void ajouter(Artiste a) {
        String req = "INSERT into personne(profilepicture,name,lastname,email,password,username,UserType,specialite,debutCarriere ) values (?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1,a.getProfilepicture());
            pst.setString(2,a.getName());
            pst.setString(3,a.getLastname());
            pst.setString(4,a.getEmail());
            pst.setString(5,a.getPassword());
            pst.setString(6,a.getUsername());
            pst.setString(7,a.getType().toString());
            pst.setString(8,a.getSpecialite());
            pst.setString(9,a.getDebutCarriere());


            pst.executeUpdate();
            System.out.println("Artiste ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void modifier(Artiste a) {
        String req = "UPDATE personne set profilepicture = ?, name = ?,lastname=?,email=?,password=?,username=?,UserType=?,specialite=?,debutCarriere=? where id = ? ;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1,a.getProfilepicture());
            pst.setString(2,a.getName());
            pst.setString(3,a.getLastname());
            pst.setString(4,a.getEmail());
            pst.setString(5,a.getPassword());
            pst.setString(6,a.getUsername());
            pst.setString(7,a.getType().toString());
            pst.setString(8,a.getSpecialite());
            pst.setString(9,a.getDebutCarriere());
            pst.setInt(10, a.getId());

            pst.executeUpdate();
            System.out.println("Artiste modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Artiste a) {
        String req = "DELETE from personne where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, a.getId());
            pst.executeUpdate();
            System.out.println("Artiste supprmié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Artiste> afficher() {
        List<Artiste> artistes = new ArrayList<>();

        String req = "SELECT * from personne where UserType='ARTISTE' ";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                artistes.add(new Artiste(rs.getInt("id"),rs.getString("profilepicture"), rs.getString("name"), rs.getString("lastname"), rs.getString("email"),rs.getString("password"), rs.getString("username"), rs.getString("specialite"), rs.getString("debutCarriere")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return artistes;
    }
}