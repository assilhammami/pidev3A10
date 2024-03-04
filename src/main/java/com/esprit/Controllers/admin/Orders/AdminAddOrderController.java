package com.esprit.Controllers.admin.Orders;

import com.esprit.models.Commande;
import com.esprit.models.Produits;
import com.esprit.service.CommandeService;
import com.esprit.service.DataUpdateCallback;
import com.esprit.service.ProduitsService;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdminAddOrderController {

    @FXML
    private TextField Montant_total;

    @FXML
    private TextField Nom;

    @FXML
    private TextField Quantity;
    @FXML
    private TextField emailUser;


    @FXML
    String NomProduit; // Assuming this variable holds the product name
    Commande c; // Assuming this variable holds the command object
    private DataUpdateCallback dataUpdateCallback;
    public void setDataUpdateCallback(DataUpdateCallback callback) {
        this.dataUpdateCallback = callback;
    }

    @FXML
    void ajouterCommande(ActionEvent event) throws SQLException {
        // controle de saisie
        if (emailUser.getText().trim().isEmpty() || Quantity.getText().trim().isEmpty() || Montant_total.getText().trim().isEmpty() || Nom.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }
        CommandeService cs = new CommandeService();

        int id_prod = cs.getProductIdByName(Nom.getText());
        int id_user = cs.getUserIdByEmail(emailUser.getText());

        float totalPrix;
        int quantiteCommande;
        try {
            totalPrix = Float.parseFloat(Montant_total.getText().trim());
            quantiteCommande = Integer.parseInt(Quantity.getText().trim());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid number format for price or stock.");
            return;
        }
        if (totalPrix < 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "Total Price must be positive.");
            return;
        }
        if (quantiteCommande <= 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "Quantity must be greater than 0.");
            return;
        }
        if (id_user == -1) {
            showAlert(Alert.AlertType.ERROR, "Error", "User Not Found.");
            return;
        }
        if (id_prod == -1) {
            showAlert(Alert.AlertType.ERROR, "Error", "Product Not Found.");
            return;
        }



        // Get the current date
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateCommande = dateObj.format(formatter);

        // Get other necessary data from UI
        float montantTotal = Float.parseFloat(Montant_total.getText());
        int qte = Integer.parseInt(Quantity.getText());

        // Create the Commande object
        Commande commande = new Commande(dateCommande, montantTotal, qte, id_prod, id_user);

        // Add the command
        cs.ajouterCommande(commande);

        // Notify data update callback if it's set
        if (dataUpdateCallback != null) {
            dataUpdateCallback.onUpdate();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

