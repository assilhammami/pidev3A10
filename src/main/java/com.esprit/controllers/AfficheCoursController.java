package com.esprit.controllers;
import javafx.event.ActionEvent;
import com.esprit.services.CoursService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.esprit.models.cours;
import com.esprit.services.MyListener;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;

public class AfficheCoursController implements Initializable {

    @FXML
    private TextField date_pub;

    @FXML
    private TextArea description;

    @FXML
    private GridPane grid;

    @FXML
    private TextField image;

    @FXML
    private ImageView img;

    @FXML
    private TextField nom;

    private int id_cours;
    @FXML
    private TextField searchField;

    //
    MyListener myListener;

    public cours selectedCours;
    private List<cours> coursList = new ArrayList<>();

    private void setChosenCours(cours cours) {
        selectedCours = cours;
        id_cours = cours.getId();
        nom.setText(cours.getNom());
        date_pub.setText((String.valueOf(cours.getDate_pub())));
        description.setText(cours.getDescription());
        image.setText(cours.getImage());
        Image image;
        image = new Image(cours.getImage());
        img.setImage(image);
    }

    void Update() {
        cours cc;
        CoursService cs= new CoursService();
        coursList.clear();
        grid.getChildren().clear();
        coursList.addAll(cs.afficher());
        myListener = new MyListener() {
            @Override
            public void onClickListener(cours c) {
                setChosenCours(c);

            }
        };
        int c = 0;
        int l = 0;
        try {
            for (int i = 0; i < coursList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CoursCard.fxml") );
                AnchorPane anchorPane = fxmlLoader.load();
                CoursCardController cardcontroller = fxmlLoader.getController();
                cardcontroller.setData(coursList.get(i), myListener);
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
        // Ajouter un écouteur d'événements sur le champ de recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Appeler la fonction de recherche à chaque fois que le texte du champ de recherche change
            search();
        });

        // Initialiser l'affichage des travaux
        Update();
    }
//boutton modifier
    @FXML
    void modifier(ActionEvent event) throws SQLException {
        cours cours = new cours(id_cours, nom.getText(), description.getText(), Date.valueOf(date_pub.getText()),  image.getText());
        CoursService cs = new CoursService();
        cs.modifier(cours);
        coursList.clear();
        grid.getChildren().clear();
        Update();
    }

//Boutton supprimer
    @FXML
    void supprimer(MouseEvent event) {
        CoursService cs = new CoursService();
        cs.supprimer(id_cours);
        coursList.clear();
        grid.getChildren().clear();
        Update();
    }
//Boutton ajouter
    @FXML
    void ajouter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCours.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        stage.show();
    }
    @FXML
    void search() {
        String searchTerm = searchField.getText().toLowerCase(); // Récupérer le terme de recherche saisi par l'utilisateur
        List<cours> filteredList = new ArrayList<>(); // Créer une liste pour stocker les éléments filtrés

        // Parcourir la liste des travaux
        for (cours cc : coursList) {
            // Vérifier si le terme de recherche est contenu dans la description ou le type du travail
            if (cc.getNom().toLowerCase().contains(searchTerm))  {
                // Si oui, ajouter le travail à la liste filtrée
                filteredList.add(cc);
            }
        }

        // Nettoyer la grille actuelle
        grid.getChildren().clear();

        // Afficher les travaux filtrés
        int c = 0;
        int l = 0;
        try {
            for (int i = 0; i < filteredList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CoursCard.fxml") );
                AnchorPane anchorPane = fxmlLoader.load();
                CoursCardController cardcontroller = fxmlLoader.getController();
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