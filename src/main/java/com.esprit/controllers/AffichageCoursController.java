package com.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Date;

public class AffichageCoursController {

    @FXML
    private Label lbDescription;
    @FXML
    private Label lbNom;
    @FXML
    private Label lbDatepub;
    @FXML
    private Label lbImage;

    public void setLbNom(String nom){
        lbNom.setText(nom);
    }
    public void setLbDescription(String description){
        lbDescription.setText(description);
    }

    public void setLbDatepub(String date_pub){ lbDatepub.setText(date_pub);}
    public void setLbImage(String image){lbImage.setText(image);}
}


