package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.services.UserDataManager;
import com.esprit.services.UserService;
import com.esprit.utils.Mail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.ResourceBundle;

public class AdminDashboardUsersController implements Initializable {



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
    private TableColumn<User, String>col_username;

    @FXML
    private TableColumn<User, String> col_usertype;


    @FXML
    private TextField searchbar;
    @FXML
    private ChoiceBox<String> filterchoicebox;
    @FXML
    private ChoiceBox<String> trichoicebox;


    @FXML
    private TableView<User> tableuser;
    @FXML
    private Button CoursesButton;

    @FXML
    private Button EventsButton;

    @FXML
    private Button ForumButton;

    @FXML
    private Button JobsButton;

    @FXML
    private Button MarketButton;

    @FXML
    private Button ProfileButton;

    @FXML
    private Label UserType;

    @FXML
    private Button UsersButton;
    @FXML
    private Button logoutButton;

    @FXML
    private ImageView photoProfile;
    @FXML
    private Label usernameOld;
    private UserDataManager userDataManager = UserDataManager.getInstance();
    private int CurrentUserId = userDataManager.getUserId();
    UserService us=new UserService();

    User currentUser = us.getUser(CurrentUserId);
    ObservableList<User> ListUser;

    public AdminDashboardUsersController() throws SQLException {
    }


    @FXML
    public void search() {

        String selectedTri= trichoicebox.getValue() != null ? trichoicebox.getValue() : "";
        String selectedFilter= filterchoicebox.getValue() != null ? filterchoicebox.getValue() : "";
        System.out.println("Selected filter: " + selectedFilter);
        System.out.println("Selected tri: " + selectedTri);
        if (selectedFilter.equals("Username")) {
            searchByUsername(selectedTri);
        } else if (selectedFilter.equals("Name")) {
            searchByName(selectedTri);
        } else if (selectedFilter.equals("Lastname")) {
            searchByLastname(selectedTri);
        } else if (selectedFilter.equals("Email")) {
            searchByEmail(selectedTri);
        } else if (selectedFilter.equals("Phone Number")) {
            searchByNum(selectedTri);
        } else if (selectedFilter.equals("BirthDate")) {
            searchByBirthdate(selectedTri);
        } else if (selectedFilter.equals("UserType")) {
            searchByType(selectedTri);
        }else if(selectedFilter.equals("All")) {System.out.println("Search method called");
        tableuser.getColumns().clear();

        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_num.setCellValueFactory(new PropertyValueFactory<>("num_telephone"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        col_birthdate.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
        col_usertype.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<User, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(getButtonCellFactory());
        tableuser.getColumns().addAll(col_username, col_name, col_lastname, col_email, col_num, col_password, col_birthdate, col_usertype, actionColumn);

        ObservableList<User> users = FXCollections.observableArrayList(us.afficher());
        tableuser.setItems(users);
        FilteredList<User> filteredData = new FilteredList<>(users, b -> true);
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
                }  else if (String.valueOf(user.getNum_telephone()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (user.getMot_de_passe().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (user.getDate_de_naissance().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (user.getType().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });

        });
        tableuser.setItems(filteredData);

    }}
    @FXML
    public void searchByUsername(String tri) {
        System.out.println("Search by username method called");
        tableuser.getColumns().clear();

        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_num.setCellValueFactory(new PropertyValueFactory<>("num_telephone"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        col_birthdate.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
        col_usertype.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<User, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(getButtonCellFactory());
        tableuser.getColumns().addAll(col_username, col_name, col_lastname, col_email, col_num, col_password, col_birthdate, col_usertype, actionColumn);
        ObservableList<User> users = FXCollections.observableArrayList(us.afficher());
        FilteredList<User> filteredData = new FilteredList<>(users, b -> true);
        FilteredList<User> finalFilteredData = filteredData;
        searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            finalFilteredData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                String username = user.getUsername().toLowerCase();
                return username.contains(lowerCaseFilter);
            });
        });
        SortedList<User> sortedData = new SortedList<>(finalFilteredData);
        if (tri != "None") {
            Comparator<User> comparator;
            if (tri.equals("Ascendant")) {
                comparator = Comparator.comparing(User::getUsername);
            } else if (tri.equals("Descendant")) {
                comparator = Comparator.comparing(User::getUsername).reversed();
            } else {
                comparator = null;
            }

            if (comparator != null) {
                sortedData.setComparator(comparator);
            }
        }

        tableuser.setItems(sortedData);
    }

