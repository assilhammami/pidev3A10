package com.esprit.Controllers.client.products;

import com.esprit.Controllers.payment.PaymentUiController;
import com.esprit.models.Panier;
import com.esprit.service.MyListenerCart;
import com.esprit.service.PanierService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
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
        panierList.addAll(panier.getUserCart(9));

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
        if(panierList.size()<1){
            showAlert(Alert.AlertType.ERROR, "ERROR", "Cart is empty");
        }else{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PaymentUI.fxml")); // Ensure correct path
                Parent root = loader.load();

                PaymentUiController paymentUiController = loader.getController();
                PanierService ps = new PanierService();
                float total = ps.getTotalAmount(9); //change with id_user
                BigDecimal totalAmount = BigDecimal.valueOf(total); // Implement this method based on your cart
                paymentUiController.initData(totalAmount);
                paymentUiController.setMyCartController(this); //pass the controller to paymentUI

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL); // This line makes the window modal
                stage.setScene(new Scene(root));
                stage.setTitle("Complete Your Payment"); // Optional: Set a title for the payment window
                stage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "ERROR", "Failed to open payment screen.");
            }
        }

    }

    @FXML
    void RemoveFromCart(ActionEvent event) {
        if(panierList.size()<1) {
            showAlert(Alert.AlertType.ERROR, "ERROR", "Cart is empty");
        }else{
            PanierService ps = new PanierService();
            ps.supprimerDuPanier(9);
            Update();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Selected items removed successfully.");
        }

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}