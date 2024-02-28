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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminProductsController implements Initializable, DataUpdateCallback {

    @FXML
    private Label Categorie;

    @FXML
    private Label Nom_produit;

    @FXML
    private Label Description;

    @FXML
    private ImageView Image_produit;

    @FXML
    private GridPane grid;

    @FXML
    private Label Prix;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Label Stock;

    @FXML
    private Label Date_creation;

    @FXML
    private TextField searchInput;


    @FXML
    private Button modifierBtn;

    @FXML
    private Button supprimerBtn;
    @FXML
    private ChoiceBox<String> filtreOptions;
    MyListener myListener;

    public Produits selectedProduct;
    int id_prod;
    private List<Produits> produitsList = new ArrayList<>();
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
        //Image_produit.setText(p.getImage_produit());
        // image = new Image(getClass().getResourceAsStream(m.getImage_produit()));
        // imgProd_ch.setImage(image);
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
        Nom_produit.setText("");
        Description.setText("");
        Prix.setText("");
        Stock.setText("");
        Date_creation.setStyle("-fx-text-fill: #6C261F;");
        Date_creation.setText("Choose a Product!");
        Categorie.setText("");
        supprimerBtn.setDisable(true);
        modifierBtn.setDisable(true);
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
    void modifierProduit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/edit_product.fxml")); // Replace with your actual path
            Parent editRoot = loader.load();

            //EditProductController editController = loader.getController();
           // editController.initData(id_prod); // Pass the id_prod to the edit scene

            Scene editScene = new Scene(editRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(editScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void supprimerProduit(ActionEvent event) {
        ProduitsService produit = new ProduitsService();
        produit.supprimerProduits(id_prod);
        Update();
    }

    @Override
    public void onUpdate() {
        Update();
    }

}
