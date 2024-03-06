package com.esprit.controllers;

import com.esprit.models.Publication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML
    void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/test.fxml"));
            Parent root = loader.load();

            // Vous pouvez éventuellement passer des données ou initialiser des contrôleurs ici

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Forum");
            stage.show();

            // Fermez la scène actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les exceptions de manière appropriée dans une application réelle
        }

    }



}
