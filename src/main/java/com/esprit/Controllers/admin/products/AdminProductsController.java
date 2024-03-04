package com.esprit.Controllers.admin.products;

import com.esprit.Controllers.admin.ProductItemController;
import com.esprit.models.Produits;
import com.esprit.service.DataUpdateCallback;
import com.esprit.service.MyListener;
import com.esprit.service.ProduitsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminProductsController implements Initializable, DataUpdateCallback {
    @FXML private Label Categorie, Nom_produit, Description, Prix, Stock, Date_creation;
    @FXML private ImageView Image_produit;
    @FXML private GridPane grid;
    @FXML private ScrollPane scroll;
    @FXML private TextField searchInput;
    @FXML private Button modifierBtn, supprimerBtn;
    @FXML private ChoiceBox<String> filtreOptions;
    MyListener myListener;

    public Produits selectedProduct;
    int id_prod;
    private List<Produits> produitsList = new ArrayList<>();
    private boolean isEditMode = false;

    @FXML private TextField Nom_produit_edit;
    @FXML private TextField Prix_edit;
    @FXML private TextArea Description_edit;
    @FXML private TextField Categorie_edit;
    @FXML private TextField Stock_edit;
    private void setChosenProduct(Produits p) {
        Date_creation.setStyle("-fx-text-fill: black;");
        supprimerBtn.setDisable(false);
        modifierBtn.setDisable(false);
        selectedProduct = p;
        id_prod = p.getId_produit();
        Nom_produit.setText(p.getNom());
        Description.setText(p.getDescription());
        Prix.setText(Float.toString(p.getPrix())+"DT");
        Stock.setText("Stock: "+Integer.toString(p.getStock()));
        Date_creation.setText("Created: "+p.getDate_Creation());
        Categorie.setText("Category: "+p.getCategorie());
        Image image = new Image(p.getImage_produit());
        Image_produit.setImage(image);
    }

    public void Update() {
        ProduitsService produit = new ProduitsService();
        produitsList.clear();
        grid.getChildren().clear();
        //METIERS
        //RECHERCHE
        String searchText = searchInput.getText().toLowerCase();
        List<Produits> allProducts = produit.afficherProduit();

        //TRI
        List<Produits> filteredList = allProducts.stream()
                .filter(p -> p.getNom().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        String selectedFilter = filtreOptions.getValue();
        if ("Price (ASC)".equals(selectedFilter)) {
            filteredList.sort(Comparator.comparing(Produits::getPrix));
        } else if ("Price (DESC)".equals(selectedFilter)) {
            filteredList.sort(Comparator.comparing(Produits::getPrix).reversed());
        }
        myListener = new MyListener() {
            @Override
            public void onClickListener(Produits p) {
                setChosenProduct(p);
            }
        };

        int column = 0;
        int row = 1;
        try {
            for (Produits prod : filteredList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/products.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ProductItemController productController = fxmlLoader.getController();
                productController.setData(prod, myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setHgap(10);
        grid.setVgap(10);
        scroll.setFitToWidth(true);
        scroll.setContent(grid);
        grid.requestLayout();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filtreOptions.getItems().addAll("Price (ASC)", "Price (DESC)");
        initScreen();
        filtreOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Update(); // This will call the Update function to refresh the list based on the new filter
        });
        Update();
    }


    @FXML
    void search(ActionEvent event) {
        Update();
    }


    @FXML
    void ajoutProduit(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/admin_add_product.fxml"));
            Parent parent = fxmlLoader.load();
            AdminAddProductController addProductController = fxmlLoader.getController();
            addProductController.setDataUpdateCallback(this);
            Stage stage = new Stage();
            stage.setTitle("Add Product");
            stage.setScene(new Scene(parent));

            //block until closed
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void modifierProduit(ActionEvent event) throws SQLException {
        isEditMode = !isEditMode;
        if (isEditMode) {
            // Switch to edit mode: Show TextFields, hide Labels
            //nom
            Nom_produit.setVisible(false);
            Nom_produit_edit.setVisible(true);
            Nom_produit_edit.setText(Nom_produit.getText());
            //prix
            Prix.setVisible(false);
            Prix_edit.setVisible(true);
            Prix_edit.setText(Float.toString(selectedProduct.getPrix()));
            //Description
            Description.setVisible(false);
            Description_edit.setVisible(true);
            Description_edit.setText(Description.getText());
            // Stock
            Stock.setVisible(false);
            Stock_edit.setVisible(true);
            Stock_edit.setText(Integer.toString(selectedProduct.getStock()));
            // Categorie
            Categorie.setVisible(false);
            Categorie_edit.setVisible(true);
            Categorie_edit.setText(selectedProduct.getCategorie());

        } else {
            // Switch back to view mode: Show Labels, hide TextFields
            //nom
            Nom_produit.setText(Nom_produit_edit.getText());
            Nom_produit.setVisible(true);
            Nom_produit_edit.setVisible(false);
            //prix
            Prix.setText(Prix_edit.getText()+" DT");
            Prix.setVisible(true);
            Prix_edit.setVisible(false);
            //description
            Description.setText(Description_edit.getText());
            Description.setVisible(true);
            Description_edit.setVisible(false);
            //stock
            Stock.setText("Stock: "+Stock_edit.getText());
            Stock.setVisible(true);
            Stock_edit.setVisible(false);
            //Categorie
            Categorie.setText("Category: "+selectedProduct.getCategorie());
            Categorie.setVisible(true);
            Categorie_edit.setVisible(false);

            // Here, call your function to save changes to the database or perform any action
            saveChanges();
        }

    }
    private void saveChanges() throws SQLException {
        String editedName = Nom_produit_edit.getText();
        String editedDescription = Description_edit.getText();
        float editedPrix = Float.parseFloat(Prix_edit.getText());
        int editedStock = Integer.parseInt(Stock_edit.getText());
        String editedCategorie = Categorie_edit.getText();
        ProduitsService produit = new ProduitsService();
        Produits updatedProduit = new Produits(id_prod, editedName, editedDescription,editedPrix,editedStock, selectedProduct.getDate_Creation(), editedCategorie, selectedProduct.getImage_produit());
        produit.modifierProduits(updatedProduit);
        Update();
    }

    @FXML
    void supprimerProduit(ActionEvent event) {
        ProduitsService produit = new ProduitsService();
        produit.supprimerProduits(id_prod);
        initScreen();
        Update();
    }

    @Override
    public void onUpdate() {
        Update();
    }

    void initScreen(){
        Nom_produit.setText("");
        Description.setText("");
        Prix.setText("");
        Stock.setText("");
        Date_creation.setStyle("-fx-text-fill: #6C261F;");
        Date_creation.setText("Choose a Product!");
        Categorie.setText("");
        supprimerBtn.setDisable(true);
        modifierBtn.setDisable(true);
        Image_produit.setImage(null);
    }
    @FXML
    void GoToOrders(ActionEvent event) throws IOException {
        URL fxURL = getClass().getResource("/AdminOrders.fxml");
        FXMLLoader loader = new FXMLLoader(fxURL);
        Parent root = (Parent) loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Davincci Admin - Orders");
        stage.setScene(new Scene(root));
        stage.show();

    }
}