package com.esprit.Controllers.admin.products;

import com.esprit.models.Produits;
import com.esprit.service.DataUpdateCallback;
import com.esprit.service.ProduitsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdminAddProductController {

    @FXML
    private TextArea Description;

    @FXML
    private TextField Prix;

    @FXML
    private TextField Nom;

    @FXML
    private TextField Stock;
    private DataUpdateCallback dataUpdateCallback;

    public void setDataUpdateCallback(DataUpdateCallback callback) {
        this.dataUpdateCallback = callback;
    }
    @FXML
    void ajouterProduit(ActionEvent event) {
        //control de saisie
        try {
            if (Nom.getText().trim().isEmpty() || Description.getText().trim().isEmpty() || Prix.getText().trim().isEmpty() || Stock.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
                return;
            }

            float prixProduit;
            int stockProduit;
            try {
                prixProduit = Float.parseFloat(Prix.getText().trim());
                stockProduit = Integer.parseInt(Stock.getText().trim());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid number format for price or stock.");
                return;
            }

            if (prixProduit < 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Price must be positive.");
                return;
            }
            if (stockProduit <= 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Stock must be greater than 0.");
                return;
            }

            // CRUD AJOUT
            LocalDate dateObj = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateCreation = dateObj.format(formatter);
            String nomProduit = Nom.getText().trim();
            String descriptionProduit = Description.getText().trim();
            String categorieProduit = "Test";
            String imgProd = "/images/pencils.png";

            ProduitsService produit = new ProduitsService();
            Produits p = new Produits(nomProduit, descriptionProduit, prixProduit, stockProduit, dateCreation, categorieProduit, imgProd);
            produit.ajouterProduits(p);

            if (dataUpdateCallback != null) {
                dataUpdateCallback.onUpdate();
            }

            showAlert(Alert.AlertType.INFORMATION, "Success", "Product added successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add product.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


//    @FXML
//    void ajouterProduit(ActionEvent event) throws SQLException {
//        ProduitsService produit = new ProduitsService();
//        LocalDate dateObj = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String dateCreation = dateObj.format(formatter);
//        String nomProduit = Nom.getText();
//        String descriptionProduit = Description.getText();
//        float prixProduit = Float.parseFloat(Prix.getText());
//        int stockProduit = Integer.parseInt(Stock.getText());
//        String categorieProduit = "Test";
//        String imgProd = "/images/pencils.png";
//        Produits p = new Produits(nomProduit, descriptionProduit, prixProduit, stockProduit, dateCreation, categorieProduit, imgProd);
//        produit.ajouterProduits(p);
//        if (dataUpdateCallback != null) {
//            dataUpdateCallback.onUpdate();
//        }
//
//    }

}


