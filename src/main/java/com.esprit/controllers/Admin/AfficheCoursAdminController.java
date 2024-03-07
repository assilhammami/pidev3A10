package com.esprit.controllers.Admin;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.esprit.models.cours;
import com.esprit.services.MyListener;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.scene.image.ImageView;




public class AfficheCoursAdminController implements Initializable {

    @FXML
    private TextField date_pub;

    @FXML
    private TextArea description;

    @FXML
    private GridPane grid;

    @FXML
    private TextField image;

    @FXML
    private ImageView img;

    @FXML
    private TextField nom;
    @FXML
    private ScrollPane scroll;

    private int id_cours;
    @FXML
    private TextField searchField;

    MyListener myListener;

    public cours selectedCours;
    private List<cours> coursList = new ArrayList<>();
    private String imagePathStr ;

    private void setChosenCours(cours cours) {
        selectedCours = cours;
        id_cours = cours.getId();
        nom.setText(cours.getNom());
        date_pub.setText((String.valueOf(cours.getDate_pub())));
        description.setText(cours.getDescription());
        imagePathStr=cours.getImage();
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

        }
        }

    public void Update() {
        CoursService cs= new CoursService();
        coursList.clear();
        grid.getChildren().clear();
        coursList.addAll(cs.afficher());
        myListener= new MyListener() {
            @Override
            public void onClickListener(cours cours) {
                setChosenCours(cours);
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

        searchField.setOnKeyReleased(keyEvent -> {
            search();
        });

        Update();
    }
    //boutton modifier
    @FXML
    void modifier(ActionEvent event) throws SQLException {
        cours cours = new cours(id_cours, nom.getText(), description.getText(), Date.valueOf(date_pub.getText()),imagePathStr);
        CoursService cs = new CoursService();
        cs.modifier(cours);
        coursList.clear();
        grid.getChildren().clear();
        Update();
    }

    //Boutton supprimer
    @FXML
    void supprimer(MouseEvent event) {
        CoursService cs = new CoursService();
        cs.supprimer(id_cours);
        coursList.clear();
        grid.getChildren().clear();
        Update();
    }
    //Boutton ajouter
    @FXML
    void ajouter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/AjouterCours.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        stage.show();
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
    private void upload() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                Path resourcesDir = Paths.get("src/main/resources/images");
                if (!Files.exists(resourcesDir)) {
                    Files.createDirectories(resourcesDir);
                }

                Path imagePath = resourcesDir.resolve(selectedFile.getName());
                Files.copy(selectedFile.toPath(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                imagePathStr = selectedFile.getName();


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
