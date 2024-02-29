package com.esprit.controllers.admin;
import com.esprit.models.Event;

import com.esprit.services.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;

import java.io.File;
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
        ImageView imageView = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        try {

            File file = new File("src/main/resources/images/" + e.getImage());
            String imageUrl = file.toURI().toURL().toExternalForm();
            Image image = new Image(imageUrl);
            img.setImage(image);
            System.out.println(e.getImage());
        } catch (Exception ex) {
            ex.printStackTrace();
    }}
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(e);
    }
}
