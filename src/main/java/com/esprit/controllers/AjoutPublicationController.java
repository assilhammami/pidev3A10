package com.esprit.controllers;

import com.esprit.models.Publication;
import com.esprit.services.PublicationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class AjoutPublicationController {
    @FXML
    private ImageView post;
    @FXML
    private Button uploadbutton;


    @FXML
    private TextArea tfdescription;

    @FXML
    private TextField tfimage;

    @FXML
    private TextField tftitre;

    @FXML
    void addPublication(ActionEvent event) throws IOException {
        if ( tfimage.getText().isEmpty()) {
            // Affichez une alerte si l'un des champs est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir l'URL de l'image.");
            alert.showAndWait();
        }
        else {
            PublicationService ps = new PublicationService();
            ps.ajouter(new Publication(tfimage.getText(), tftitre.getText(), tfdescription.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Publication ajoutée");
            alert.setContentText("Publication ajoutée !");
            alert.show();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichagePublication.fxml"));
            Parent root = loader.load();
            tftitre.getScene().setRoot(root);
            AffichagePublicationController apc = loader.getController();
            apc.setLbtitre(tftitre.getText());
            apc.setLbdescription(tfdescription.getText());
            apc.setLbimage(post.getImage());
        }







    }
    @FXML
    void upload(ActionEvent event) {
         /*String url=urlimage.getText();
        Image image = new Image(url);
        Photo_de_profil.setImage(image);
        Photo_de_profil.setVisible(true);*/
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            tfimage.setText(selectedFile.getAbsolutePath());
            Image image = new Image(selectedFile.toURI().toString());
            post.setImage(image);
            post.setVisible(true);
        } else {
            System.out.println("file is not valid");
        }

        }

    }


