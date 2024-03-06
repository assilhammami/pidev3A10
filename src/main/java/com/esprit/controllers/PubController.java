package com.esprit.controllers;
import com.esprit.models.Publication;

import com.esprit.models.commentaire;
import com.esprit.services.MyListener;
import com.esprit.services.commentaireService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PubController implements Initializable {
    @FXML
    private Label ratingLabel;
    @FXML
    private Label titre;
    private  Publication p;
    private MyListener myListener;
    @FXML
    private ImageView img;
    public int getIdPublication() {
        return this.p.getId();
    }


    public void setData(Publication e, MyListener myListener) throws SQLException {
        this.p = e;

        this.myListener = myListener;
        titre.setText(p.getTitre());
        Image image = loadImage(p.getImage());
        img.setImage(image);
        double averageRating = calculateAverageRating(e);

        // Mettre à jour le Label représentant les étoiles
        updateRatingLabel(averageRating);

    }
    private double calculateAverageRating(Publication publication) throws SQLException {
        // Calculer la moyenne des notes en utilisant les commentaires de la publication
        // Vous devez avoir une méthode dans votre service pour récupérer les commentaires par ID de publication
        commentaireService commentaireService = new commentaireService();
        List<commentaire> commentaires = commentaireService.getCommentairesByPublicationId(publication.getId());

        if (commentaires.isEmpty()) {
            return 0.0;
        }

        int sumOfRatings = commentaires.stream().mapToInt(commentaire::getNote).sum();
        return (double) sumOfRatings / commentaires.size();
    }

    private void updateRatingLabel(double averageRating) {
        // Mettre à jour le texte du Label en fonction de la moyenne des notes
        ratingLabel.setText(generateStarsText(averageRating));
        ratingLabel.setStyle(generateColorStyle(averageRating));
    }
    private String generateStarsText(double rating) {
        // Générer le texte représentant les étoiles
        int numberOfStars = 5;
        int numberOfColoredStars = (int) Math.floor(rating);
        double remainder = rating - numberOfColoredStars;

        StringBuilder starsText = new StringBuilder();
        for (int i = 0; i < numberOfStars; i++) {
            if (i < numberOfColoredStars) {
                starsText.append("\u2605"); // Étoile pleine
            } else if (i == numberOfColoredStars) {
                if (remainder <= 0.25) {
                    starsText.append("\u2606"); // Étoile vide
                } else if (remainder >= 0.75) {
                    starsText.append("\u2605"); // Étoile pleine
                } else {
                    starsText.append("\u00BD"); // Demi-étoile
                }
            } else {
                starsText.append("\u2606"); // Étoile vide
            }
        }

        return starsText.toString();
    }

    private String generateColorStyle(double rating) {
        // Générer le style CSS pour la couleur des étoiles
        String color = "gold"; // Couleur d'or par défaut

        return "-fx-text-fill: " + color + ";";
    }

    private String generateStarString(double rating) {
        int numberOfStars = (int) Math.round(rating);

        StringBuilder starString = new StringBuilder();
        for (int i = 0; i < numberOfStars; i++) {
            starString.append("★"); // Caractère étoile pleine
        }

        return starString.toString();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ratingLabel.setText("");

    }

    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(p);
    }
    public  Image loadImage(String filePath) {
        try {
            File file = new File(filePath);
            return new Image(file.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }}
}