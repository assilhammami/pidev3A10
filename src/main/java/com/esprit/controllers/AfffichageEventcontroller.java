package com.esprit.controllers;
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
import com.esprit.services.EventService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;

public class AfffichageEventcontroller implements Initializable {

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
        image.setText(ev.getImage());
        Image image;

        image = new Image(ev.getImage());
        img.setImage(image);

    }

    void Update() {
         Event e;
         EventService ev= new EventService();
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
                 fxmlLoader.setLocation(getClass().getResource("/evvv.fxml") );
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
        Update();
     }

     @FXML
     void modifier(ActionEvent event) throws SQLException {
         Event e = new Event(id_evnt, nom.getText(),date.getText(), description.getText(), Integer.parseInt(capacity.getText()), place.getText(), image.getText());
         EventService es = new EventService();
         es.modifier(e);
         eventsList.clear();
         grid.getChildren().clear();
         Update();
         System.out.println("ghvdshgv");
         System.out.println(e.getNom());
     }



    @FXML
    void supprimer(MouseEvent event) {
        EventService ev = new EventService();
        ev.supprimer(id_evnt);
        eventsList.clear();
        grid.getChildren().clear();
      Update();
    }


    @FXML
    void ajouterEvent(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutEvent.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        Stage stageAfficher = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(" ajouter Event");
        stageAfficher.close();

        stage.show();
    }
    @FXML
    void search( ) {
        String searchTerm = searchField.getText().toLowerCase(); // Récupérer le terme de recherche saisi par l'utilisateur
        List<Event> filteredList = new ArrayList<>(); // Créer une liste pour stocker les éléments filtrés

        // Parcourir la liste des travaux
        for (Event ev : eventsList) {
            // Vérifier si le terme de recherche est contenu dans la description ou le type du travail
            if (ev.getNom().toLowerCase().contains(searchTerm) ){
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
                fxmlLoader.setLocation(getClass().getResource("/evvv.fxml"));
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
    }}