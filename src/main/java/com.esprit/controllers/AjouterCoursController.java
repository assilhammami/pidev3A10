package com.esprit.controllers;

import com.esprit.models.cours;
import com.esprit.services.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.*;
import javafx.stage.Stage;

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




    }


    @FXML
    void afficher(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheCours.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        stage.show();

    }



}
