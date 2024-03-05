package com.esprit.controllers;

import com.esprit.models.Publication;
import com.esprit.models.commentaire;
import com.esprit.services.PublicationService;
import com.esprit.services.commentaireService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AjoutCommentaireController {
    @FXML
    private TextField iduser;

    @FXML
    private Button tfajouter;

    @FXML
    private TextArea contenu;

    @FXML
    private TextField note;


    private int idPublicationSelectionnee;
    private commentaire commentaireToModify;

    public void setIdPublicationSelectionnee(int idPublicationSelectionnee) {
        this.idPublicationSelectionnee = idPublicationSelectionnee;
    }
    @FXML
    void confirmer(ActionEvent event) throws IOException {
        String noteText = note.getText();

        // Vérifier si le champ de note est vide
        if (noteText.isEmpty()) {
            showAlert("Veuillez saisir une note.");
            return;
        }

        try {
            // Convertir la note en entier
            int noteValue = Integer.parseInt(noteText);

            // Vérifier si la note est numérique et est dans la plage de 0 à 5
            if (noteValue < 0 || noteValue > 5) {
                showAlert("La note doit être un nombre entre 0 et 5 (inclus).");
                return;
            }

            // Continuer avec le reste de votre logique
            int idPublication = idPublicationSelectionnee;
            System.out.println(idPublication);
            commentaireService cs = new commentaireService();
            cs.ajouter(new commentaire(contenu.getText(), noteValue, idPublication, Integer.parseInt(iduser.getText())));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Commentaire ajouté");
            alert.setContentText("Commentaire ajouté !");
            alert.show();
            ((Stage) tfajouter.getScene().getWindow()).close();
        } catch (NumberFormatException e) {
            // Gérer l'exception si la conversion en entier échoue
            showAlert("Veuillez saisir une valeur numérique valide pour la note.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation de la note");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void upload(ActionEvent event) {


    }


    }













