package com.esprit.controllers;
import com.esprit.models.Event;

import com.esprit.services.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Evvvcontroller implements Initializable {
    @FXML
    private Label nom;
    private   Event e;
    private MyListener myListener;
    @FXML
    private ImageView img;


    public void setData(Event e, MyListener myListener) {
        this.e = e;

        this.myListener = myListener;
        nom.setText(e.getNom());
        Image image = new Image(e.getImage());
        img.setImage(image);

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(e);
    }
}
