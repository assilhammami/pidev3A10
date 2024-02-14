package com.esprit.services;

import com.esprit.models.Event;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventService implements IService<Event> {

    private Connection connection;

    public EventService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Event event) {
        String req = "INSERT into event(nom, date, description, capacity, place) values (?, ?, ?, ?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, event.getNom());
            pst.setString(2, event.getDate());
            pst.setString(3, event.getDescription());
            pst.setInt(4, event.getCapacity());
            pst.setString(5, event.getPlace());
            pst.executeUpdate();
            System.out.println("event ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Event event) {
        String req = "UPDATE event set nom = ?, date = ?, description = ?, capacity = ?, place = ? where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(6, event.getId());
            pst.setString(1, event.getNom());
            pst.setString(2, event.getDate());
            pst.setString(3, event.getDescription());
            pst.setInt(4, event.getCapacity());
            pst.setString(5, event.getPlace());
            pst.executeUpdate();

            System.out.println("event modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Event event) {
        String req = "DELETE from event where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, event.getId());
            pst.executeUpdate();
            System.out.println("Personne supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Event> afficher() {
        List<Event> events = new ArrayList<>();

        String req = "SELECT * from event";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                events.add(new Event(rs.getInt("id"), rs.getString("nom"), rs.getString("date"), rs.getString("description"), rs.getInt("capacity"), rs.getString("place")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return events;
    }
}
