package com.esprit.controllers;

import com.esprit.models.Num_chapitre;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AffichageCoursController {

    @FXML
    private Label lbDescription;

    @FXML
    private Label lbNom;

    @FXML
    private Label lbNumchap;
    public void setLbNom(String nom){
        lbNom.setText(nom);
    }
    public void setLbDescription(String description){
        lbDescription.setText(description);
    }
    public void setLbNumchap(Num_chapitre num_chap){
        lbNumchap.setText(num_chap.toString());
    }

}
