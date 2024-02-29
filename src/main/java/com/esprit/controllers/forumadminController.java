package com.esprit.controllers;

import com.esprit.models.Publication;
import com.esprit.services.MyListener;
import com.esprit.services.PublicationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class forumadminController implements Initializable {
    @FXML
    private Label Date_creation;

    @FXML
    private Label Description;



    @FXML
    private GridPane grid;

    @FXML
    private ImageView img;

    @FXML
    private Button pub;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchfiled;

    @FXML
    private Button tfcom;

    @FXML
    private Label titre;
    @FXML
    private Button admindelete;
    @FXML
    void deletepost(ActionEvent event) {
        PublicationService ps= new PublicationService();
        ps.supprimer(id_pub);



    }

    @FXML
    void mespub(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageToutesPublications.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Liste de mes Publications");
        stage.show();


    }
    @FXML
    void ajoutbtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutPublication.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Ajout de Publication");
        stage.show();


    }






    private int id_pub;
    //
    MyListener myListener;


    public Publication selectedPublication;
    private List<Publication> publicationList = new ArrayList<>();
    private void setChosenProduct(Publication p) {
        selectedPublication = p;
        id_pub = p.getId();
        titre.setText(p.getTitre());
        System.out.println(p.getTitre());
        Description.setText(p.getDescription());
        Date_creation.setText("Date creation: "+p.getDatepublication());
        Image image;
        image = loadImage(p.getImage());
        img.setImage(image);
        updateCommentButtonVisibility();
        updateDeletetButtonVisibility();



    }

    private void updateDeletetButtonVisibility() {
        if (selectedPublication != null) {
            admindelete.setVisible(true);
        } else {
            admindelete.setVisible(false);
        }
    }

    @FXML
    void comlist(ActionEvent event) throws IOException, SQLException {
        int idPublicationSelectionnee = getIdPublicationSelectionnee();
        // System.out.println(idPublicationSelectionnee);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/listecommentairesadmin.fxml"));
        Parent root = loader.load();
        // Passez l'id de la publication sélectionnée au contrôleur de la liste des commentaires
        listecommentairesadminController listCommentairesadminController = loader.getController();
        listCommentairesadminController.setIdPublicationSelectionnee(idPublicationSelectionnee);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Liste des Commentaires");
        stage.show();

    }
    private void updateCommentButtonVisibility() {
        if (selectedPublication != null) {
            tfcom.setVisible(true);
        } else {
            tfcom.setVisible(false);
        }
    }
    public void Update() {

        PublicationService ps= new PublicationService();
        publicationList.clear();
        grid.getChildren().clear();
        publicationList.addAll(ps.afficher());
        myListener= new MyListener() {
            @Override
            public void onClickListener(Publication p) {
                setChosenProduct(p);
            }

        };

        int column = 0;
        int row = 1;
        try {
            for (Publication ev : publicationList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/pub.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                PubController pubcontroller = fxmlLoader.getController();
                pubcontroller.setData(ev, myListener);

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
        grid.setVgap(10);
        scroll.setFitToWidth(true);
        scroll.setContent(grid);
        grid.requestLayout();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfcom.setVisible(false);
        admindelete.setVisible(false);

        Update();



    }
    public int getIdPublicationSelectionnee() {
        return id_pub;

    }



    @FXML
    public void search(KeyEvent event) {
        String searchTerm = searchfiled.getText().toLowerCase(); // Récupérer le terme de recherche saisi par l'utilisateur
        List<Publication> filteredList = new ArrayList<>(); // Créer une liste pour stocker les éléments filtrés

        // Parcourir la liste des travaux
        for (Publication pp : publicationList) {
            // Vérifier si le terme de recherche est contenu dans la description ou le type du travail
            if (pp.getTitre().toLowerCase().contains(searchTerm))  {
                // Si oui, ajouter le travail à la liste filtrée
                filteredList.add(pp);
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
                fxmlLoader.setLocation(getClass().getResource("/pub.fxml") );
                AnchorPane anchorPane = fxmlLoader.load();
                PubController pubcontroller = fxmlLoader.getController();
                pubcontroller.setData(filteredList.get(i), myListener);
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
        } catch (IOException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    public  Image loadImage(String filePath) {
        try {
            File file = new File(filePath);
            return new Image(file.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @FXML
    void refreshForum(ActionEvent event) {
        Update();
    }

}
