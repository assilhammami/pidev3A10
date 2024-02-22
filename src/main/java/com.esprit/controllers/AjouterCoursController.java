package com.esprit.controllers;

import com.esprit.models.cours;
import com.esprit.services.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.Date ;


public class AjouterCoursController  {
    @FXML
    private TextField tfDescription;
    @FXML
    private TextField tfNom;
    @FXML
    private DatePicker tfDatepub;
    @FXML
    private TextField tfImage;


    @FXML
    void addCours(ActionEvent event) throws IOException {
        CoursService cs = new CoursService();
        Date date_pub = Date.valueOf(tfDatepub.getValue());
        cs.ajouter(new cours(tfNom.getText(),tfDescription.getText(),date_pub,tfImage.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cours ajouté ");
        alert.setContentText("Cours ajouté avec succès !");
        alert.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageCours.fxml"));
        Parent root = loader.load();
        tfNom.getScene().setRoot(root);

        //get the controller
        AffichageCoursController acc = loader.getController();
        acc.setLbNom(tfNom.getText());
        acc.setLbDescription(tfDescription.getText());
        acc.setLbDatepub(String.valueOf(Date.valueOf(tfDatepub.getValue())));
        acc.setLbImage(tfImage.getText());







    }






}
