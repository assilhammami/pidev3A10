package com.esprit.controllers.Client;

import com.esprit.controllers.Artiste.DetailsTravailArtisteController;
import com.esprit.models.Travail;
import com.esprit.services.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TravailCardClientController implements Initializable {

    @FXML
    private Label titre;
    @FXML
    private Label date_demande;

    @FXML
    private Label date_fin;

    private Travail travail;
    private MyListener myListener;
    @FXML
    private HBox heartIconContainer ;

    public void setData(Travail travail, MyListener myListener) {
        this.travail = travail;

        this.myListener = myListener;
        titre.setText(travail.getTitre());
        date_fin.setText(travail.getDate_fin().toString());
        date_demande.setText(travail.getDate_demande().toString());


    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(travail);
    }

    public HBox getContainerForHeartIcon() {
        return heartIconContainer;
    }

    @FXML
    void voirdetails(ActionEvent event) throws IOException {
        // Charger le fichier FXML pour la vue de détails
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/DetailtravailClient.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();

        // Récupérer le contrôleur de la vue de détails
        DetailsTravailClientController detailsController = fxmlLoader.getController();

        // Passer les données du travail sélectionné au contrôleur de la vue de détails
        detailsController.setData(travail);

        // Créer une nouvelle fenêtre pour afficher les détails
        Stage stage = new Stage();
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Détails du Travail");
        stage.show();
    }





}