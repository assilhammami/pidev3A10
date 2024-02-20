package com.esprit.services;

import com.esprit.models.Num_chapitre;
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
        String req = "INSERT into cours(nom, description, num_chap) values (?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(3, cours.getNum_chap().name());
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
        String req = "UPDATE cours set nom = ?, description = ?, num_chap = ? where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(4, cours.getId());
            pst.setString(3, cours.getNum_chap().name());
            pst.setString(2, cours.getDescription());
            pst.setString(1, cours.getNom());
            pst.executeUpdate();
            System.out.println("cours modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(cours cours) {
        String req = "DELETE from cours where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, cours.getId());
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
                String num_chapStr = rs.getString("num_chap");
                Num_chapitre num_chap = Num_chapitre.valueOf(num_chapStr);
                courss.add(new cours(id, nom, description, num_chap));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return courss;
    }
}
