package com.esprit.controllers;

import com.esprit.models.StatusTravail;
import com.esprit.models.Travail;
import com.esprit.services.MyListener;
import com.esprit.services.TravailService2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AfficheController implements Initializable {

    @FXML
    private VBox chosen;

    @FXML
    private TextField date_demande;

    @FXML
    private TextField date_fin;

    @FXML
    private TextArea description;

    @FXML
    private GridPane grid;

    @FXML
    private TextField prix;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField type;

    private int id_travail;

    @FXML
    private TextField searchField; // Champ de texte pour la recherche



    MyListener myListener;

    public Travail selectedTravail;
    private List<Travail> TravailList = new ArrayList<>();

    private void setChosenTravail(Travail travail) {
        selectedTravail = travail;
        id_travail = travail.getId();
        description.setText(travail.getDescription());
        type.setText(travail.getType());
        prix.setText(String.valueOf(travail.getPrix()));
        date_fin.setText((String.valueOf(travail.getDate_fin())));
        date_demande.setText((String.valueOf(travail.getDate_demande())));


    } // travail selectionnée


    void Update() {
        Travail tt;
        TravailService2 ts = new TravailService2();
        TravailList.clear();
        grid.getChildren().clear();
        TravailList.addAll(ts.afficher());
        myListener = new MyListener() {
            @Override
            public void onClickListener(Travail c) {
                setChosenTravail(c);

            } //capteur de cliquage
        };

        int c = 0;
        int l = 0;
        try {
            for (int i = 0; i < TravailList.size(); i++) {// affichage des cartes
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/TravailCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                TravailCardController cardcontroller = fxmlLoader.getController();
                cardcontroller.setData(TravailList.get(i), myListener);
                if (c > 3) {
                    c = 0;
                    l++;
                }
                grid.add(anchorPane, c++, l);
//grid weight
                grid.setMinWidth(134);
                grid.setPrefWidth(134);
                grid.setMaxWidth(134);//
//height
                grid.setMinHeight(112);
                grid.setPrefHeight(112);
                grid.setMaxHeight(112);//
                grid.setLayoutY(10);


                GridPane.setMargin(anchorPane, new Insets(175, 0, 0, 70));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Ajouter un écouteur d'événements sur le champ de recherche
       searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Appeler la fonction de recherche à chaque fois que le texte du champ de recherche change
            search();
        });

        // Initialiser l'affichage des travaux
        Update();

    }// lors d execution d'interface


    @FXML
    void modifier(ActionEvent event) {
        Travail travail = new Travail(id_travail, description.getText(), Integer.parseInt(prix.getText()), type.getText(), StatusTravail.Attente,Date.valueOf(date_demande.getText()), Date.valueOf(date_demande.getText()));
        TravailService2 ts = new TravailService2();
        ts.modifier(travail);
        TravailList.clear();
        grid.getChildren().clear();
        Update();


    } // boutton modifier




    @FXML
    void supprimer(MouseEvent event) {
        TravailService2 cs = new TravailService2();
        cs.supprimer(id_travail);
        TravailList.clear();
        grid.getChildren().clear();
        Update();


    }//Boutton supprimer
@FXML
    void ajouterEvent(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/ajouttravail.fxml")); //créer une nouvelle instance de FXMLLoader
        Parent root = loader.load(); // Charger le contenu du fichier FXML
    Scene scene = new Scene(root);// Créer une nouvelle scène à partir de la racine de l'interface
    Stage stage=new Stage();
        stage.setScene(scene); // Définir la scène créer comme scène principale

    Stage stageAjouter = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setTitle("Ajouter travail");
    stageAjouter.close();
    stage.show();
    } //Boutton ajouter


    @FXML
    void search() {
        String searchTerm = searchField.getText().toLowerCase(); // Récupérer le terme de recherche saisi par l'utilisateur
        List<Travail> filteredList = new ArrayList<>(); // Créer une liste pour stocker les éléments filtrés

        // Parcourir la liste des travaux
        for (Travail tv : TravailList) {
            // Vérifier si le terme de recherche est contenu dans la description ou le type du travail
            if (tv.getType().toLowerCase().contains(searchTerm)) {
                // Si oui, ajouter le travail à la liste filtrée
                //..startsWith(searchTerm)
                filteredList.add(tv);
            }
        }

        // Nettoyer la grille actuelle
        grid.getChildren().clear();

        // Afficher les travaux filtrés
        int c = 0;
        int l = 0;
        try {
            for (int i = 0; i < filteredList.size(); i++) {// affichage des cartes
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/TravailCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                TravailCardController cardcontroller = fxmlLoader.getController();
                cardcontroller.setData(filteredList.get(i), myListener);
                if (c > 3) {
                    c = 0;
                    l++;
                }
                grid.add(anchorPane, c++, l);
//grid weight
                grid.setMinWidth(134);
                grid.setPrefWidth(134);
                grid.setMaxWidth(134);//
//height
                grid.setMinHeight(112);
                grid.setPrefHeight(112);
                grid.setMaxHeight(112);//
                grid.setLayoutY(10);


                GridPane.setMargin(anchorPane, new Insets(175, 0, 0, 70));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }






}