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
import org.controlsfx.control.Rating;

public class ModifCommentaire {

    @FXML
    private TextArea contenu;

    @FXML
    private TextField iduser;

    @FXML
    private Rating rating;


    @FXML
    private Button tfsubmit;
    private commentaire commentaireToModify;

    @FXML
    void submit(ActionEvent event) {
        // Récupérer les valeurs des champs
        String contenuText = contenu.getText();

        // Récupérer la nouvelle note depuis le composant Rating
        int nouvelleNote = (int) rating.getRating();

        try {
            // Mettre à jour le commentaire
            commentaireToModify.setContenu(contenuText);
            commentaireToModify.setNote(nouvelleNote);

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

        } catch (Exception e) {
            showAlert("Une erreur s'est produite lors de la modification du commentaire.");
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
            rating.setRating(commentaireToModify.getNote());
            contenu.setText(commentaireToModify.getContenu());
        }
    }
}
