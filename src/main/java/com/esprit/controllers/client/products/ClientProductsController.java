package com.esprit.Controllers.client.products;

import com.esprit.Controllers.admin.ProductItemController;
import com.esprit.models.Panier;
import com.esprit.models.Produits;
import com.esprit.service.AbonnementShopService;
import com.esprit.service.MyListener;
import com.esprit.service.PanierService;
import com.esprit.service.ProduitsService;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ClientProductsController implements Initializable {

    @FXML
    private Label Description;

    @FXML
    private Label Prix;

    @FXML
    private Label Stock;

    @FXML
    private Button btnAcheter;

    @FXML
    private ChoiceBox<String> filtreOptions;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView img;

    @FXML
    private Label nomProduit;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchInput;
    @FXML
    private ChoiceBox<String> filterBycategory;
    @FXML
    private Button subscribedBtn;

    @FXML
    private Button subscribeBtn;
    MyListener myListener;

    public Produits selectedProduct;
    int id_prod, checkStock;
    private List<Produits> produitsList = new ArrayList<>();

    private void setChosenProduct(Produits p) {
        btnAcheter.setVisible(true);
        btnAcheter.setDisable(false);
        selectedProduct = p;
        id_prod = p.getId_produit();
        nomProduit.setText(p.getNom());
        Description.setText(p.getDescription());
        Prix.setText(Float.toString(p.getPrix())+" DT");
        Image image = new Image(p.getImage_produit());
        img.setImage(image);
        checkStock = p.getStock();
        if(p.getStock() < 4){
            Stock.setText("only "+Integer.toString(p.getStock())+" left");
        }else{
            Stock.setText("");
        }

        if(checkStock < 1){
            Stock.setText("OUT OF STOCK");
            btnAcheter.setDisable(true);

        }
    }

    public void Update() {
    ProduitsService produit = new ProduitsService();
        produitsList.clear();
        grid.getChildren().

    clear();

    //METIERS
    //RECHERCHE
    String searchText = searchInput.getText().toLowerCase();
    List<Produits> allProducts = produit.afficherProduit();
        //recherche par nom
    List<Produits> filteredList = allProducts.stream()
            .filter(p -> p.getNom().toLowerCase().contains(searchText))
            .collect(Collectors.toList());

        // category filter
        String selectedCategory = filterBycategory.getValue();
        if (selectedCategory != null && !selectedCategory.equals("All Categories")) {
            filteredList = filteredList.stream()
                    .filter(p -> p.getCategorie().equals(selectedCategory))
                    .collect(Collectors.toList());
        }

        // price filter (tri)
    String selectedFilter = filtreOptions.getValue();
        if("Price (ASC)".

    equals(selectedFilter))

    {
        filteredList.sort(Comparator.comparing(Produits::getPrix));
    } else if("Price (DESC)".

    equals(selectedFilter))

    {
        filteredList.sort(Comparator.comparing(Produits::getPrix).reversed());
    }

    myListener =new

    MyListener() {
        @Override
        public void onClickListener (Produits p){
            setChosenProduct(p);
        }
    }

    ;

    int column = 0;
    int row = 1;
        try

    {
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
    } catch(
    IOException ex)

    {
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

    @FXML
    void acheterProduit(ActionEvent event) throws SQLException {
        ProduitsService produit = new ProduitsService();
        PanierService panier = new PanierService();
        Panier pan = new Panier(selectedProduct.getNom(), selectedProduct.getImage_produit(), selectedProduct.getPrix(), 1 , true, selectedProduct.getId_produit(), 9);
        panier.ajouterAuPanier(pan);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Product added to cart.");
        checkStock--;
        if(checkStock < 4){
            Stock.setText("       only "+Integer.toString(checkStock)+" left");
        }else{
            Stock.setText("");
        }
        if(checkStock < 1){
            Stock.setText("OUT OF STOCK");
            btnAcheter.setDisable(true);

        }
        Update();
    }

    @FXML
    void search(ActionEvent event) {
        Update();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomProduit.setText("");
        Description.setText("");
        Stock.setText("");
        Stock.setStyle("-fx-text-fill: #6C261F;");
        Prix.setText("");
        btnAcheter.setDisable(true);
        //ajout des options a choisir dans filter options
        filtreOptions.getItems().addAll("Price (ASC)", "Price (DESC)");
        filtreOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Update(); // This will call the Update function to refresh the list based on the new filter
        });
        //ajout des options a choisir dans filter categorie
        filterBycategory.getItems().addAll("All Categories","Table", "Tools");
        //prendre l'option all categories par defaut
        filterBycategory.getSelectionModel().select("All Categories");
        filterBycategory.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> Update());

        // Listener for searchInput text changes
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            Update(); // Call Update method whenever text changes
        });
        btnAcheter.setVisible(false);
        initSubsribeButton();
        Update();
    }
    private void initSubsribeButton(){
        AbonnementShopService abonne = new AbonnementShopService();
        if(abonne.isSubscribed(9)){ //id_user logged in
            subscribeBtn.setVisible(false);
            subscribedBtn.setVisible(true);
        }else{
            subscribeBtn.setVisible(true);
            subscribedBtn.setVisible(false);
        }
    }
    @FXML
    void GoToOrders(ActionEvent event) throws IOException {
        URL fxURL = getClass().getResource("/ClientOrders.fxml");
        FXMLLoader loader = new FXMLLoader(fxURL);
        Parent root = (Parent) loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Davincci - My Orders");
        stage.setScene(new Scene(root));
        stage.show();

    }
    @FXML
    void openMyCart(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/my_cart.fxml"));
            Parent parent = fxmlLoader.load();

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
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void subscribe(ActionEvent event) {
        subscribedBtn.setVisible(true);
        subscribeBtn.setVisible(false);
        AbonnementShopService abonne = new AbonnementShopService();
        abonne.toogleSubscription(9, true);
    }

    @FXML
    void unsubscribe(ActionEvent event) {
        subscribeBtn.setVisible(true);
        subscribedBtn.setVisible(false);
        AbonnementShopService abonne = new AbonnementShopService();
        abonne.toogleSubscription(9, false);
    }
}
