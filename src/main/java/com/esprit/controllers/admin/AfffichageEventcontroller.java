package com.esprit.controllers.admin;
import javafx.event.ActionEvent;
import com.esprit.services.EventService;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.esprit.models.Event;
import com.esprit.services.MyListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
public class AfffichageEventcontroller implements Initializable {

    @FXML
    private TextArea description;

    @FXML
    private TextField nom;

    @FXML
    private TextField capacity;

    @FXML
    private VBox chosen;

    @FXML
    private TextField date;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView img;

    @FXML
    private TextField place;

    @FXML
    private ScrollPane scroll;

    private int id_evnt;
    //
    MyListener myListener;


    public Event selectedProduct;
    private List<Event> eventsList = new ArrayList<>();
    @FXML
    private TextField searchField;
    private String imagePathStr;

    private void setChosenProduct(Event ev) {
        selectedProduct = ev;
        id_evnt = ev.getId();
        nom.setText(ev.getNom());
        date.setText((ev.getDate()));
        description.setText(ev.getDescription());
        capacity.setText(Integer.toString(ev.getCapacity()));
        place.setText(ev.getPlace());
        chosen.setVisible(true);
        chosen.setLayoutX(1050);
        ImageView imageView = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        try {

            File file = new File("src/main/resources/images/" + ev.getImage());
            String imageUrl = file.toURI().toURL().toExternalForm();
            Image image = new Image(imageUrl);
            img.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();



        }}

    public void Update() {

        EventService es= new EventService();
        eventsList.clear();
        grid.getChildren().clear();
        eventsList.addAll(es.afficher());
        myListener= new MyListener() {
            @Override
            public void onClickListener(Event p) {
                setChosenProduct(p);
            }
        };

        int column = 0;
        int row = 1;
        try {
            for (Event  ev : eventsList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/admin/evvv.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                Evvvcontroller evvvcontroller = fxmlLoader.getController();
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
        grid.setVgap(10);
        scroll.setFitToWidth(true);
        scroll.setContent(grid);
        grid.requestLayout();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chosen.setVisible(false);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {

            search();
        });
        Update();
    }

    @FXML
    void modifier(ActionEvent event) throws SQLException {
        if (isInputValid()) {
            Event e = new Event(id_evnt, nom.getText(),date.getText(), description.getText(), Integer.parseInt(capacity.getText()), place.getText(), imagePathStr);
            EventService es = new EventService();
            es.modifier(e);
            eventsList.clear();
            grid.getChildren().clear();
            Update();
            System.out.println("ghvdshgv");
            System.out.println(e.getNom());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez remplir tous les champs correctement !");
            alert.show();
        }
        chosen.setVisible(false);
    }

    private boolean isInputValid() {
        // Vérifier si tous les champs sont remplis
        if (nom.getText().isEmpty() || date.getText().isEmpty() || description.getText().isEmpty() || capacity.getText().isEmpty() || place.getText().isEmpty() || imagePathStr.isEmpty()) {
            return false;
        }

        // Vérifier d'autres critères de validation au besoin

        return true;
    }




    @FXML
    void supprimer(MouseEvent event) {
        EventService ev = new EventService();
        ev.supprimer(id_evnt);
        eventsList.clear();
        grid.getChildren().clear();
        Update();
        chosen.setVisible(false);
    }


    @FXML
    void ajouterEvent(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/AjoutEvent.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        Stage stageAfficher = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(" ajouter Event");
        stageAfficher.close();

        stage.show();
    }
    @FXML
    void search( ) {
        String searchTerm = searchField.getText().toLowerCase(); // Récupérer le terme de recherche saisi par l'utilisateur
        List<Event> filteredList = new ArrayList<>(); // Créer une liste pour stocker les éléments filtrés

        // Parcourir la liste des travaux
        for (Event ev : eventsList) {
            // Vérifier si le terme de recherche est contenu dans la description ou le type du travail
            if (ev.getNom().toLowerCase().contains(searchTerm) ){
                // Si oui, ajouter le travail à la liste filtrée
                filteredList.add(ev);
            }
        }

        // Nettoyer la grille actuelle
        grid.getChildren().clear();

        EventService es= new EventService();
        eventsList.clear();
        grid.getChildren().clear();
        eventsList.addAll(es.afficher());
        myListener= new MyListener() {
            @Override
            public void onClickListener(Event p) {
                setChosenProduct(p);
            }
        };

        int column = 0;
        int row = 1;
        try {
            for (Event  ev : filteredList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/admin/evvv.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                Evvvcontroller evvvcontroller = fxmlLoader.getController();
                evvvcontroller.setData(ev, myListener);

                if (column == 4) {
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
        chosen.setVisible(false);
    }
    @FXML
    private void handleUploadImage() {
        Event ev; // Vous devez initialiser cet objet avec les données de l'événement

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                java.nio.file.Path resourcesDir = Paths.get("src/main/resources/images");
                if (!Files.exists(resourcesDir)) {
                    Files.createDirectories(resourcesDir);
                }

                java.nio.file.Path imagePath = resourcesDir.resolve(selectedFile.getName());
                Files.copy(selectedFile.toPath(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                 imagePathStr =   selectedFile.getName();

                // Charger et afficher l'image
                try {
                    String imageUrl = imagePath.toUri().toURL().toExternalForm();
                    Image image = new Image(imageUrl);
                    img.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to upload image.");
                alert.showAndWait();
            }
        }
    }

}