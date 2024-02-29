package com.esprit.controllers;


import com.esprit.models.Publication;
import com.esprit.models.commentaire;
import com.esprit.services.commentaireService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class listecommentairesController implements Initializable {



    @FXML
    private TableView<commentaire> tabcom;

    @FXML
    private TableColumn<commentaire, Integer> tfnote;

    @FXML
    private TableColumn<commentaire, String> tfcontenu;

    @FXML
    private TableColumn<commentaire, LocalDate> tfdatecom;

    @FXML
    private TableColumn<commentaire, String> tfuser;

    private commentaireService cs = new commentaireService();
    public int idPublicationSelectionnee;
    public List<commentaire> Listecomm=new ArrayList<>();










    @FXML
    public void setIdPublicationSelectionnee(int idPublicationSelectionnee) throws SQLException {
        this.idPublicationSelectionnee = idPublicationSelectionnee;
        Listecomm = cs.getCommentairesByPublicationId(idPublicationSelectionnee);
        System.out.println(Listecomm.toString());
        System.out.println(idPublicationSelectionnee);

        // Appelez rafraichirTableView() ici pour mettre à jour la TableView
        rafraichirTableView(idPublicationSelectionnee);
        tabcom.refresh();
    }

    public void rafraichirTableView(int idpubselect) throws SQLException {

        //System.out.println(idPublicationSelectionnee);
        List<commentaire> comList = cs.getCommentairesByPublicationId(idpubselect);

        // System.out.println(comList.toString());
        ObservableList<commentaire> com = FXCollections.observableArrayList(comList);

        // Associer les propriétés des zones aux colonnes de la table view
        tfcontenu.setCellValueFactory(new PropertyValueFactory<>("Contenu"));
        tfnote.setCellValueFactory(new PropertyValueFactory<>("Note"));
        tfuser.setCellValueFactory(cellData -> {
            int userId = cellData.getValue().getIduser();
            return new SimpleStringProperty(cs.getUsernameById(userId));
        });

        tfdatecom.setCellValueFactory(new PropertyValueFactory<>("datecommentaire"));
        tfdatecom.setCellFactory(column -> {
            return new TableCell<>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    }
                }
            };
        });



        // Set the items to the TableView
        tabcom.setItems(com);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            rafraichirTableView(idPublicationSelectionnee);

            // Colonne Actions
            TableColumn<commentaire, String> actionsColumn = new TableColumn<>("Actions");
            actionsColumn.setSortable(false);

            // Ajouter les boutons Supprimer et Modifier dans la même cellule
            actionsColumn.setCellValueFactory(param -> new SimpleStringProperty("Actions"));

            actionsColumn.setCellFactory(col -> {
                HBox container = new HBox(); // Conteneur pour les boutons

                // Bouton Supprimer
                Button deleteButton = new Button("Supprimer");
                deleteButton.setOnAction(event -> {
                    commentaire commentaire = tabcom.getSelectionModel().getSelectedItem();
                    int currentIdUser = 91;
                    if (commentaire != null && commentaire.getIduser() == currentIdUser) {
                        cs.supprimer(commentaire);
                        tabcom.getItems().remove(commentaire);
                    }
                });

                // Bouton Modifier
                Button editButton = new Button("Modifier");
                editButton.setOnAction(event -> {
                    commentaire commentaire = tabcom.getSelectionModel().getSelectedItem();
                    int currentIdUser = 91;
                    if (commentaire != null && commentaire.getIduser() == currentIdUser) {
                        // Rediriger vers AjoutCommentaireController avec les champs remplis
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifCommentaire.fxml"));
                        Parent root;
                        try {
                            root = loader.load();

                            ModifCommentaire modifCommentaire = loader.getController();
                            // ajoutCommentaireController.setIdPublicationSelectionnee(idPublicationSelectionnee);
                            modifCommentaire.setCommentaireToModify(commentaire);

                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                // Ajouter les boutons au conteneur
                container.getChildren().addAll(deleteButton, editButton);

                return new TableCell<commentaire, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            commentaire selectedComment = tabcom.getItems().get(getIndex());
                            int currentIdUser = 91;
                            setGraphic(selectedComment.getIduser() == currentIdUser ? container : null);
                        }
                    }
                };
            });

            // Ajouter la colonne à la TableView
            tabcom.getColumns().add(actionsColumn);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    @FXML
    void ajoutcom(ActionEvent event) throws IOException {
        int idPublication = idPublicationSelectionnee;
        System.out.println(idPublication);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutCommentaire.fxml"));
        Parent root = loader.load();
        AjoutCommentaireController AjoutCommentaireController = loader.getController();
        AjoutCommentaireController.setIdPublicationSelectionnee(idPublicationSelectionnee);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.show();



    }
    @FXML
    void refreshTableView(ActionEvent event) {
        // Rafraîchir la TableView
        try {
            rafraichirTableView(idPublicationSelectionnee);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }





}
