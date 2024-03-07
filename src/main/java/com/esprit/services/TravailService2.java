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
        String req = "INSERT into travail(description, prix,type,status,date_demande,date_fin,titre,idp) values (?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(8, travail.getIdp());
            pst.setString(7, travail.getTitre());
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
        String req = "UPDATE travail set description = ?, prix = ?, type= ?,status= ?, date_demande= ? , date_fin = ? , titre= ?, idp= ? where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(9, travail.getId());
            pst.setString(1, travail.getDescription());
            pst.setInt(2, travail.getPrix());
            pst.setString(3, travail.getType());
            pst.setString(4, travail.getStatus().name());
            pst.setDate(5, travail.getDate_demande());
            pst.setDate(6, travail.getDate_fin());
            pst.setString(7, travail.getTitre());
            pst.setInt(8, travail.getIdp());

            pst.executeUpdate();
            System.out.println("Travail modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void toggleFavorite(int travailId, boolean isFavorite) {
        String req = "UPDATE travail SET favorite = ? WHERE id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setBoolean(1, isFavorite);
            pst.setInt(2, travailId);
            pst.executeUpdate();
            System.out.println("Travail favorite status updated!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE from travail where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
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
                String titre = rs.getString("titre");
                int idp = rs.getInt("idp");
                boolean favorite = rs.getBoolean("favorite");
                travails.add(new Travail(id, description, prix, type, status, date_demande, date_fin, titre, idp, favorite));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return travails;
    }


    public Travail getByID(int id) {

        Travail t = null;
        String req = "SELECT * FROM travail WHERE id= "+ Integer.toString(id);
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int idt = rs.getInt("id");
                String description = rs.getString("description");
                int prix = rs.getInt("prix");
                String type = rs.getString("type");
                StatusTravail status = StatusTravail.valueOf(rs.getString("status"));
                Date date_demande = rs.getDate("date_demande");
                Date date_fin = rs.getDate("date_fin");
                String titre = rs.getString("titre");
                int idp = rs.getInt("idp");

                t = new Travail(id, description, prix, type, status, date_demande, date_fin,titre,idp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return t;
    }

public List<Travail> afficherbyid (int id){
    List<Travail> travails = new ArrayList<>();

    String req = "SELECT * from travail where idp = ?";
        try {



        PreparedStatement pst = connection.prepareStatement(req);
        pst.setInt(1, id);



        ResultSet rs = pst.executeQuery();
        while (rs.next()) {

            int idt = rs.getInt("id");
            String description = rs.getString("description");
            int prix = rs.getInt("prix");
            String type = rs.getString("type");
            StatusTravail status = StatusTravail.valueOf(rs.getString("status"));
            Date date_demande = rs.getDate("date_demande");
            Date date_fin = rs.getDate("date_fin");
            String titre = rs.getString("titre");
            int idp = rs.getInt("idp");

            travails.add( new Travail(id, description, prix, type, status, date_demande, date_fin,titre,idp));




        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

        return travails;
}





}
