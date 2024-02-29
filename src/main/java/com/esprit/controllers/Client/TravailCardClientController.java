package com.esprit.controllers.Client;

import com.esprit.models.Travail;
import com.esprit.services.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class TravailCardClientController implements Initializable {

    @FXML
    private Label titre;
    @FXML
    private Label date_demande;

    @FXML
    private Label date_fin;

    private Travail travail;
    private MyListener myListener;


    public void setData(Travail travail, MyListener myListener) {
        this.travail = travail;

        this.myListener = myListener;
        titre.setText(travail.getTitre());
        date_fin.setText(travail.getDate_fin().toString());
        date_demande.setText(travail.getDate_demande().toString());


    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(travail);
    }



}