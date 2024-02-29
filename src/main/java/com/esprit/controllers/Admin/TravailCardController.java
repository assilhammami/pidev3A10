package com.esprit.controllers.Admin;

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

public class TravailCardController implements Initializable {

    @FXML
    private Label Titre;


    private Travail travail;
    private MyListener myListener;


    public void setData(Travail travail, MyListener myListener) {
        this.travail = travail;

        this.myListener = myListener;
        Titre.setText(travail.getTitre());


    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(travail);
    }



}