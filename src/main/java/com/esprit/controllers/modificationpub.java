package com.esprit.controllers;

import com.esprit.models.Publication;
import com.esprit.services.PublicationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class modificationpub {
    @FXML
    private ImageView ancienpost;

    @FXML
    private TextField tfdescription;

    @FXML
    private TextField tfimage;

    @FXML
    private TextField tftitre;
    private Publication selectedPublication;

    @FXML
    void confirmer(ActionEvent event) {

        String newDescription = tfdescription.getText();
        String newImage = tfimage.getText();
        String newTitre = tftitre.getText();

        // Mettre à jour la publication avec les nouvelles valeurs
        selectedPublication.setDescription(newDescription);
        selectedPublication.setImage(newImage);
        selectedPublication.setTitre(newTitre);


        // Appeler le service (ou la classe d'accès aux données) pour mettre à jour la publication dans la base de données
        PublicationService publicationService = new PublicationService();
        publicationService.modifier(selectedPublication);

        // Fermer la fenêtre de modification si nécessaire
        Stage stage = (Stage) tfdescription.getScene().getWindow();
        stage.close();



        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Publication modifiée");
        alert.setContentText("Publication modifiée !");
        alert.show();


    }




    public void initData(Publication selectedPublication) {
        this.selectedPublication = selectedPublication;

        // Initialiser les champs avec les données de la publication
        tfdescription.setText(selectedPublication.getDescription());
        tfimage.setText(selectedPublication.getImage());
        tftitre.setText(selectedPublication.getTitre());
        Image ancienpostImage = loadImage(selectedPublication.getImage());
        ancienpost.setImage(ancienpostImage);
    }

    // Méthode pour charger une image depuis un fichier
    private Image loadImage(String filePath) {
        try {
            File file = new File(filePath);
            return new Image(file.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
    @FXML
    void uploadimage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            tfimage.setText(selectedFile.getAbsolutePath());
            Image image = new Image(selectedFile.toURI().toString());
            ancienpost.setImage(image);
            ancienpost.setVisible(true);
        } else {
            System.out.println("file is not valid");
        }

    }
}




