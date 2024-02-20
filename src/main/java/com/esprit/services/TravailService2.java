package com.esprit.services;

import com.esprit.models.StatusTravail;
import com.esprit.models.Travail;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravailService2 implements IService<Travail> {

    private final Connection connection;

    public TravailService2() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Travail travail) {
        String req = "INSERT into travail(description, prix,type,status,date_demande,date_fin) values (?,?,?,?,?,?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setDate(6, travail.getDate_fin());
            pst.setDate(5, travail.getDate_demande());

            pst.setString(4, travail.getStatus().name());
            pst.setString(3, travail.getType());
            pst.setInt(2, travail.getPrix());
            pst.setString(1, travail.getDescription());
            pst.executeUpdate();
            System.out.println("Travail ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void modifier(Travail travail) {
        String req = "UPDATE travail set description = ?, prix = ?, type= ?,status= ?, date_demande= ? , date_fin = ? where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(7, travail.getId());
            pst.setString(1, travail.getDescription());
            pst.setInt(2, travail.getPrix());
            pst.setString(3, travail.getType());
            pst.setString(4, travail.getStatus().name());
            pst.setDate(5, travail.getDate_demande());
            pst.setDate(6, travail.getDate_fin());
            pst.executeUpdate();
            System.out.println("Travail modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void supprimer(Travail travail) {
        String req = "DELETE from travail where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, travail.getId());
            pst.executeUpdate();
            System.out.println("Travail supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Travail> afficher() {
        List<Travail> travails = new ArrayList<>();

        String req = "SELECT * FROM travail";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                int prix = rs.getInt("prix");
                String type = rs.getString("type");
                StatusTravail status = StatusTravail.valueOf(rs.getString("status"));
                Date date_demande = rs.getDate("date_demande");
                Date date_fin = rs.getDate("date_fin");

                travails.add(new Travail(id, description, prix, type, status, date_demande, date_fin));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return travails;
    }

}
