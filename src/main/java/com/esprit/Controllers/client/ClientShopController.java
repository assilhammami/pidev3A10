package com.esprit.Controllers.client;

import com.esprit.Controllers.admin.ProductItemController;
import com.esprit.models.Produits;
import com.esprit.service.MyListener;
import com.esprit.service.ProduitsService;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientShopController implements Initializable {

    @FXML
    private Label Description;

    @FXML
    private Label Nom_produit;

    @FXML
    private Label Prix;

    @FXML
    private Label Stock;

    @FXML
    private VBox chosen;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView img;
    @FXML
    private Button btnAcheter;

    @FXML
    private ScrollPane scroll;
    MyListener myListener;
    int id_prod;

    public Produits selectedProduct;
    private List<Produits> produitsList = new ArrayList<>();
    int checkStock;
    private void setChosenProduct(Produits p) {
        btnAcheter.setDisable(false);
        selectedProduct = p;
        id_prod = p.getId_produit();
        Nom_produit.setText(p.getNom());
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

        // image = new Image(getClass().getResourceAsStream(m.getImage_produit()));
        // imgProd_ch.setImage(image);
    }
    public void Update() {
        Produits p;
        ProduitsService produit = new ProduitsService();
        produitsList.clear();
        grid.getChildren().clear();
        produitsList.addAll(produit.afficherProduit());
        myListener = new MyListener() {
            @Override
            public void onClickListener(Produits p) {
                setChosenProduct(p);

            }
        };

        int c = 0;
        int l = 0;
        try {
            for (int i = 0; i < produitsList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/products.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                ProductItemController productController = fxmlLoader.getController();
                productController.setData(produitsList.get(i), myListener);
                if (c > 2) {
                    c = 0;
                    l++;
                }
                grid.add(anchorPane, c++, l);
                //grid weight
                grid.setMinWidth(215);
                grid.setPrefWidth(215);
                grid.setMaxWidth(215);//
                //height
                grid.setMinHeight(170);
                grid.setPrefHeight(170);
                grid.setMaxHeight(170);//
                grid.setLayoutY(10);


                GridPane.setMargin(anchorPane, new Insets(215, 0, 0, 65));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Nom_produit.setText("");
        Description.setText("");
        Stock.setText("");
        Prix.setText("");
        btnAcheter.setDisable(true);
        Update();
    }

    @FXML
    void acheterProduit(ActionEvent event) {
        ProduitsService produit = new ProduitsService();
        produit.acheterProduit(id_prod, 21);
        checkStock--;
        if(checkStock < 4){
            Stock.setText("only "+Integer.toString(checkStock)+" left");
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
    void search(MouseEvent event) {

    }
    @FXML
    void GoToMyOrders(ActionEvent event) throws IOException {
        URL fxURL = getClass().getResource("/ClientOrders.fxml");
        FXMLLoader loader = new FXMLLoader(fxURL);
        Parent root = (Parent) loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Davincci Client - Orders");
        stage.setScene(new Scene(root));
        stage.show();

    }


}