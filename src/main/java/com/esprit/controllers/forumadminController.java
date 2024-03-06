package com.esprit.controllers;

import com.esprit.models.Publication;
import com.esprit.models.commentaire;
import com.esprit.services.MyListener;
import com.esprit.services.PublicationService;
import com.esprit.services.commentaireService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class forumadminController implements Initializable {
    @FXML
    private Label Date_creation;

    @FXML
    private Label Description;



    @FXML
    private GridPane grid;

    @FXML
    private ImageView img;

    @FXML
    private Button pub;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchfiled;

    @FXML
    private Button tcom;
    @FXML
    private TextArea contenu;
    @FXML
    private Button sousmettre;
    @FXML
    private Rating starrating;

    @FXML
    private Label titre;
    @FXML
    private Button admindelete;
    @FXML
    private ImageView iconefavori;
    private boolean estFavori = false;
    @FXML
    private ChoiceBox<?> filtreOptions;
    @FXML
    private DatePicker datePicker;
    @FXML
    void filterByDate(ActionEvent event) {
        LocalDate selectedDate = datePicker.getValue();

        PublicationService ps = new PublicationService();
        List<Publication> filteredList;

        if (selectedDate != null) {
            filteredList = ps.rechercherParDate(Date.valueOf(selectedDate).toLocalDate());
        } else {
            // Si aucune date n'est sélectionnée, récupérez toutes les publications
            filteredList = ps.afficher();
        }

        grid.getChildren().clear();
        updateGrid(filteredList);
    }


    public void updateGrid(List<Publication> publications) {
        myListener = new MyListener() {
            @Override
            public void onClickListener(Publication p) {
                setChosenProduct(p);
            }
        };

        int column = 0;
        int row = 1;
        try {
            for (Publication ev : publications) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/pub.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                PubController pubcontroller = fxmlLoader.getController();
                pubcontroller.setData(ev, myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setHgap(10);
        grid.setVgap(10);
        scroll.setFitToWidth(true);
        scroll.setContent(grid);
        grid.requestLayout();
    }

    // Utilisation de la méthode updateGrid dans votre code
    @FXML
    void filterByOption(ActionEvent event) {
        String selectedOption = (String) filtreOptions.getValue();
        if (selectedOption == null || selectedOption.isEmpty()) {
            showAlert("Veuillez sélectionner une option de filtre.");
            return;
        }

        List<Publication> filteredList = new ArrayList<>();

        switch (selectedOption) {
            case "Date de création (Nouveau - Ancien)":
                Collections.sort(publicationList, Comparator.comparing(Publication::getDatepublication).reversed());
                filteredList.addAll(publicationList);
                break;

            case "Date de création (Ancien - Nouveau)":
                Collections.sort(publicationList, Comparator.comparing(Publication::getDatepublication));
                filteredList.addAll(publicationList);
                break;

            // Ajoutez d'autres cas selon vos besoins

            default:
                showAlert("Option de filtre non prise en charge : " + selectedOption);
                return;
        }

        // Mettez à jour la grille avec la nouvelle liste triée
        grid.getChildren().clear();
        updateGrid(filteredList);
    }
    @FXML
    void toggleFavori(MouseEvent event) {
        // Vérifier si une publication est sélectionnée
        if (selectedPublication != null) {
            // Basculer l'état de favori pour la publication sélectionnée
            selectedPublication.setFavori(!selectedPublication.isFavori());

            // Mettre à jour l'icône en fonction de l'état de favori
            if (selectedPublication.isFavori()) {
                iconefavori.setImage(new Image(getClass().getResource("/css/coeur rouge.png").toExternalForm()));
            } else {
                iconefavori.setImage(new Image(getClass().getResource("/css/img.png").toExternalForm()));
            }

            // Ajoutez ici le code pour mettre à jour votre modèle de données avec l'état de favori
            // ...

            // Sauvegardez les modifications dans la base de données (exemple hypothétique)
            PublicationService publicationService = new PublicationService();
            publicationService.modifier(selectedPublication);
        } else {
            showAlert("Veuillez sélectionner une publication avant de marquer comme favori.");
        }
    }

    @FXML
    void nzidcom(ActionEvent event) {
        int noteValue = (int) starrating.getRating();
        if (noteValue == 0) {
            showAlert("Please rate the publication.");
            return;
        }

        // Vérifier si une publication est sélectionnée
        if (selectedPublication != null) {
            int idPublication = selectedPublication.getId();
            System.out.println(idPublication);
            commentaireService cs = new commentaireService();
            cs.ajouter(new commentaire(contenu.getText(), noteValue, idPublication, 91));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Commentaire ajouté");
            alert.setContentText("Commentaire ajouté !");
            alert.show();

            // Charger la scène listecommentaires.fxml
            loadListeCommentairesScene(idPublication, event);
        } else {
            showAlert("Veuillez sélectionner une publication avant d'ajouter un commentaire.");
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ... Autres méthodes ...

    // Méthode pour charger la scène listecommentaires.fxml
    private void loadListeCommentairesScene(int idPublication, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listecommentaires.fxml"));
            Parent root = loader.load();

            // Passez l'id de la publication sélectionnée et le chemin de l'image au contrôleur de la liste des commentaires
            listecommentairesController listCommentairesController = loader.getController();
            listCommentairesController.initializeData(idPublication, selectedPublication.getImage());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Liste des Commentaires");
            stage.show();

            // Fermez la scène actuelle
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void deletepost(ActionEvent event) {
        PublicationService ps= new PublicationService();
        ps.supprimer(id_pub);
        refreshForum();



    }
    public void refreshForum() {
        // Réinitialiser la sélection actuelle
        selectedPublication = null;
        id_pub = 0;
        titre.setText("");
        Description.setText("");
        Date_creation.setText("");
        img.setImage(null);
        starrating.setVisible(false);
        contenu.setVisible(false);
        sousmettre.setVisible(false);
        tcom.setVisible(false);
        iconefavori.setVisible(false);
        admindelete.setVisible(false);

        // Vider la liste des publications
        publicationList.clear();

        // Mettre à jour le GridPane
        Update();
    }


    @FXML
    void mespub(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageToutesPublications.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Liste de mes Publications");
        stage.show();


    }
    @FXML
    void ajoutbtn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutPublicationadmin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Ajout de Publication");
        stage.show();
        Stage stageAfficher = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageAfficher.close();


    }






    private int id_pub;
    //
    MyListener myListener;


    public Publication selectedPublication;
    private List<Publication> publicationList = new ArrayList<>();
    private void setChosenProduct(Publication p) {
        selectedPublication = p;
        id_pub = p.getId();
        titre.setText(p.getTitre());
        System.out.println(p.getTitre());
        Description.setText(p.getDescription());
        Date_creation.setText("Date creation: "+p.getDatepublication());
        Image image;
        image = loadImage(p.getImage());
        img.setImage(image);
        starrating.setVisible(true);
        contenu.setVisible(true);
        sousmettre.setVisible(true);
        tcom.setVisible(true);
        iconefavori.setVisible(true);
        if (p.isFavori()) {
            iconefavori.setImage(new Image(getClass().getResource("/css/coeur rouge.png").toExternalForm()));
        } else {
            iconefavori.setImage(new Image(getClass().getResource("/css/img.png").toExternalForm()));
        }
        updateDeletetButtonVisibility();



    }

    private void updateDeletetButtonVisibility() {
        if (selectedPublication != null) {
            admindelete.setVisible(true);
        } else {
            admindelete.setVisible(false);
        }
    }

    @FXML
    void comlist(ActionEvent event) throws IOException, SQLException {
        int idPublicationSelectionnee = getIdPublicationSelectionnee();
        // System.out.println(idPublicationSelectionnee);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/listecommentairesadmin.fxml"));
        Parent root = loader.load();
        // Passez l'id de la publication sélectionnée au contrôleur de la liste des commentaires
        listecommentairesadminController listCommentairesadminController = loader.getController();
        listCommentairesadminController.setIdPublicationSelectionnee(idPublicationSelectionnee);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Liste des Commentaires");
        stage.show();

    }

    public void Update() {

        PublicationService ps= new PublicationService();
        publicationList.clear();
        grid.getChildren().clear();
        publicationList.addAll(ps.afficher());
        myListener= new MyListener() {
            @Override
            public void onClickListener(Publication p) {
                setChosenProduct(p);
            }

        };

        int column = 0;
        int row = 1;
        try {
            for (Publication ev : publicationList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/pub.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                PubController pubcontroller = fxmlLoader.getController();
                pubcontroller.setData(ev, myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setHgap(10);
        grid.setVgap(10);
        scroll.setFitToWidth(true);
        scroll.setContent(grid);
        grid.requestLayout();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        starrating.setVisible(false);
        contenu.setVisible(false);
        sousmettre.setVisible(false);
        tcom.setVisible(false);
        iconefavori.setVisible(false);
        admindelete.setVisible(false);
        searchfiled.textProperty().addListener((observable, oldValue, newValue) -> {
            // Appeler la fonction de recherche à chaque fois que le texte du champ de recherche change
            search();
        });

        Update();



    }
    public int getIdPublicationSelectionnee() {
        return id_pub;

    }



    @FXML
    public void search() {
        String searchTerm = searchfiled.getText().toLowerCase(); // Récupérer le terme de recherche saisi par l'utilisateur
        List<Publication> filteredList = new ArrayList<>(); // Créer une liste pour stocker les éléments filtrés

        // Parcourir la liste des travaux
        for (Publication pp : publicationList) {
            // Vérifier si le terme de recherche est contenu dans la description ou le type du travail
            if (pp.getTitre().toLowerCase().contains(searchTerm))  {
                // Si oui, ajouter le travail à la liste filtrée
                filteredList.add(pp);
            }
        }

        // Nettoyer la grille actuelle
        grid.getChildren().clear();

        // Afficher les travaux filtrés
        int c = 0;
        int l = 0;
        try {
            for (int i = 0; i < filteredList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/pub.fxml") );
                AnchorPane anchorPane = fxmlLoader.load();
                PubController pubcontroller = fxmlLoader.getController();
                pubcontroller.setData(filteredList.get(i), myListener);
                if (c > 3) {
                    c = 0;
                    l++;
                }
                grid.add(anchorPane, c++, l);
                //grid weight
                grid.setMinWidth(134);
                grid.setPrefWidth(134);
                grid.setMaxWidth(134);//
                //height
                grid.setMinHeight(112);
                grid.setPrefHeight(112);
                grid.setMaxHeight(112);//
                grid.setLayoutY(10);
                GridPane.setMargin(anchorPane, new Insets(175, 0, 0, 70));
            }
        } catch (IOException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    public  Image loadImage(String filePath) {
        try {
            File file = new File(filePath);
            return new Image(file.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @FXML
    void showcomments(ActionEvent event) {
        int idPublication = getIdPublicationSelectionnee();
        loadListeCommentairesScene(idPublication, event);

    }


}
