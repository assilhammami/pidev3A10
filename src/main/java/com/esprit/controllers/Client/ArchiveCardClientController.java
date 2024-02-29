package com.esprit.controllers.Client;

import com.esprit.controllers.Artiste.AjoutArchiveArtisteController;
import com.esprit.models.Archive;
import com.esprit.models.Travail;
import com.esprit.services.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class ArchiveCardClientController {

    @FXML
    Label titre;
    @FXML
    Label descLabel;
    Archive archive;
    public void setData(Archive archive) {
        this.archive = archive;
        titre.setText(archive.getTravail().getTitre());
        descLabel.setText(archive.getDescription());
    }

    public void initialize() {

    }



}
