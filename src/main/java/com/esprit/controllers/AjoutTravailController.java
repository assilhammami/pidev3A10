package com.esprit.controllers;

import com.esprit.models.Travail;
import com.esprit.models.StatusTravail;
import com.esprit.services.TravailService2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AjoutTravailController {

    @FXML
    private TextArea tfDescription;
    
    @FXML
    private DatePicker tfDatedemande;

    @FXML
    private DatePicker tfDatefin;

    @FXML
    private TextField tfPrix;

    @FXML
    private TextField tfType;

    @FXML
    private TextField tfStatus;

    @FXML
    void addTravail(ActionEvent event) throws IOException {
        TravailService2 ts = new TravailService2();

        Date datedemande = Date.valueOf(tfDatedemande.getValue()); // localdate en date.sql
        Date datefin = Date.valueOf(tfDatefin.getValue());  // localdate en date.sql
            ts.ajouter(new Travail(tfDescription.getText(), Integer.parseInt(tfPrix.getText()),tfType.getText(),StatusTravail.Attente,datedemande , datefin));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Travail ajoutée");
            alert.setContentText("Travail ajoutée !");
            alert.show();

        }
}
