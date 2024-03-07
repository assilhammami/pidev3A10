package com.esprit.controllers;

import com.esprit.models.Travail;
import com.esprit.models.StatusTravail;
import com.esprit.services.TravailService2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    void annulerEvent(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficheTravail.fxml")); //créer une nouvelle instance de FXMLLoader
        Parent root = loader.load(); // Charger le contenu du fichier FXML
        Scene scene = new Scene(root);// Créer une nouvelle scène à partir de la racine de l'interface
        Stage stage=new Stage();
        stage.setScene(scene); // Définir la scène créer comme scène principale

        Stage stageAjouter = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageAjouter.close();
        stage.show();



    }






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


        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficheTravail.fxml")); //créer une nouvelle instance de FXMLLoader
        Parent root =loader.load(); // initialise la vue qui sera affichée à l'utilisateur
        tfDescription.getScene().setRoot(root); // change la racine de la scène actuelle pour afficher la nouvelle vue chargée

        //get the controller
        AffichageTravailController atc = loader.getController(); //Obtention du contrôleur associé à la vue FXML :
        atc.setLbDescription(tfDescription.getText()); //Passage de données au contrôleur :
        atc.setlbPrix(tfPrix.getText());
        atc.setlbType(tfType.getText());
        atc.setlbDatedemande(String.valueOf(Date.valueOf(tfDatedemande.getValue())));
        atc.setlbDatefin(String.valueOf(Date.valueOf(tfDatefin.getValue())));



        }
}
