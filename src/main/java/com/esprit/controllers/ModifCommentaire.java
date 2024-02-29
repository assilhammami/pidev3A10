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
        commentaireToModify.setContenu(contenu.getText());
        commentaireToModify.setNote(Integer.parseInt(note.getText()));
        commentaireService commentaireService =new commentaireService();
        commentaireService.modifier(commentaireToModify);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Publication modifiée");
        alert.setContentText("Publication modifiée !");
        alert.show();




        // Fermer la fenêtre de modification
        ((Stage) tfsubmit.getScene().getWindow()).close();

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
