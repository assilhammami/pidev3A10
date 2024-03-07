package com.esprit.controllers.Artiste;

import com.esprit.controllers.Artiste.AjoutArchiveArtisteController;
import com.esprit.models.Archive;
import com.esprit.models.Travail;
import com.esprit.services.MyListener;
import com.esprit.services.TravailService2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ArchiveCardArtisteController {

    @FXML
    Label titre;
    @FXML
    Label descLabel;
    @FXML
    ImageView img;
    Archive archive;
    public void setData(Archive archive) {
        this.archive = archive;
        titre.setText(archive.getTravail().getTitre());
        descLabel.setText(archive.getDescription());
        ImageView imageView = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.minHeight(300);
        imageView.maxHeight(300);
        imageView.maxWidth(300);
        imageView.minHeight(300);
        imageView.prefHeight(300);

        imageView.setPreserveRatio(true);
        try {

            File file = new File("src/main/resources/images/" + archive.getPath());
            String imageUrl = file.toURI().toURL().toExternalForm();
            Image image = new Image(imageUrl);


            img.minHeight(200);
            img.maxHeight(200);
            img.maxWidth(200);
            img.minHeight(200);
            img.prefHeight(200);
            img.prefWidth(200);
            img.setFitHeight(200);
            img.setFitWidth(200);
            img.setImage(image);

            System.out.println(archive.getPath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }}



    public void initialize() {

    }



}
