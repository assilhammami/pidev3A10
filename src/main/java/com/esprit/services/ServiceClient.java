package com.esprit.services;


import com.esprit.models.Client;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceClient implements IService<Client> {

    private Connection connection;

    public ServiceClient() {
        connection = DataSource.getInstance().getConnection();
    }

    public void ajouter(Client c) {
        String req = "INSERT into personne(profilepicture,name,lastname,email,password,username,UserType,adresse ) values (?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1,c.getProfilepicture());
            pst.setString(2,c.getName());
            pst.setString(3,c.getLastname());
            pst.setString(4,c.getEmail());
            pst.setString(5,c.getPassword());
            pst.setString(6,c.getUsername());
            pst.setString(7,c.getType().toString());
            pst.setString(8,c.getAdresse());



            pst.executeUpdate();
            System.out.println("Client ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void modifier(Client c) {
        String req = "UPDATE personne set profilepicture = ?, name = ?,lastname=?,email=?,password=?,username=?,UserType=?,specialite=?,debutCarriere=? where id = ? ;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1,c.getProfilepicture());
            pst.setString(2,c.getName());
            pst.setString(3,c.getLastname());
            pst.setString(4,c.getEmail());
            pst.setString(5,c.getPassword());
            pst.setString(6,c.getUsername());
            pst.setString(7,c.getType().toString());
            pst.setString(8,c.getAdresse());

            pst.setInt(9, c.getId());

            pst.executeUpdate();
            System.out.println("Client modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Client c) {
        String req = "DELETE from personne where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, c.getId());
            pst.executeUpdate();
            System.out.println("Client supprmié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Client> afficher() {
        List<Client> clients = new ArrayList<>();

        String req = "SELECT * from personne where UserType='CLIENT' ";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                clients.add(new Client(rs.getInt("id"),rs.getString("profilepicture"), rs.getString("name"), rs.getString("lastname"), rs.getString("email"),rs.getString("password"), rs.getString("username"), rs.getString("adresse")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return clients;
    }
}