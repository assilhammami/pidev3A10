package com.esprit.Controllers.admin;

import com.esprit.models.Produits;
import com.esprit.service.MyListener;
import com.esprit.service.ProduitsService;
import javafx.event.ActionEvent;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;
public class AdminAffichageProduits implements Initializable{
    @FXML
    private TextField Categorie;

    @FXML
    private TextField Nom_produit;
    @FXML
    private TextField image_produit;

    @FXML
    private ImageView img;

    @FXML
    private TextArea Description;

    @FXML
    private GridPane grid;

    @FXML
    private TextField Prix;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField Stock;

    @FXML
    private Label Date_creation;

    @FXML
    private VBox chosen;

    private int id_prod;
    //
    MyListener myListener;

    public Produits selectedProduct;
    private List<Produits> produitsList = new ArrayList<>();
    String date_creation_chosen;
    private void setChosenProduct(Produits p) {
        selectedProduct = p;
        id_prod = p.getId_produit();
        Nom_produit.setText(p.getNom());
        Description.setText(p.getDescription());
        Prix.setText(Float.toString(p.getPrix()));
        Stock.setText(Integer.toString(p.getStock()));
        Date_creation.setText("Date creation: "+p.getDate_Creation());
        date_creation_chosen = p.getDate_Creation();
        Categorie.setText(p.getCategorie());
        Image image = new Image(p.getImage_produit());
        img.setImage(image);
        image_produit.setText(p.getImage_produit());
        // image = new Image(getClass().getResourceAsStream(m.getImage_produit()));
        // imgProd_ch.setImage(image);
    }
    public void Update() {
        Produits p;
        ProduitsService produit = new ProduitsService();
        produitsList.clear();
        grid.getChildren().clear();
        produitsList.addAll(produit.afficherProduit());
        myListener = new MyListener() {
            @Override
            public void onClickListener(Produits p) {
                setChosenProduct(p);

            }
        };

        int c = 0;
        int l = 0;
        try {
            for (int i = 0; i < produitsList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/products.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                ProductItemController productController = fxmlLoader.getController();
                productController.setData(produitsList.get(i), myListener);
                if (c > 2) {
                    c = 0;
                    l++;
                }
                grid.add(anchorPane, c++, l);
                //grid weight
                grid.setMinWidth(215);
                grid.setPrefWidth(215);
                grid.setMaxWidth(215);//
                //height
                grid.setMinHeight(170);
                grid.setPrefHeight(170);
                grid.setMaxHeight(170);//
                grid.setLayoutY(10);


                GridPane.setMargin(anchorPane, new Insets(215, 0, 0, 65));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Update();
    }

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
        String imgProd = image_produit.getText();

        Produits p = new Produits(nomProduit, descriptionProduit, prixProduit, stockProduit, dateCreation, categorieProduit, imgProd);
        produit.ajouterProduits(p);
        Update();
    }

    @FXML
    void modifierProduit(ActionEvent event) throws SQLException {
        Produits p = new Produits(id_prod, Nom_produit.getText(), Description.getText(), Float.parseFloat(Prix.getText()), Integer.parseInt(Stock.getText()), date_creation_chosen, Categorie.getText(), image_produit.getText());
        ProduitsService produit = new ProduitsService();
        produit.modifierProduits(p);
        produitsList.clear();
        grid.getChildren().clear();
        Update();
    }

    @FXML
    void search(MouseEvent event) {
        Update();
    }

    @FXML
    void supprimerProduit(MouseEvent event) {
        ProduitsService produit = new ProduitsService();
        produit.supprimerProduits(id_prod);
        produitsList.clear();
        grid.getChildren().clear();
        Update();
    }

    @FXML
    void GoToOrders(ActionEvent event) throws IOException {
        URL fxURL = getClass().getResource("/AdminAffichageCommandes.fxml");
        FXMLLoader loader = new FXMLLoader(fxURL);
        Parent root = (Parent) loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Davincci Admin - Orders");
        stage.setScene(new Scene(root));
        stage.show();

    }
}