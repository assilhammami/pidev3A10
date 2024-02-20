package com.esprit.Controllers;

import com.esprit.models.Produits;
import com.esprit.service.ProduitsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.sql.SQLException;

public class AdminAjoutProduitsController {

    @FXML
    private Button btnAjout;

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
    void ajouterProduit(ActionEvent event) throws SQLException {
        ProduitsService ps = new ProduitsService();
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateCreation = dateObj.format(formatter);
        String nomProd = nom_produit.getText();
        String descProd = description_produit.getText();
        float prixProd = Float.parseFloat(prix_produit.getText());
        int stockProd = Integer.parseInt(stock_produit.getText());
        String categorieProd = categorie_produit.getText();
        Produits p = new Produits(nomProd, descProd, prixProd, stockProd, dateCreation, categorieProd);

        ps.ajouterProduits(p);

    }

}
