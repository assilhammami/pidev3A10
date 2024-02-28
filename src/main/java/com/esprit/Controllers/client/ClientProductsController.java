package com.esprit.Controllers.client.products;

import com.esprit.Controllers.admin.ProductItemController;
import com.esprit.models.Produits;
import com.esprit.service.MyListener;
import com.esprit.service.ProduitsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    MyListener myListener;

    public Produits selectedProduct;
    int id_prod, checkStock;
    private List<Produits> produitsList = new ArrayList<>();

    private void setChosenProduct(Produits p) {
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

    //TRI
    List<Produits> filteredList = allProducts.stream()
            .filter(p -> p.getNom().toLowerCase().contains(searchText))
            .collect(Collectors.toList());

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
    void acheterProduit(ActionEvent event) {
        ProduitsService produit = new ProduitsService();
        produit.acheterProduit(id_prod, 8);
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
        filtreOptions.getItems().addAll("Price (ASC)", "Price (DESC)");
        filtreOptions.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Update(); // This will call the Update function to refresh the list based on the new filter
        });
        Update();
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

}
