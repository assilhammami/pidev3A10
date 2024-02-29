package com.esprit.controllers;

import com.esprit.models.Publication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AffichagePublicationController {

    @FXML
    private Label lbdate;

    @FXML
    private Label lbdescription;

    @FXML
    private Label lbtitre;
    @FXML
    private ImageView lbimage;

    public void setLbimage(Image imageUrl) {

        lbimage.setImage(imageUrl);
        lbimage.setVisible(true);
    }

    @FXML

    public void setLbtitre(String nom) {
        lbtitre.setText(nom);
    }



    public void setLbdescription(String prenom) {
        lbdescription.setText(prenom);
    }
    public void initialize() {
        // Obtenir la date actuelle
        LocalDate currentDate = LocalDate.now();

        // Formater la date comme une chaîne de caractères (par exemple, "18/02/2024")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);

        // Mettre à jour le texte de la Label avec la date actuelle
        lbdate.setText(formattedDate);


    }



}
