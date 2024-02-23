package com.esprit.controllers;

import com.esprit.models.cours;
import com.esprit.services.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CoursCardController implements Initializable {

    @FXML
    private ImageView img;

    @FXML
    private Label nom;
    private cours cours;
    private MyListener myListener;


    public void setData(cours cours, MyListener myListener) {
        this.cours = cours;

        this.myListener = myListener;
        nom.setText(cours.getNom());
        Image image = new Image(cours.getImage());
        img.setImage(image);

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(cours);
    }



}