    @FXML
    public void searchByName(String tri) {
        System.out.println("Search by name method called");
        tableuser.getColumns().clear();

        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_num.setCellValueFactory(new PropertyValueFactory<>("num_telephone"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        col_birthdate.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
        col_usertype.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<User, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(getButtonCellFactory());
        tableuser.getColumns().addAll(col_username, col_name, col_lastname, col_email, col_num, col_password, col_birthdate, col_usertype, actionColumn);

        ObservableList<User> users = FXCollections.observableArrayList(us.afficher());
        tableuser.setItems(users);
        FilteredList<User> filteredData = new FilteredList<>(users, b -> true);
        FilteredList<User> finalFilteredData = filteredData;
        searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            finalFilteredData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                String username = user.getNom().toLowerCase();
                return username.contains(lowerCaseFilter);
            });

        });SortedList<User> sortedData = new SortedList<>(finalFilteredData);
        if (tri != "None") {
            Comparator<User> comparator;
            if (tri.equals("Ascendant")) {
                comparator = Comparator.comparing(User::getNom);
            } else if (tri.equals("Descendant")) {
                comparator = Comparator.comparing(User::getNom).reversed();
            } else {
                comparator = null;
            }

            if (comparator != null) {
                sortedData.setComparator(comparator);
            }
        }

        tableuser.setItems(sortedData);
    }
    @FXML
    public void searchByLastname(String tri){ System.out.println("Search by lastname method called");
        tableuser.getColumns().clear();

        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_num.setCellValueFactory(new PropertyValueFactory<>("num_telephone"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        col_birthdate.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
        col_usertype.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<User, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(getButtonCellFactory());
        tableuser.getColumns().addAll(col_username, col_name, col_lastname, col_email, col_num, col_password, col_birthdate, col_usertype, actionColumn);

        ObservableList<User> users = FXCollections.observableArrayList(us.afficher());
        tableuser.setItems(users);
        FilteredList<User> filteredData = new FilteredList<>(users, b -> true);
        FilteredList<User> finalFilteredData = filteredData;
        searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            finalFilteredData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                String username = user.getPrenom().toLowerCase();
                return username.contains(lowerCaseFilter);
            });

        });SortedList<User> sortedData = new SortedList<>(finalFilteredData);
        if (tri != "None") {
            Comparator<User> comparator;
            if (tri.equals("Ascendant")) {
                comparator = Comparator.comparing(User::getPrenom);
            } else if (tri.equals("Descendant")) {
                comparator = Comparator.comparing(User::getPrenom).reversed();
            } else {
                comparator = null;
            }

            if (comparator != null) {
                sortedData.setComparator(comparator);
            }
        }

        tableuser.setItems(sortedData);
    }
