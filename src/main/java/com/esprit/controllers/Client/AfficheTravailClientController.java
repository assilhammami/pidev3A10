package com.esprit.controllers.Client;

import com.esprit.controllers.Admin.TravailCardController;
import com.esprit.models.Archive;
import com.esprit.models.StatusTravail;
import com.esprit.models.Travail;
import com.esprit.services.ArchiveService2;
import com.esprit.services.MyListener;
import com.esprit.services.TravailService2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AfficheTravailClientController implements Initializable {

    @FXML
    private VBox chosen;

    @FXML
    private DatePicker date_demande;

    @FXML
    private DatePicker date_fin;


    @FXML
    private TextArea description;

    @FXML
    private GridPane grid;

    @FXML
    private TextField prix;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField type;

    private int id_travail;

    @FXML
    private TextField titre;

    @FXML
    private TextField searchField; // Champ de texte pour la recherche

    int idUser;

    @FXML
    private Button ajoutBT;
    @FXML
    private Button modBT;
    @FXML
    private Button suppBT;
    ArchiveService2 service = new ArchiveService2();

    MyListener myListener;

    boolean travau = false;

    public Travail selectedTravail;
    private List<Travail> TravailList = new ArrayList<>();

    boolean loaded = false;

    private void setChosenTravail(Travail travail) {
        selectedTravail = travail;
        id_travail = travail.getId();
        description.setText(travail.getDescription());
        type.setText(travail.getType());
        prix.setText(String.valueOf(travail.getPrix()));
        date_fin.setValue(travail.getDate_fin().toLocalDate());
        date_demande.setValue(travail.getDate_demande().toLocalDate());
        titre.setText(travail.getTitre());
        System.out.println(travail.getTitre());


    } // travail selectionnée


    public void Update() {
        if(travau == false) {
            TravailService2 es = new TravailService2();

            TravailList.clear();


                chosen.setDisable(false);

                chosen.setVisible(true);
                ajoutBT.setVisible(true);
                ajoutBT.setDisable(false);
                modBT.setVisible(true);
                modBT.setDisable(false);
                suppBT.setVisible(true);
                suppBT.setDisable(false);
                grid.getChildren().clear();
                loaded = true;

            TravailList.addAll(es.afficher());
            myListener = new MyListener() {
                @Override
                public void onClickListener(Travail p) {
                    setChosenTravail(p);
                }
            };

            int column = 0;
            int row = 1;
            try {
                for (Travail ev : TravailList) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/Client/TravailCardClient.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    TravailCardClientController travailcontroller = fxmlLoader.getController();
                    travailcontroller.setData(ev, myListener);

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
            grid.setVgap(10);
            scroll.setFitToWidth(true);
            scroll.setContent(grid);
            grid.requestLayout();
        }
        else
        {
            List<Archive> archives = new ArrayList<>();
            grid.getChildren().clear();
            chosen.setDisable(true);
            chosen.setVisible(false);

            ajoutBT.setVisible(false);
            ajoutBT.setDisable(true);
            modBT.setVisible(false);
            modBT.setDisable(true);
            suppBT.setVisible(false);
            suppBT.setDisable(true);
            archives = service.getByIDUser(10);
            int column = 0;
            int row = 1;
            try {
                for (Archive ev : archives) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/Client/TravauCardClient.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ArchiveCardClientController travailcontroller = fxmlLoader.getController();
                    travailcontroller.setData(ev);

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
            grid.setVgap(10);
            scroll.setFitToWidth(true);
            scroll.setContent(grid);
            grid.requestLayout();

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Ajouter un écouteur d'événements sur le champ de recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Appeler la fonction de recherche à chaque fois que le texte du champ de recherche change
            search();
        });

        // Initialiser l'affichage des travaux
        Update();

    }// lors d execution d'interface


    @FXML
    void modifier(ActionEvent event) {
        // Vérifier si tous les champs sont remplis
        if (description.getText().isEmpty() || prix.getText().isEmpty() || type.getText().isEmpty() || titre.getText().isEmpty() || date_demande.getValue() == null || date_fin.getValue() == null) {
            // Afficher un message d'erreur si un champ est vide
            afficherMessageErreur("Veuillez remplir tous les champs !");
            return; // Sortir de la méthode, la modification ne peut pas être effectuée
        }

        // Vérifier si le type ne contient que des lettres
        if (!type.getText().matches("[a-zA-Z]+")) {
            // Afficher un message d'erreur si le type contient des caractères autres que des lettres
            afficherMessageErreur("Le champ 'type' doit contenir uniquement des lettres de l'alphabet !");
            return; // Sortir de la méthode, la modification ne peut pas être effectuée
        }

        try {
            // Convertir le prix en entier
            int prixValue = Integer.parseInt(prix.getText());

            // Vérifier si la date de demande est postérieure ou égale à aujourd'hui
            LocalDate aujourdhui = LocalDate.now();
            if (date_demande.getValue().isBefore(aujourdhui)) {
                // Afficher un message d'erreur si la date de demande est antérieure à aujourd'hui
                afficherMessageErreur("La date de demande doit être égale ou postérieure à aujourd'hui !");
                return; // Sortir de la méthode, la modification ne peut pas être effectuée
            }

            // Vérifier si la date de fin est postérieure à la date de demande
            if (date_fin.getValue().isBefore(date_demande.getValue())) {
                // Afficher un message d'erreur si la date de fin est antérieure à la date de demande
                afficherMessageErreur("La date de fin doit être postérieure à la date de demande !");
                return; // Sortir de la méthode, la modification ne peut pas être effectuée
            }

            // Créer un nouvel objet Travail avec les données saisies
            Travail travail = new Travail(id_travail, description.getText(), prixValue, type.getText(), StatusTravail.Attente, Date.valueOf(date_demande.getValue()), Date.valueOf(date_fin.getValue()),titre.getText(),10);

            // Modifier le travail
            TravailService2 ts = new TravailService2();
            ts.modifier(travail);

            // Nettoyer et mettre à jour l'affichage
            TravailList.clear();
            grid.getChildren().clear();
            Update();
        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion de texte en nombre
            afficherMessageErreur("Le prix doit être un nombre entier !");
        }
    }

    // Méthode utilitaire pour afficher un message d'erreur
    private void afficherMessageErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
// boutton modifier




    @FXML
    void supprimer(MouseEvent event) {
        TravailService2 cs = new TravailService2();
        cs.supprimer(id_travail);
        TravailList.clear();
        grid.getChildren().clear();
        Update();


    }//Boutton supprimer



    @FXML
    void ajouterEvent(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/Client/AjoutTravailClient.fxml")); //créer une nouvelle instance de FXMLLoader
        Parent root = loader.load(); // Charger le contenu du fichier FXML
        Scene scene = new Scene(root);// Créer une nouvelle scène à partir de la racine de l'interface
        Stage stage=new Stage();
        stage.setScene(scene); // Définir la scène créer comme scène principale

        Stage stageAjouter = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Ajouter travail");
        stageAjouter.close();
        stage.show();
    } //Boutton ajouter



    @FXML
    void search() {
        String searchTerm = searchField.getText().toLowerCase(); // Récupérer le terme de recherche saisi par l'utilisateur
        List<Travail> filteredList = new ArrayList<>(); // Créer une liste pour stocker les éléments filtrés

        // Parcourir la liste des travaux
        for (Travail tv : TravailList) {
            // Vérifier si le terme de recherche est contenu dans la description ou le type du travail
            if (tv.getTitre().toLowerCase().contains(searchTerm)) {
                // Si oui, ajouter le travail à la liste filtrée
                //..startsWith(searchTerm)
                filteredList.add(tv);
            }
        }

        // Nettoyer la grille actuelle
        grid.getChildren().clear();

        // Afficher les travaux filtrés
        int c = 0;
        int l = 0;
        try {
            for (int i = 0; i < filteredList.size(); i++) {// affichage des cartes
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Client/TravailCardClient.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                TravailCardClientController cardcontroller = fxmlLoader.getController();
                cardcontroller.setData(filteredList.get(i), myListener);
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
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void switchTravau(ActionEvent event)
    {
        if(travau)
        {
            travau = false;
            Update();
        }
        else
        {
            travau = true;
            Update();
        }
    }




}