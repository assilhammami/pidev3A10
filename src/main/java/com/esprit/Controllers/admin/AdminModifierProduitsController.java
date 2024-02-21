package com.esprit.Controllers.admin;

import com.esprit.models.Produits;
import com.esprit.service.ProduitsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class AdminModifierProduitsController {

    @FXML
    private Button btnModifier;

    @FXML
    private TextField categorie_produit;

    @FXML
    private TextArea description_produit;

    @FXML
    private TextField nom_produit;

    @FXML
    private TextField prix_produit;

    @FXML
    private TextField stock_produit;



    @FXML
    void goBack(MouseEvent event) throws IOException {
        URL fxURL = getClass().getResource("/AdminAffichageProduits.fxml");
        FXMLLoader loader = new FXMLLoader(fxURL);
        Parent root = (Parent) loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Davincci - Admin Produit");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void modifierProduit(ActionEvent event) throws SQLException {
        AdminAffichageProduits produitInfo = new AdminAffichageProduits();
        Produits chosen = produitInfo.selectedProduct;

        ProduitsService ps = new ProduitsService();


        String nomProd = nom_produit.getText();
        String descProd = description_produit.getText();
        float prixProd = Float.parseFloat(prix_produit.getText());
        int stockProd = Integer.parseInt(stock_produit.getText());
        String categorieProd = categorie_produit.getText();
        Produits p = new Produits(chosen.getId_produit(), nomProd, descProd, prixProd, stockProd, chosen.getDate_Creation(), categorieProd);

        ps.modifierProduits(p);
    }

}