@FXML
public void searchByEmail(String tri) { System.out.println("Search by email method called");
    tableuser.getColumns().clear();

    col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
    col_name.setCellValueFactory(new PropertyValueFactory<>("nom"));
    col_lastname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
    col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
    col_num.setCellValueFactory(new PropertyValueFactory<>("num_telephone"));
    col_password.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
    col_birthdate.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
    col_usertype.setCellValueFactory(new PropertyValueFactory<>("type"));
    TableColumn<User, Void> actionColumn = new TableColumn<>("Actions");
    actionColumn.setCellFactory(getButtonCellFactory());
    tableuser.getColumns().addAll(col_username, col_name, col_lastname, col_email, col_num, col_password, col_birthdate, col_usertype, actionColumn);

    ObservableList<User> users = FXCollections.observableArrayList(us.afficher());
    tableuser.setItems(users);
    FilteredList<User> filteredData = new FilteredList<>(users, b -> true);
    FilteredList<User> finalFilteredData = filteredData;
    searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
        finalFilteredData.setPredicate(user -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            String username = user.getEmail().toLowerCase();
            return username.contains(lowerCaseFilter);
        });

    });
    SortedList<User> sortedData = new SortedList<>(finalFilteredData);
    if (tri != "None") {
        Comparator<User> comparator;
        if (tri.equals("Ascendant")) {
            comparator = Comparator.comparing(User::getEmail);
        } else if (tri.equals("Descendant")) {
            comparator = Comparator.comparing(User::getEmail).reversed();
        } else {
            comparator = null;
        }

        if (comparator != null) {
            sortedData.setComparator(comparator);
        }
    }

    tableuser.setItems(sortedData);
}
    @FXML
    public void searchByNum(String tri) {
        System.out.println("Search by num method called");
        tableuser.getColumns().clear();

        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_num.setCellValueFactory(new PropertyValueFactory<>("num_telephone"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        col_birthdate.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
        col_usertype.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<User, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(getButtonCellFactory());
        tableuser.getColumns().addAll(col_username, col_name, col_lastname, col_email, col_num, col_password, col_birthdate, col_usertype, actionColumn);

        ObservableList<User> users = FXCollections.observableArrayList(us.afficher());
        tableuser.setItems(users);
        FilteredList<User> filteredData = new FilteredList<>(users, b -> true);
        FilteredList<User> finalFilteredData = filteredData;
        searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            finalFilteredData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                String username = String.valueOf(user.getNum_telephone()).toLowerCase();
                return username.contains(lowerCaseFilter);
            });

        });
        SortedList<User> sortedData = new SortedList<>(finalFilteredData);
        if (tri != "None") {
            Comparator<User> comparator;
            if (tri.equals("Ascendant")) {
                comparator = Comparator.comparing(User::getNum_telephone);
            } else if (tri.equals("Descendant")) {
                comparator = Comparator.comparing(User::getNum_telephone).reversed();
            } else {
                comparator = null;
            }

            if (comparator != null) {
                sortedData.setComparator(comparator);
            }
        }

        tableuser.setItems(sortedData);
    }
    @FXML
    public void searchByBirthdate(String tri) {
        System.out.println("Search by birthdate method called");
        tableuser.getColumns().clear();

        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_num.setCellValueFactory(new PropertyValueFactory<>("num_telephone"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        col_birthdate.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
        col_usertype.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<User, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(getButtonCellFactory());
        tableuser.getColumns().addAll(col_username, col_name, col_lastname, col_email, col_num, col_password, col_birthdate, col_usertype, actionColumn);

        ObservableList<User> users = FXCollections.observableArrayList(us.afficher());
        tableuser.setItems(users);
        FilteredList<User> filteredData = new FilteredList<>(users, b -> true);
        FilteredList<User> finalFilteredData = filteredData;
        searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            finalFilteredData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                String username = user.getDate_de_naissance().toString().toLowerCase();
                return username.contains(lowerCaseFilter);
            });

        });
        SortedList<User> sortedData = new SortedList<>(finalFilteredData);
        if (tri != "None") {
            Comparator<User> comparator;
            if (tri.equals("Ascendant")) {
                comparator = Comparator.comparing((User user) -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    return LocalDate.parse(user.getDate_de_naissance(), formatter);
                });
            } else if (tri.equals("Descendant")) {
                comparator = Comparator.comparing((User user) -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    return LocalDate.parse(user.getDate_de_naissance(), formatter);
                }).reversed();
            } else {
                comparator = null;
            }

            if (comparator != null) {
                sortedData.setComparator(comparator);
            }
        }

        tableuser.setItems(sortedData);
    }
