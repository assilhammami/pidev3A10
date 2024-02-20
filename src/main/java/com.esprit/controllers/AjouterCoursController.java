package com.esprit.controllers;

import com.esprit.models.Num_chapitre;
import com.esprit.models.cours;
import com.esprit.services.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterCoursController  {
    @FXML
    private TableColumn<cours, String> colDescription;

    @FXML
    private TableColumn<cours, Integer> colId;

    @FXML
    private TableColumn<cours, String> colNom;

    @FXML
    private TableColumn<cours, Num_chapitre > colNumchap;

    @FXML
    private TableView<cours> table;

    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfNom;

    @FXML
    private ChoiceBox<Num_chapitre> tfNumchap;
    /*private ObservableList<cours> listCours = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rafraichirTableView();
        initializeTableView();
        FillForm();

    }

    private void FillForm() {

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {

                cours CoursSelectionne = table.getSelectionModel().getSelectedItem();
                if (CoursSelectionne != null) {

                    tfNom.setText(CoursSelectionne.getNom());
                    tfDescription.setText(CoursSelectionne.getDescription());
                    tfNumchap.setValue(CoursSelectionne.getNum_chap());


                }
            }
        });
    }

    private void rafraichirTableView() {
        CoursService coursService = new CoursService();
        List<cours> coursList = coursService.afficher();
        ObservableList<cours> cours = FXCollections.observableArrayList(coursList);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colNumchap.setCellValueFactory(new PropertyValueFactory<>("numero_chapitre"));

        table.setItems(cours);
    }

    public void initializeTableView(){
        CoursService coursService = new CoursService();
        List<cours> allCours = coursService.afficher();
        ObservableList<cours> displayedCours = FXCollections.observableArrayList();
        displayedCours.addAll(allCours);
        table.setItems(displayedCours);
    }

*/
    public void initialize() {
        // Créer une liste d'éléments
        ObservableList<Num_chapitre> chapitres = FXCollections.observableArrayList(Num_chapitre.values());

        // Ajouter la liste d'éléments à la ChoiceBox
        tfNumchap.setItems(chapitres);}

    @FXML
    void addCours(ActionEvent event) throws IOException {
        CoursService cs = new CoursService();
        cs.ajouter(new cours(tfNom.getText(),tfDescription.getText(),tfNumchap.getValue()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cours ajouté ");
        alert.setContentText("Cours ajouté avec succès !");
        alert.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageCours.fxml"));
        Parent root = loader.load();
        tfNom.getScene().setRoot(root);

        //get the controller
        AffichageCoursController acc = loader.getController();
        acc.setLbNom(tfNom.getText());
        acc.setLbDescription(tfDescription.getText());
        acc.setLbNumchap(tfNumchap.getValue());


    }
    /*private void resetFormulaire() {
        tfNom.setText("");
        tfDescription.setText("");
        tfNumchap.getSelectionModel().clearSelection();
    }*/





}
