package com.esprit.Controllers.admin.products;

import com.esprit.models.Produits;
import com.esprit.service.DataUpdateCallback;
import com.esprit.service.ProduitsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminAddProductController implements Initializable {


    @FXML
    private TextArea Description;

    @FXML
    private TextField Prix;

    @FXML
    private TextField Nom;

    @FXML
    private TextField Stock;
    @FXML
    private ChoiceBox<String> categorieOptions;

    private DataUpdateCallback dataUpdateCallback;
    private List<Produits> produitsList = new ArrayList<>();
    FileChooser fileChooser = new FileChooser();

    public void setDataUpdateCallback(DataUpdateCallback callback) {
        this.dataUpdateCallback = callback;
    }
    @FXML
    void ajouterProduit(ActionEvent event) {

        //control de saisie
        try {
            if (Nom.getText().trim().isEmpty() || Description.getText().trim().isEmpty() || Prix.getText().trim().isEmpty() || Stock.getText().trim().isEmpty() || categorieOptions.getValue().compareTo("") == 0) {
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
            String categorieProduit = categorieOptions.getValue();

            String imgProd = "/images/pencils.png";
            String upperCaseNom;

            ProduitsService produit = new ProduitsService();
            Produits p = new Produits(nomProduit, descriptionProduit, prixProduit, stockProduit, dateCreation, categorieProduit, imgProd);
            produitsList = produit.afficherProduit();
            for (Produits prod : produitsList) {
                upperCaseNom = prod.getNom().toUpperCase();
                if (upperCaseNom.equals(nomProduit.toUpperCase())) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Product Already exists!");
                    return;
                }
            }
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

    public void initialize(URL url, ResourceBundle rb) {
        categorieOptions.getItems().addAll("Table", "Tools");
    }

    public Image loadImage(String filePath) {
        try {
            File file = new File(filePath);
            return new Image(file.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }}

    @FXML
    void upload_image(MouseEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());
        
    }
}


