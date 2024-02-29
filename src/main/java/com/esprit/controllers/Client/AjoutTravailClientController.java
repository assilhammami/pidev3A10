package com.esprit.controllers.Client;

import com.esprit.models.Travail;
import com.esprit.models.StatusTravail;
import com.esprit.services.TravailService2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AjoutTravailClientController implements Initializable {

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
    private TextField tfTitre;


    @FXML
    void annulerEvent(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Client/AfficheTravailClient.fxml")); //créer une nouvelle instance de FXMLLoader
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
        // Récupérer la date d'aujourd'hui
        LocalDate aujourdhui = LocalDate.now();

        // Vérifier si tous les champs sont remplis
        if (tfDatedemande.getValue() == null || tfDatefin.getValue() == null ||
                tfDescription.getText().isEmpty() || tfPrix.getText().isEmpty() || tfType.getText().isEmpty() || tfTitre.getText().isEmpty() ) {
            // Afficher un message d'erreur si un champ est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.show();
            return; // Sortir de la méthode, l'ajout ne peut pas être effectué
        }

        // Convertir les valeurs de texte en types appropriés
        Date datedemande = Date.valueOf(tfDatedemande.getValue());
        Date datefin = Date.valueOf(tfDatefin.getValue());

        // Vérifier si la date de demande est supérieure ou égale à aujourd'hui
        if (datedemande.toLocalDate().isBefore(aujourdhui)) {
            // Afficher un message d'erreur si la date de demande est antérieure à aujourd'hui
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de date");
            alert.setHeaderText(null);
            alert.setContentText("La date de demande doit être égale ou postérieure à aujourd'hui !");
            alert.show();
            return; // Sortir de la méthode, l'ajout ne peut pas être effectué
        }

        // Vérifier si la date de fin est supérieure à aujourd'hui
        if (datefin.toLocalDate().isBefore(aujourdhui)) {
            // Afficher un message d'erreur si la date de fin est antérieure à aujourd'hui
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de date");
            alert.setHeaderText(null);
            alert.setContentText("La date de fin doit être postérieure à aujourd'hui !");
            alert.show();
            return; // Sortir de la méthode, l'ajout ne peut pas être effectué
        }

        // Vérifier si la date de fin est postérieure à la date de demande
        if (datefin.toLocalDate().isBefore(datedemande.toLocalDate())) {
            // Afficher un message d'erreur si la date de fin est antérieure à la date de demande
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de date");
            alert.setHeaderText(null);
            alert.setContentText("La date de fin doit être postérieure à la date de demande !");
            alert.show();
            return; // Sortir de la méthode, l'ajout ne peut pas être effectué
        }






        try {
            // Convertir le prix en entier
            int prix = Integer.parseInt(tfPrix.getText());

            // Ajouter le travail avec les données saisies
            TravailService2 ts = new TravailService2();
            ts.ajouter(new Travail(tfDescription.getText(), prix, tfType.getText(), StatusTravail.Attente, datedemande, datefin, tfTitre.getText(),10));

            // Afficher un message de confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Travail ajouté");
            alert.setContentText("Travail ajouté avec succès !");
            alert.show();
        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion de texte en nombre
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Le prix doit être un nombre entier !");
            alert.show();
        }




        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Client/AfficheTravailClient.fxml")); //créer une nouvelle instance de FXMLLoader
        Parent root =loader.load(); // initialise la vue qui sera affichée à l'utilisateur
        tfDescription.getScene().setRoot(root); // change la racine de la scène actuelle pour afficher la nouvelle vue chargée


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfDatedemande.setValue(LocalDate.now());
    }
}
