package com.esprit.controllers.Artiste;

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

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TravailCardArtisteController {


    @FXML
    private Label titre;
    @FXML
    private Label date_demande;

    @FXML
    private Label date_fin;

    private Travail travail;
    private MyListener myListener;


    public void setData(Travail travail, MyListener myListener) {
        this.travail = travail;

        this.myListener = myListener;
        titre.setText(travail.getTitre());
        date_fin.setText(travail.getDate_fin().toString());
        date_demande.setText(travail.getDate_demande().toString());


    }

    public void initialize() {

    }



    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(travail);
    }


    public void ajoutArchive(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Artiste/AjoutArchiveArtiste.fxml")); //créer une nouvelle instance de FXMLLoader
        Parent root = loader.load(); // Charger le contenu du fichier FXML
        AjoutArchiveArtisteController controller = loader.getController();
        controller.setTravail(travail);
        Scene scene = new Scene(root);// Créer une nouvelle scène à partir de la racine de l'interface
        Stage stage=new Stage();
        stage.setScene(scene); // Définir la scène créer comme scène principale

        Stage stageAjouter = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageAjouter.close();
        stage.show();
    }
}
