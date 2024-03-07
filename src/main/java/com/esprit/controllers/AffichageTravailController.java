package com.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AffichageTravailController {

    @FXML
    private Label lbDatedemande;

    @FXML
    private Label lbDatefin;

    @FXML
    private Label lbDescription;

    @FXML
    private Label lbPrix;

    @FXML
    private Label lbType;


    public void setLbDescription(String Description) {
        lbDescription.setText(Description);
    }
    public void setlbDatedemande(String Date_demande) {
        lbDatedemande.setText(Date_demande);
    }
    public void setlbDatefin(String Date_fin) {
        lbDatefin.setText(Date_fin);
    }
    public void setlbPrix(String prix) {lbPrix.setText(prix);}
    public void setlbType(String Type) {
        lbType.setText(Type);
    }



}