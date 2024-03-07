package com.esprit.controllers.Artiste;

import com.esprit.models.Travail;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailsTravailArtisteController implements Initializable {
    @FXML
    private DatePicker dateDemandLabel;

    @FXML
    private DatePicker dateEndLabel;

    @FXML
    private TextArea descriptionLabel;

    @FXML
    private TextField priceLabel;

    @FXML
    private TextField titleLabel;

    @FXML
    private TextField typeLabel;

    private Travail travail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialise la vue des détails du travail
    }

    // Méthode pour configurer les données du travail dans la vue
    public void setData(Travail travail) {
        this.travail = travail;
        titleLabel.setText(travail.getTitre());
        descriptionLabel.setText(travail.getDescription());
        typeLabel.setText(travail.getType());
        priceLabel.setText(String.valueOf(travail.getPrix()));
        dateDemandLabel.setValue(travail.getDate_fin().toLocalDate());
        dateEndLabel.setValue(travail.getDate_demande().toLocalDate());
    }
}
