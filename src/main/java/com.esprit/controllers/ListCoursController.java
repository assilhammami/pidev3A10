package com.esprit.controllers;

import com.esprit.models.cours;
import com.esprit.services.CoursService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ListCoursController implements Initializable {

    @FXML
    private TableColumn<cours, java.util.Date> Date;

    @FXML
    private TableColumn<cours, String> Description;

    @FXML
    private TableColumn<cours, Integer> ID;

    @FXML
    private TableColumn<cours, String> Image;

    @FXML
    private TableColumn<cours, String> Nom;

    @FXML
    private TableView<cours> tabcours;
    private CoursService cs = new CoursService();

    private ObservableList<cours> displayedCours = FXCollections.observableArrayList(cs.afficher());

    @FXML
    public void initializeTableViewT() {
        // Ajouter toutes les utilisateurs à la liste observable
        displayedCours.addAll(cs.afficher());

        // Associer la liste observable à la table view
        tabcours.setItems(displayedCours);
    }

    @FXML
    private void rafraichirTableView() {
        List<cours> coursList = cs.afficher();
        ObservableList<cours> cours = FXCollections.observableArrayList(coursList);

        // Associer les propriétés des zones aux colonnes de la table view
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date_pub"));
        Image.setCellValueFactory(new PropertyValueFactory<>("image"));

        // Associer la liste observable à la table view
        tabcours.setItems(cours);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rafraichirTableView();
        initializeTableViewT();
    }

}


