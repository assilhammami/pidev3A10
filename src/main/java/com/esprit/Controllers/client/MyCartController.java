package com.esprit.Controllers.client.products;

import com.esprit.Controllers.admin.OrderItemController;
import com.esprit.models.Commande;
import com.esprit.models.Panier;
import com.esprit.service.CommandeService;
import com.esprit.service.MyListenerCart;
import com.esprit.service.MyListenerCommande;
import com.esprit.service.PanierService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyCartController implements Initializable {

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    private List<Panier> panierList = new ArrayList<>();
    private MyListenerCart myListenerCart;

    public void Update() {
        Panier p;
        PanierService panier = new PanierService();
        panierList.clear();
        grid.getChildren().clear();
        panierList.addAll(panier.getUserCart(8));

        int column = 0;
        int row = 1;
        try {
            for (Panier mycart : panierList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/cart_item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CartItemController cartItemController = fxmlLoader.getController();
                cartItemController.setData(mycart, myListenerCart);

                if (column == 1) {
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
        Update();
    }



    @FXML
    void BuySelected(ActionEvent event) {
        PanierService ps = new PanierService();
        ps.buySelected(8);
        ps.supprimerDuPanier(8);

        Update();
    }

    @FXML
    void RemoveFromCart(ActionEvent event) {
        PanierService ps = new PanierService();
        ps.supprimerDuPanier(8);
        Update();
    }

}