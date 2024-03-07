package com.esprit.controllers;

import com.esprit.models.Publication;
import com.esprit.services.PublicationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AffichageToutesPublications {
    @FXML
    private Label lbdate;

    @FXML
    private Label lbdescription;

    @FXML
    private ImageView lbimage;

    @FXML
    private Label lbtitre;

    @FXML
    private ListView<String> listoftitles;
    @FXML
    private Text tf;
    @FXML
    private Label tftitre;
    @FXML
    private Label tfdescription;




    @FXML
    private Button tfmodifier;
    @FXML
    private Button tfsupprimer;


    @FXML
    void addcom(ActionEvent event) throws IOException {
        // Chargez la nouvelle interface d'ajout de commentaire
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutCommentaire.fxml"));
        Parent root = loader.load();

        // Obtenez le contrôleur associé à la nouvelle interface
        AjoutCommentaireController ajoutCommentaireController = loader.getController();

        // Affichez la nouvelle interface dans une nouvelle fenêtre ou une boîte de dialogue
        // Vous devrez peut-être définir votre propre logique pour afficher la nouvelle interface selon vos besoins
        // par exemple, si vous souhaitez une nouvelle fenêtre :
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }


    public void initialize() {
        PublicationService ps = new PublicationService();
        // Supposons que vous ayez une méthode pour obtenir les titres des publications depuis la base de données
        List<String> publicationTitles = ps.getPublicationTitlesForLoggedInUser();

        // Initialiser la ListView avec les titres
        listoftitles.getItems().addAll(publicationTitles);
        tf.setVisible(false);
        tfmodifier.setVisible(false);
        tfsupprimer.setVisible(false);
        tftitre.setVisible(false);
        tfdescription.setVisible(false);


        lbimage.setVisible(false);
        lbtitre.setVisible(false);
        lbdate.setVisible(false);
        lbdescription.setVisible(false);
        listoftitles.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println(newValue);
                // Utilisez le titre pour obtenir les détails de la publication depuis la base de données

                Publication selectedPublication = ps.getPublicationByTitle(newValue);

                // Affichez les détails de la publication dans la partie droite de la page
                Image image = loadImage(selectedPublication.getImage());
                lbimage.setImage(image);
                lbtitre.setText(selectedPublication.getTitre());
                lbdate.setText(selectedPublication.getDatepublication().toString());
                lbdescription.setText(selectedPublication.getDescription());
                tf.setVisible(true);
                tfmodifier.setVisible(true);
                tfsupprimer.setVisible(true);
                tftitre.setVisible(true);
                tfdescription.setVisible(true);

                lbimage.setVisible(true);
                lbtitre.setVisible(true);
                lbdate.setVisible(true);
                lbdescription.setVisible(true);
            }
        });

    }

    @FXML
    void addmodif(ActionEvent event) throws IOException {
        PublicationService ps = new PublicationService();


        Publication selectedPublication = ps.getPublicationByTitle(listoftitles.getSelectionModel().getSelectedItem());

        // Charger l'interface de modification
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modificationpub.fxml"));
        Parent root = loader.load();

        // Obtenez le contrôleur associé à l'interface de modification
        modificationpub modifController = loader.getController();

        // Passez les données de la publication à l'interface de modification
        modifController.initData(selectedPublication);
        Stage stage2 = (Stage) tfdescription.getScene().getWindow();
        stage2.close();

        // Afficher l'interface de modification dans une nouvelle fenêtre
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();



    }



    @FXML
    void delete(ActionEvent event) throws IOException {


        String selectedTitle = listoftitles.getSelectionModel().getSelectedItem();

        if (selectedTitle != null) {
            PublicationService ps = new PublicationService();
            // Récupérer l'ID de la publication à partir du titre
            int publicationId = ps.getPublicationIdByTitle(selectedTitle);

            if (publicationId != -1) {
                // Supprimer la publication de la base de données
                ps.supprimer(publicationId);
                // Rafraîchir la ListView après la suppression
                listoftitles.getItems().remove(selectedTitle);




                // Effacer les détails de la publication (vous devrez implémenter cela en fonction de votre interface)

            }




        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Publication supprimée");
        alert.setContentText("Publication supprimée !");
        alert.show();

     }
    public  Image loadImage(String filePath) {
        try {
            File file = new File(filePath);
            return new Image(file.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }}


}











