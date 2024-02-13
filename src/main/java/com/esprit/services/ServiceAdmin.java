package com.esprit.services;


import com.esprit.models.Admin;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceAdmin implements IService<Admin> {

    private Connection connection;

    public ServiceAdmin() {
        connection = DataSource.getInstance().getConnection();
    }

    public void ajouter(Admin a) {
        String req = "INSERT into personne(profilepicture,name,lastname,email,password,username,UserType ) values (?,?,?,?,?,?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1,a.getProfilepicture());
            pst.setString(2,a.getName());
            pst.setString(3,a.getLastname());
            pst.setString(4,a.getEmail());
            pst.setString(5,a.getPassword());
            pst.setString(6,a.getUsername());
            pst.setString(7,a.getType().toString());


            pst.executeUpdate();
            System.out.println("Admin ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void modifier(Admin a) {
        String req = "UPDATE personne set profilepicture = ?, name = ?,lastname=?,email=?,password=?,username=?,UserType=? where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1,a.getProfilepicture());
            pst.setString(2,a.getName());
            pst.setString(3,a.getLastname());
            pst.setString(4,a.getEmail());
            pst.setString(5,a.getPassword());
            pst.setString(6,a.getUsername());
            pst.setString(7,a.getType().toString());
            pst.setInt(8, a.getId());

            pst.executeUpdate();
            System.out.println("Admin modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Admin a) {
        String req = "DELETE from personne where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, a.getId());
            pst.executeUpdate();
            System.out.println("Admin supprmié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Admin> afficher() {
        List<Admin> admins = new ArrayList<>();

        String req = "SELECT * from personne where UserType='ADMIN'";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                admins.add(new Admin(rs.getInt("id"),rs.getString("profilepicture"), rs.getString("name"), rs.getString("lastname"), rs.getString("email"),rs.getString("password"), rs.getString("username")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return admins;
    }
}
