package com.esprit.controllers.Artiste;

import com.esprit.models.Archive;
import com.esprit.models.StatusTravail;
import com.esprit.models.Travail;
import com.esprit.services.ArchiveService2;
import com.esprit.services.TravailService2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AjoutArchiveArtisteController {

    @FXML
    private TextArea tfDescription;
    @FXML
    private TextField tfType;

    Travail t;


    public void setTravail(Travail t)
    {
        this.t = t;
    }

    @FXML
    void annulerEvent(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Artiste/AfficheTravailArtiste.fxml")); //créer une nouvelle instance de FXMLLoader
        Parent root = loader.load(); // Charger le contenu du fichier FXML
        Scene scene = new Scene(root);// Créer une nouvelle scène à partir de la racine de l'interface
        Stage stage=new Stage();
        stage.setScene(scene); // Définir la scène créer comme scène principale

        Stage stageAjouter = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageAjouter.close();
        stage.show();

    }


    @FXML
    void addArchive(ActionEvent event) throws IOException {
        // Récupérer la date d'aujourd'hui
        LocalDate aujourdhui = LocalDate.now();

        // Vérifier si tous les champs sont remplis
        if (tfDescription.getText().isEmpty() || tfType.getText().isEmpty()) {
            // Afficher un message d'erreur si un champ est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.show();
            return; // Sortir de la méthode, l'ajout ne peut pas être effectué
        }


               try {
            // Ajouter le travail avec les données saisies
                   ArchiveService2 service = new ArchiveService2();
                   Archive a = new Archive();
                   a.setDescription(tfDescription.getText());
                   a.setIdU(9);
                   a.setPath(tfType.getText());
                   a.setTravail(t);
                   Date d = Date.valueOf(aujourdhui);
                   a.setDateCreation(d);
                   service.ajouter(a);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Archive ajouté");
            alert.setContentText("Archive ajouté avec succès !");
            alert.show();
        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion de texte en nombre
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Le prix doit être un nombre entier !");
            alert.show();
        }




        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Artiste/AfficheTravailArtiste.fxml")); //créer une nouvelle instance de FXMLLoader
        Parent root =loader.load(); // initialise la vue qui sera affichée à l'utilisateur
        tfDescription.getScene().setRoot(root); // change la racine de la scène actuelle pour afficher la nouvelle vue chargée


    }
}
