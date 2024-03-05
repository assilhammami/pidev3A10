package com.esprit.controllers;

import com.esprit.models.commentaire;
import com.esprit.services.commentaireService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifCommentaire {

    @FXML
    private TextArea contenu;

    @FXML
    private TextField iduser;

    @FXML
    private TextField note;

    @FXML
    private Button tfsubmit;
    private commentaire commentaireToModify;

    @FXML
    void submit(ActionEvent event) {
        // Récupérer les valeurs des champs
        String contenuText = contenu.getText();
        String noteText = note.getText();

        // Vérifier si le champ de note est vide
        if (noteText.isEmpty()) {
            showAlert("Veuillez saisir une note.");
            return;
        }

        try {
            // Convertir la note en entier
            int noteValue = Integer.parseInt(noteText);

            // Vérifier si la note est entre 0 et 5
            if (noteValue < 0 || noteValue > 5) {
                showAlert("La note doit être un nombre entre 0 et 5.");
                return;
            }

            // Mettre à jour le commentaire
            commentaireToModify.setContenu(contenuText);
            commentaireToModify.setNote(noteValue);

            // Appeler le service pour effectuer la modification
            commentaireService commentaireService = new commentaireService();
            commentaireService.modifier(commentaireToModify);

            // Afficher une boîte de dialogue d'information
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Commentaire modifiée");
            alert.setContentText("Commentaire modifiée !");
            alert.show();

            // Fermer la fenêtre de modification
            ((Stage) tfsubmit.getScene().getWindow()).close();

        } catch (NumberFormatException e) {
            showAlert("Veuillez saisir une note valide (entier entre 0 et 5).");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setContentText(message);
        alert.show();
    }




    public void setCommentaireToModify(commentaire commentaireToModify) {
        this.commentaireToModify = commentaireToModify;
        // Mettez à jour vos champs d'interface graphique avec les données du commentaireToModify
        if (commentaireToModify != null) {
            iduser.setText(String.valueOf(commentaireToModify.getIduser()));
            note.setText(String.valueOf(commentaireToModify.getNote()));
            contenu.setText(commentaireToModify.getContenu());
        }
    }
}