@FXML
public void searchByType(String tri) { System.out.println("Search by type method called");

    tableuser.getColumns().clear();

    col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
    col_name.setCellValueFactory(new PropertyValueFactory<>("nom"));
    col_lastname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
    col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
    col_num.setCellValueFactory(new PropertyValueFactory<>("num_telephone"));
    col_password.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
    col_birthdate.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
    col_usertype.setCellValueFactory(new PropertyValueFactory<>("type"));
    TableColumn<User, Void> actionColumn = new TableColumn<>("Actions");
    actionColumn.setCellFactory(getButtonCellFactory());
    tableuser.getColumns().addAll(col_username, col_name, col_lastname, col_email, col_num, col_password, col_birthdate, col_usertype, actionColumn);

    ObservableList<User> users = FXCollections.observableArrayList(us.afficher());
    tableuser.setItems(users);
    FilteredList<User> filteredData = new FilteredList<>(users, b -> true);
    FilteredList<User> finalFilteredData = filteredData;
    searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
        finalFilteredData.setPredicate(user -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            String username = user.getType().toLowerCase();
            return username.contains(lowerCaseFilter);
        });

    });
    SortedList<User> sortedData = new SortedList<>(finalFilteredData);
    if (tri != "None") {
        Comparator<User> comparator;
        if (tri.equals("Ascendant")) {
            comparator = Comparator.comparing(User::getType);
        } else if (tri.equals("Descendant")) {
            comparator = Comparator.comparing(User::getType).reversed();
        } else {
            comparator = null;
        }

        if (comparator != null) {
            sortedData.setComparator(comparator);
        }
    }

    tableuser.setItems(sortedData);
}



    private void loadUsers() {
        ObservableList<User> users = FXCollections.observableArrayList(us.afficher());
        tableuser.setItems(users);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameOld.setText(currentUser.getUsername());
        Image photo_profile=us.loadImage(currentUser.getPhoto_de_profile());
        photoProfile.setImage(photo_profile);
        trichoicebox.getItems().addAll("Ascendant", "Descendant", "None");
        filterchoicebox.getItems().addAll("Username", "Name", "Lastname", "Email", "Phone Number",  "BirthDate", "UserType", "All");
        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_lastname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_num.setCellValueFactory(new PropertyValueFactory<>("num_telephone"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        col_birthdate.setCellValueFactory(new PropertyValueFactory<>("date_de_naissance"));
        col_usertype.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<User, Void> actionColumn = new TableColumn<>("Actions");
        actionColumn.setCellFactory(getButtonCellFactory());
        tableuser.getColumns().add(actionColumn);

        loadUsers();
        tableuser.refresh();
        trichoicebox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Tri changed: " + newValue);
            search();
        });

        filterchoicebox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Filter changed: " + newValue);
            search();
        });


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
                            try {
                                Mail.sendActivatedMail(data.getEmail());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            tableuser.refresh();

                        });

                        desactiverButton.setOnAction(event -> {
                            User data = getTableView().getItems().get(getIndex());
                            us.DesactivateAccount(data);
                            us.modifier(data);
                            try {
                                Mail.sendDeactivateMail(data.getEmail());
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
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
    @FXML
    void LogOut(ActionEvent event) throws IOException {
        userDataManager.logout();
        CurrentUserId =0;
        currentUser=null;
        Stage stage=(Stage) logoutButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void goToCourses(ActionEvent event) {

    }

    @FXML
    void goToEvents(ActionEvent event) {

    }

    @FXML
    void goToForum(ActionEvent event) {

    }

    @FXML
    void goToJobs(ActionEvent event) {

    }

    @FXML
    void goToMarket(ActionEvent event) {

    }

    @FXML
    void goToUsers(ActionEvent event) throws IOException {
        Stage stage = (Stage) UsersButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/AdminDashboardUsers.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);stage.setTitle("Users management");
    }

    }




