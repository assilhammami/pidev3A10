package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.models.UserType;
import com.esprit.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class testcontroller implements Initializable {



    @FXML
    private TableColumn<User, String> col_birthdate;

    @FXML
    private TableColumn<User, String> col_email;



    @FXML
    private TableColumn<User, String> col_lastname;

    @FXML
    private TableColumn<User, String>col_name;

    @FXML
    private TableColumn<User, Integer> col_num;

    @FXML
    private TableColumn<User, String> col_password;

    @FXML
    private TableColumn<User, String> col_path;

    @FXML
    private TableColumn<User, String>col_username;

    @FXML
    private TableColumn<User, String> col_usertype;


    @FXML
    private TextField searchbar;



    @FXML
    private TableView<User> tableuser;


    UserService us=new UserService();
    ObservableList<User> ListUser;



    @FXML
    public void search() {
        System.out.println("Search method called");
        tableuser.getColumns().clear();

        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_path.setCellValueFactory(new PropertyValueFactory<>("photo_de_profile"));
        col_num.setCellValueFactory(new PropertyValueFactory<>("num_telephone"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        col_birthdate.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
        col_usertype.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<User, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(getButtonCellFactory());

        // Add columns to the table
        tableuser.getColumns().addAll(col_username, col_name, col_lastname, col_email, col_path, col_num, col_password, col_birthdate, col_usertype, actionColumn);

        ObservableList<User> reclamations = FXCollections.observableArrayList(us.afficher());
        tableuser.setItems(reclamations);
        FilteredList<User> filteredData = new FilteredList<>(reclamations, b -> true);
        searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (user.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (user.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (user.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (user.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (user.getPhoto_de_profile().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(user.getNum_telephone()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (user.getMot_de_passe().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (user.getDate_de_naissance().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (user.getType().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        tableuser.setItems(filteredData);

    }


    private void loadUsers() {
        ObservableList<User> reclamations = FXCollections.observableArrayList(us.afficher());
        tableuser.setItems(reclamations);

    }
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {

        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_path.setCellValueFactory(new PropertyValueFactory<>("photo_de_profile_path"));
        col_num.setCellValueFactory(new PropertyValueFactory<>("num_telephone"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        col_birthdate.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
        col_usertype.setCellValueFactory(new PropertyValueFactory<>("type"));


        TableColumn<User, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(getButtonCellFactory());
        tableuser.getColumns().add(actionColumn);

        loadUsers();
        tableuser.refresh();
        search();
    }

    private Callback<TableColumn<User, Void>, TableCell<User, Void>> getButtonCellFactory() {
        return new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
                final TableCell<User, Void> cell = new TableCell<User, Void>() {
                    private final HBox hbox = new HBox();
                    private final Button activerButton = new Button("Activer");
                    private final Button desactiverButton = new Button("Desactiver");


                    {


                        activerButton.setOnAction(event -> {
                            User data = getTableView().getItems().get(getIndex());

                            us.ActivateAccount(data);
                            us.modifier(data);
                            tableuser.refresh();

                        });

                        desactiverButton.setOnAction(event -> {
                            User data = getTableView().getItems().get(getIndex());
                            us.DesactivateAccount(data);
                            us.modifier(data);
                            tableuser.refresh();

                        });
                        activerButton.setMaxWidth(Double.MAX_VALUE);
                        desactiverButton.setMaxWidth(Double.MAX_VALUE);

                        HBox.setHgrow(activerButton, Priority.ALWAYS);
                        HBox.setHgrow(desactiverButton, Priority.ALWAYS);

                        activerButton.setAlignment(Pos.CENTER);
                        desactiverButton.setAlignment(Pos.CENTER);

                        hbox.setAlignment(Pos.CENTER);
                        hbox.getChildren().addAll(activerButton, desactiverButton);
                    }



                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            User data = getTableView().getItems().get(getIndex());
                            if (!data.getActive()) {
                                // Si le compte est activé, afficher le bouton "Activer"
                                setGraphic(activerButton);
                            } else {
                                // Sinon, afficher le bouton "Désactiver"
                                setGraphic(desactiverButton);
                            }
                        }

                    }
                };
                return cell;
            }
        };
    }

    }




