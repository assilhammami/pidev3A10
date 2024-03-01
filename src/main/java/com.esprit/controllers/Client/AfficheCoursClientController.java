package com.esprit.controllers.Client;
import com.esprit.models.NoteCours;
import com.esprit.models.avis;
import com.esprit.services.AvisService;
import com.esprit.controllers.Admin.CoursCardAdminController;
import com.esprit.services.UserDataManager;
import javafx.event.ActionEvent;
import com.esprit.services.CoursService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import com.esprit.models.cours;
import com.esprit.services.MyListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;


public class AfficheCoursClientController implements Initializable {



    @FXML
    private GridPane grid;

    @FXML
    private ImageView img;

    @FXML
    private TextField nom;
    @FXML
    private ScrollPane scroll;

    private int id_cours;
    @FXML
    private TextField commentaire;
    @FXML
    private TextField searchField;
    @FXML
    ToggleGroup toggleGroup;
    @FXML
    private Button voirdetails;

    MyListener myListener;
    private NoteCours noteCours;
    private  int userId;
    private  int selectedCoursId;

    public cours selectedCours;
    private List<cours> coursList = new ArrayList<>();

    private void setChosenCours(cours cours) {
        selectedCours = cours;
        id_cours = cours.getId();
        nom.setText(cours.getNom());
        selectedCoursId =cours.getId();
        UserDataManager.getInstance().setSelectedCoursId(selectedCoursId);
        voirdetails.setVisible(true);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        try {

            File file = new File("src/main/resources/images/" + cours.getImage());
            String imageUrl = file.toURI().toURL().toExternalForm();
            Image image = new Image(imageUrl);
            img.setImage(image);
            System.out.println(cours.getImage());
        } catch (Exception ex) {
            ex.printStackTrace();

        }}

    public void Update() {
        CoursService cs= new CoursService();
        coursList.clear();
        grid.getChildren().clear();
        coursList.addAll(cs.afficher());
        myListener= new MyListener() {
            @Override
            public void onClickListener(cours cours) {
                setChosenCours(cours);
                selectedCoursId = cours.getId();
            }
        };

        int column = 0;
        int row = 1;
        try {
            for (cours  ev : coursList) {
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
        scroll.setFitToWidth(true);
        scroll.setContent(grid);
        grid.requestLayout();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            search();
        });

        Update();
        voirdetails.setVisible(false);
    }


    @FXML
    void search() {
        String searchTerm = searchField.getText().toLowerCase(); // Récupérer le terme de recherche saisi par l'utilisateur
        List<cours> filteredList = new ArrayList<>(); // Créer une liste pour stocker les éléments filtrés

        filteredList = coursList.stream().filter(cc -> cc.getNom().toLowerCase().contains(searchTerm)).collect(Collectors.toCollection(ArrayList::new));

        // Nettoyer la grille actuelle
        grid.getChildren().clear();

        // Afficher les cours filtrés
        int column = 0;
        int row = 1;
        try {
            for (cours ev : filteredList) {
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

        grid.setHgap(10);
        grid.setVgap(-20);

    }
    @FXML
    void ajouteravis(ActionEvent event) throws IOException {
          AvisService as = new AvisService();
        System.out.println(toggleGroup.getToggles());
          RadioButton radioButton = (RadioButton) toggleGroup.getSelectedToggle();
        System.out.println(radioButton);
        switch(Integer.parseInt(radioButton.getText())) {
            case 1:
                noteCours = NoteCours.UNE;
                break;
            case 2:
                noteCours = NoteCours.DEUX;
                break;
            case 3:
                noteCours = NoteCours.TROIS;
                break;
            case 4:
                noteCours = NoteCours.QUATRE;
                break;
            case 5:
                noteCours = NoteCours.CINQ;
                break;
            default:
                System.out.println(noteCours);
                // Code à exécuter si aucun des cas ne correspond
                break;}
        System.out.println(noteCours);
                userId = 10;
                avis a=new avis(noteCours,commentaire.getText());
                a.setIdu(userId);
                a.setIdc(id_cours);
            as.ajouter(a);

    }

    @FXML
    void voirdetails(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/DétailsCours.fxml"));
        System.out.println();
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        stage.show();



    }
}
