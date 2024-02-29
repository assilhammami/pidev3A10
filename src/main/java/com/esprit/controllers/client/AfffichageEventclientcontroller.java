package com.esprit.controllers.client;

import com.esprit.controllers.admin.Evvvcontroller;
import com.esprit.models.Event;
import com.esprit.models.Reservation;
import com.esprit.models.Status;
import com.esprit.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import com.esprit.services.EventService;
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
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.esprit.models.Event;
import com.esprit.services.MyListener;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;

    public class AfffichageEventclientcontroller implements Initializable {
        @FXML
        private TextField nbplaces;
        @FXML
        private TextField datee;
        @FXML
        private VBox chosen1;
        @FXML
        private TextArea description;

        @FXML
        private TextField nom;

        @FXML
        private TextField capacity;

        @FXML
        private VBox chosen;

        @FXML
        private TextField date;

        @FXML
        private GridPane grid;

        @FXML
        private ImageView img;

        @FXML
        private TextField place;
        @FXML
        private TextField image;
        private int userId;

        private int id_evnt;
        //
        MyListener myListener;


        public Event selectedProduct;
        private List<Event> eventsList = new ArrayList<>();
        @FXML
        private TextField searchField;

        private void setChosenProduct(Event ev) {
            selectedProduct = ev;
            id_evnt = ev.getId();
            nom.setText(ev.getNom());
            date.setText((ev.getDate()));
            description.setText(ev.getDescription());
            capacity.setText(Integer.toString(ev.getCapacity()));
            place.setText(ev.getPlace());
            chosen.setLayoutX(1050);
            chosen.setVisible(true);
            chosen1.setVisible(false);
            ImageView imageView = new ImageView();
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(true);
            try {

                File file = new File("src/main/resources/images/" + ev.getImage());
                String imageUrl = file.toURI().toURL().toExternalForm();
                Image image = new Image(imageUrl);
                img.setImage(image);
                System.out.println(ev.getImage());
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }

        void Update() {
            Event e;
            EventService ev = new EventService();
            eventsList.clear();
            grid.getChildren().clear();
            eventsList.addAll(ev.afficher());
            myListener = new MyListener() {
                @Override
                public void onClickListener(Event p) {
                    setChosenProduct(p);

                }
            };

            int c = 0;
            int l = 0;
            try {
                for (int i = 0; i < eventsList.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/admin/evvv.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    Evvvcontroller evvvcontroller = fxmlLoader.getController();
                    evvvcontroller.setData(eventsList.get(i), myListener);
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
            chosen.setVisible(false);
            Update();
            userId = UserDataManager.getInstance().getUserId();
            System.out.println(userId);
            System.out.println(id_evnt);
        }


        private boolean isInputValid() {
            // Vérifier si tous les champs sont remplis
            if (nom.getText().isEmpty() || date.getText().isEmpty() || description.getText().isEmpty() || capacity.getText().isEmpty() || place.getText().isEmpty()) {
                return false;
            }

            // Vérifier d'autres critères de validation au besoin

            return true;
        }


        @FXML
        void search() {
            String searchTerm = searchField.getText().toLowerCase(); // Récupérer le terme de recherche saisi par l'utilisateur
            List<Event> filteredList = new ArrayList<>(); // Créer une liste pour stocker les éléments filtrés

            // Parcourir la liste des travaux
            for (Event ev : eventsList) {
                // Vérifier si le terme de recherche est contenu dans la description ou le type du travail
                if (ev.getNom().toLowerCase().contains(searchTerm)) {
                    // Si oui, ajouter le travail à la liste filtrée
                    filteredList.add(ev);
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
                    fxmlLoader.setLocation(getClass().getResource("/admin/evvv.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    Evvvcontroller evvvcontroller = fxmlLoader.getController();
                    evvvcontroller.setData(filteredList.get(i), myListener);
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

        @FXML
        private void ajouter(ActionEvent event) throws IOException {
            System.out.println(id_evnt);
            System.out.println(userId);
            if (isInputValid()) {

                if (id_evnt != 0) {
                    Reservation re =new Reservation (datee.getText(), Status.attente, Integer.parseInt(nbplaces.getText()));
                    ReservationService ev = new ReservationService();
                    re.setUser(userId);
                    re.setEvent(id_evnt);
                    ev.ajouter(re);

                    System.out.println(ev);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Reservation ajoutée");
                    alert.setContentText("Reservation ajoutée !");
                    alert.show();
                } else {
                    System.err.println("id_evnt is null.");
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez remplir tous les champs correctement !");
                alert.show();
            }
        }

        @FXML
        void reserver(ActionEvent event) {
            chosen1.setVisible(true);
            System.out.println(userId);
            System.out.println(id_evnt);
        }
    }

