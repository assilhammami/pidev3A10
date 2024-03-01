package com.esprit.controllers.Client;

import com.esprit.controllers.Admin.CoursCardAdminController;
import com.esprit.models.NoteCours;
import com.esprit.models.avis;
import com.esprit.models.cours;
import com.esprit.services.AvisService;
import com.esprit.services.CoursService;
import com.esprit.services.MyListener;
import com.esprit.services.UserDataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class DetailsCoursClientController implements Initializable {

    @FXML
    private TextArea Description;

    @FXML
    private TextField Nom;

    @FXML
    private GridPane grid;

    @FXML
    private ListView<avis> listview;
    private ObservableList<avis> avisObservableList;

    @FXML
    private TextField datepub;

    @FXML
    private ScrollPane scroll1;
    MyListener myListener;

    private  int userId;
    private int id_cours;
    private int selectedCoursId;

    public cours selectedCours;
    private List<cours> coursList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Assurez-vous que selectedCoursId est correctement initialisé
        selectedCoursId = UserDataManager.getInstance().getSelectedCoursId();

        // Créer une instance de votre service AvisService
        AvisService avisService = new AvisService();

        // Récupérer la liste d'avis à partir de votre service
        List<avis> aviss = avisService.getByIDCours(selectedCoursId);

        // Convertir la liste en une liste observable
        avisObservableList = FXCollections.observableArrayList(aviss);

        // Définir la liste observable comme la source de données de votre ListView
        listview.setItems(avisObservableList);

        // Définir un cell factory personnalisé pour afficher les avis dans le ListView
        listview.setCellFactory(param -> new ListCell<avis>() {
            @Override
            protected void updateItem(avis item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    // Créer un VBox pour organiser la note et le commentaire
                    VBox vbox = new VBox();
                    vbox.setSpacing(5); // Espacement entre les éléments

                    // Créer un Label pour afficher la note
                    Label noteLabel = new Label("Note : " + item.getNote().getValue());
                    noteLabel.setStyle("-fx-font-weight: bold;"); // Style pour mettre en gras

                    // Créer un Label pour afficher le commentaire
                    Label commentaireLabel = new Label("Commentaire : " + item.getCommentaire());
                    commentaireLabel.setStyle("-fx-font-weight: bold;"); // Style pour mettre en gras

                    // Ajouter les labels au VBox
                    vbox.getChildren().addAll(noteLabel, commentaireLabel);

                    // Définir le contenu de la cellule comme le VBox
                    setGraphic(vbox);
                }
            }
        });

        // Mettre à jour la vue
        Update();
    }


    private void setChosenCours(cours cours) {
        selectedCours = cours;
        Nom.setText(cours.getNom());
        Description.setText(cours.getDescription());
        datepub.setText(String.valueOf(cours.getDate_pub()));

        }
    public void Update() {
        CoursService cs = new CoursService();
        coursList.clear();
        grid.getChildren().clear();
        selectedCoursId = UserDataManager.getInstance().getSelectedCoursId();
        coursList.addAll(cs.getByIDUser(selectedCoursId));
        myListener = new MyListener() {
            @Override
            public void onClickListener(cours cours) {
                setChosenCours(cours);
            }
        };

        int column = 0;
        int row = 1;
        try {
            for (cours ev : coursList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Admin/CoursCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CoursCardAdminController evvvcontroller = fxmlLoader.getController();
                evvvcontroller.setData(ev, myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setHgap(10);
        grid.setVgap(-20);
        scroll1.setFitToWidth(true);
        scroll1.setContent(grid);
        grid.requestLayout();
    }



}
