package com.esprit.controllers;
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
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.esprit.models.cours;
import com.esprit.services.MyListener;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;

public class AfficheCoursController implements Initializable {

    @FXML
    private VBox chosen;

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
    //
    MyListener myListener;


    public cours selectedCours;
    private List<cours> coursList = new ArrayList<>();

    private void setChosenCours(cours cours) {
        selectedCours = cours;
        id_cours = cours.getId();
        nom.setText(cours.getNom());
        date_pub.setText((String.valueOf(cours.getDate_pub())));
        description.setText(cours.getDescription());
        image.setText(cours.getImage());
        Image image;
        image = new Image(cours.getImage());
        img.setImage(image);

    }

    void Update() {
        cours cc;
        CoursService cs= new CoursService();
        coursList.clear();
        grid.getChildren().clear();
        coursList.addAll(cs.afficher());
        myListener = new MyListener() {
            @Override
            public void onClickListener(cours c) {
                setChosenCours(c);

            }
        };

        int c = 0;
        int l = 0;
        try {
            for (int i = 0; i < coursList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CoursCard.fxml") );
                AnchorPane anchorPane = fxmlLoader.load();

                CoursCardController cardcontroller = fxmlLoader.getController();
                cardcontroller.setData(coursList.get(i), myListener);
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Update();
    }
    /*
         @FXML
         void ajoutProduit(ActionEvent event) throws SQLException {
             ProduitsService produit = new ProduitsService();
             LocalDate dateObj = LocalDate.now();
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
             String dateCreation = dateObj.format(formatter);

             String nomProduit = Nom_produit.getText();
             String descriptionProduit = Description.getText();
             float prixProduit = Float.parseFloat(Prix.getText());
             int stockProduit = Integer.parseInt(Stock.getText());
             String categorieProduit = Categorie.getText();
             Produits p = new Produits(nomProduit, descriptionProduit, prixProduit, stockProduit, dateCreation, categorieProduit);
             produit.ajouterProduits(p);
             Update();
         }
*/
    @FXML
    void modifier(ActionEvent event) throws SQLException {
        cours cours = new cours(id_cours, nom.getText(), description.getText(), Date.valueOf(date_pub.getText()),  image.getText());
        CoursService cs = new CoursService();
        cs.modifier(cours);
        coursList.clear();
        grid.getChildren().clear();
        Update();
        System.out.println("ghvdshgv");
        System.out.println(cours.getNom());
    }

    @FXML
    void search(MouseEvent event) {
        Update();
    }

    @FXML
    void supprimer(MouseEvent event) {
        CoursService cs = new CoursService();
        cs.supprimer(id_cours);
        coursList.clear();
        grid.getChildren().clear();
        Update();
    }

    @FXML
    void GoToOrders(ActionEvent event) throws IOException {
        URL fxURL = getClass().getResource("/AfficheCours.fxml");
        FXMLLoader loader = new FXMLLoader(fxURL);
        Parent root = (Parent) loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Davincci Admin - Orders");
        stage.setScene(new Scene(root));
        stage.show();

    }


}