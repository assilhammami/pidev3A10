package com.esprit.services;

import com.esprit.models.Event;
import com.esprit.models.Reservation;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IService<Reservation> {

    private Connection connection;

    public ReservationService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Reservation reservation) {
        String req = "INSERT into reservation(date, statut, nbplaces) values (?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, reservation.getDate());
            pst.setString(2, reservation.getStatut());
            pst.setInt(3, reservation.getNbplaces());
            pst.executeUpdate();
            System.out.println("reservation ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Reservation reservation) {
        String req = "UPDATE reservation set  date = ?, statut = ?, nbplaces = ? where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(4, reservation.getId());
            pst.setString(1, reservation.getDate());
            pst.setString(2, reservation.getStatut());
            pst.setInt(4, reservation.getNbplaces());
            pst.executeUpdate();

            System.out.println("personne modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(int id ) {
        String req = "DELETE from reservation where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("reservation supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Reservation> afficher() {
        List<Reservation> reservations = new ArrayList<>();

        String req = "SELECT * from reservation";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                reservations.add(new Reservation(rs.getInt("id"),  rs.getString("date"), rs.getString("statut"), rs.getInt("nbplaces") ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reservations;
    }

}
