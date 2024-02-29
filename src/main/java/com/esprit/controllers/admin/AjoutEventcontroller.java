package com.esprit.controllers.admin;

import com.esprit.models.Event;
import com.esprit.services.EventService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class AjoutEventcontroller {

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfcapa;

    @FXML
    private TextField tfdate;

    @FXML
    private TextField tfdes;

    @FXML
    private TextField tfpla;

    @FXML
    private TextField tfimage;


    @FXML
    void addEvent(ActionEvent event) throws IOException {
        if (isInputValid()) {
            EventService ev = new EventService();
            ev.ajouter(new Event(tfNom.getText(), tfdate.getText(), tfdes.getText(), Integer.parseInt(tfcapa.getText()), tfpla.getText(), tfimage.getText()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Event ajoutée");
            alert.setContentText("Event ajoutée !");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs correctement !");
            alert.show();
        }


    }

    private boolean isInputValid() throws IOException {
        // Vérifier si tous les champs sont remplis
        if (tfNom.getText().isEmpty() || tfdate.getText().isEmpty() || tfdes.getText().isEmpty() || tfcapa.getText().isEmpty() || tfpla.getText().isEmpty() || tfimage.getText().isEmpty()) {
            return false;
        }

        // Vérifier si le nom commence par une lettre
        String nom = tfNom.getText().trim();
        if (!Character.isLetter(nom.charAt(0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Le nom doit commencer par une lettre.");
            alert.show();
            return false;
        }

        // Vérifier si la date est au format attendu (jj/mm/aaaa) et est supérieure ou égale à la date d'aujourd'hui
        String datePattern = "\\d{2}/\\d{2}/\\d{4}";
        if (!tfdate.getText().matches(datePattern)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format de date");
            alert.setContentText("Le format de la date est incorrect. Utilisez le format jj/mm/aaaa.");
            alert.show();
            return false;
        }

        LocalDate dateAujourdhui = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateSaisie = LocalDate.parse(tfdate.getText(), formatter);

        if (dateSaisie.isBefore(dateAujourdhui)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de date");
            alert.setContentText("La date doit être égale ou postérieure à aujourd'hui.");
            alert.show();
            return false;
        }

        // Ajoutez d'autres validations au besoin
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/AfffichageEvent.fxml"));
        Parent root =loader.load(); // initialise la vue qui sera affichée à l'utilisateur
        tfNom.getScene().setRoot(root);
        return true;

    }




    @FXML
    void afficherEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/AfffichageEvent.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        Stage stageAjouter = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageAjouter.close();
        stage.show();
    }
    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                java.nio.file.Path resourcesDir = Paths.get("src/main/resources/images");
                if (!Files.exists(resourcesDir)) {
                    Files.createDirectories(resourcesDir);
                }

                java.nio.file.Path imagePath = resourcesDir.resolve(selectedFile.getName());
                Files.copy(selectedFile.toPath(), imagePath, StandardCopyOption.REPLACE_EXISTING);

                tfimage.setText(selectedFile.getName());
                System.out.println(tfimage);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to upload image.");
                alert.showAndWait();
            }
        }
    }

}

