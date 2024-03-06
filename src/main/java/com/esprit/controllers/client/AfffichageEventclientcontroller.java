package com.esprit.controllers.client;

import com.esprit.controllers.admin.Evvvcontroller;
import com.esprit.models.Event;
import com.esprit.models.Reservation;
import com.esprit.models.Status;
import com.esprit.objects.Post;
import com.esprit.objects.Reactions;
import com.esprit.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import com.esprit.services.EventService;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import com.esprit.services.MyListener1;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
        private int idreser=10;
        private long startTime = 0;
        //
        MyListener1 myListener;
        @FXML
        private ScrollPane scroll;
        private Reactions currentReaction;
        Event data;
        @FXML
        public VBox all;


        @FXML
        private ImageView imgProfile;

        @FXML
        private Label username;

        @FXML
        private ImageView imgVerified;


        @FXML
        private ImageView audience;

        @FXML
        private Label caption;

        @FXML
        private ImageView imgPost;

        @FXML
        private Label nbReactions;

        @FXML
        private Label nbComments;

        @FXML
        private Label nbShares;

        @FXML
        private HBox reactionsContainer;

        @FXML
        private ImageView imgLike;

        @FXML
        private ImageView imgLove;

        @FXML
        private ImageView imgCare;

        @FXML
        private ImageView imgHaha;

        @FXML
        private ImageView imgWow;

        @FXML
        private ImageView imgSad;

        @FXML
        private ImageView imgAngry;

        @FXML
        private HBox likeContainer;

        @FXML
        private ImageView imgReaction;

        @FXML
        private Label reactionName;
        @FXML
        public ImageView mostliked1;

        @FXML
        public ImageView mostliked2;

        @FXML
        public ImageView mostliked3;

        @FXML
        private Button events;



        public Event selectedProduct;
        private List<Event> eventsList = new ArrayList<>();
        private List<Reservation> reservationsList = new ArrayList<>();
        @FXML
        private TextField searchField;
        private LikesService LS = new LikesService();
        @FXML
        private Button supprime;

        private int user_liked = 0;
        private Post post;
        private int places;

        private void setChosenProduct(Event ev) {
            selectedProduct = ev;
            id_evnt = ev.getId();
            nom.setText(ev.getNom());
            date.setText((ev.getDate()));
            description.setText(ev.getDescription());
            capacity.setText(Integer.toString(ev.getCapacity()));
            place.setText(ev.getPlace());

            chosen.setVisible(true);
            chosen1.setVisible(false);
            chosen.setLayoutX(1050);
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

        public void Update() {
            EventService cs= new EventService();
            eventsList.clear();
            grid.getChildren().clear();
            eventsList.addAll(cs.afficher());
            myListener= new MyListener1() {
                @Override
                public void onClickListener(Event e) {
                    setChosenProduct(e);

                }
            };

            int column = 0;
            int row = 1;
            try {
                for (Event  ev : eventsList) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/admin/evvv.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    Evvvcontroller evvvcontroller = fxmlLoader.getController();
                    evvvcontroller.setData(ev, myListener);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    grid.add(anchorPane, column++, row);
                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setHgap(10);
            grid.setVgap(-20);
            scroll.setFitToWidth(true);
            scroll.setContent(grid);
            grid.requestLayout();
        }


        @Override
        public void initialize(URL url, ResourceBundle rb) {
            // Ajouter un écouteur d'événements sur le champ de recherche
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Appeler la fonction de recherche à chaque fois que le texte du champ de recherche change
                search();
            });
            chosen.setVisible(false);
            userId = UserDataManager.getInstance().getUserId();
            System.out.println(userId);
            System.out.println(id_evnt);
            Update();
            chosen1.setVisible(false);
            events.setVisible(false);
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
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML
        private void ajouter(ActionEvent event) throws IOException {
            System.out.println(id_evnt);
            System.out.println(userId);
            if (isInputValid()) {

                if (id_evnt != 0) {
                    Reservation re = new Reservation(datee.getText(), Status.attente, Integer.parseInt(nbplaces.getText()));
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
                alert.setContentText("Veuillez selectionner un event !");
                alert.show();
            }
        }

        @FXML
        void reserver(ActionEvent event) {
            chosen1.setVisible(true);
            System.out.println(userId);
            System.out.println(id_evnt);
        }





        @FXML
        void reservation(ActionEvent event) {
            // Votre code actuel pour afficher les réservations
            displayReservations();
            events.setVisible(true);
            nbplaces.setEditable(false);
            datee.setEditable(false);
        }

        @FXML
        void supprimer(ActionEvent event) {
            // Supprimer la réservation sélectionnée
            ReservationService ev = new ReservationService();
            ev.supprimer(idreser);
            // Réafficher la liste des réservations après la suppression
            displayReservations();
            // Masquer la zone sélectionnée
            chosen.setVisible(false);
        }

        private void displayReservations() {
            // Votre code actuel pour afficher les réservations
            ReservationService RS = new ReservationService();
            List<Reservation> R = RS.afficher_si_hakim(userId);
            EventService ev = new EventService();
            eventsList.clear();
            grid.getChildren().clear();
            eventsList.addAll(ev.afficher());
            chosen.setVisible(false);
            nbplaces.setEditable(false);
            datee.setEditable(false);

            myListener = new MyListener1() {
                @Override
                public void onClickListener(Event p) {
                    setChosenProduct(p);
                    chosen.setVisible(false);
                    chosen1.setVisible(true);
                }
            };

            int c = 0;
            int l = 0;
            try {
                for (int i = 0; i < R.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/admin/evvv.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    nbplaces.setEditable(false);
                    datee.setEditable(false);
                    Evvvcontroller evvvcontroller = fxmlLoader.getController();
                    evvvcontroller.setData(ev.afficher_si_hakim(R.get(i).getEvent()), myListener);
                    Label l1 = new Label(String.valueOf(R.get(i).getNbplaces()));
                    Label l2 = new Label(R.get(i).getDate());

                    if (c > 2) {
                        c = 0;
                        l++;
                    }
                    evvvcontroller.hakimvbox.getChildren().add(l1);
                    evvvcontroller.hakimvbox.getChildren().add(l2);
                    List<Reservation> finalR = R;
                    int finalI = i;

                    evvvcontroller.hakimvbox.setOnMouseClicked(event2 -> {
                        datee.setText(finalR.get(finalI).getDate());
                        nbplaces.setText(String.valueOf(finalR.get(finalI).getNbplaces()));
                        idreser = finalR.get(finalI).getId();
                        System.out.println(idreser);
                    });
                    grid.add(anchorPane, c++, l);
                    grid.setMinWidth(134);
                    grid.setPrefWidth(134);
                    grid.setMaxWidth(134);
                    grid.setMinHeight(112);
                    grid.setPrefHeight(112);
                    grid.setMaxHeight(112);
                    grid.setLayoutY(10);
                    GridPane.setMargin(anchorPane, new Insets(175, 0, 0, 70));
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setHgap(10);
            grid.setVgap(10);
            scroll.setFitToWidth(true);
            scroll.setContent(grid);
            grid.requestLayout();
        }
        @FXML
        void afficherr(ActionEvent event) {
         Update();

        }
        @FXML
        void modifierres(ActionEvent event) {
            // Rendre les champs accessibles pour permettre la modification
            nbplaces.setEditable(true);
            datee.setEditable(true);

            // Vérifier si l'identifiant de la réservation à modifier est valide
            if (idreser != 0) {
                // Déclarer une variable pour stocker la nouvelle réservation
                Reservation reservationAModifier = new Reservation(datee.getText(), Status.attente, Integer.parseInt(nbplaces.getText()));

                // Mettre à jour les champs de la réservation avec les nouvelles valeurs saisies par l'utilisateur
                reservationAModifier.setId(idreser);

                // Appeler la méthode de service pour enregistrer les modifications
                ReservationService reservationService = new ReservationService();
                reservationService.modifier(reservationAModifier);

                // Afficher un message de succès à l'utilisateur
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modification de la réservation");
                alert.setContentText("La réservation a été modifiée avec succès !");
                alert.show();

                // Réafficher la liste des réservations après la modification
                displayReservations();

                // Rendre les champs inaccessibles à nouveau
                nbplaces.setEditable(false);
                datee.setEditable(false);
            } else {
                System.err.println("L'identifiant de la réservation est invalide.");
            }
        }






        }



