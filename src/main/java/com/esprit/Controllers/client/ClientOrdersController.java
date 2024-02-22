package com.esprit.Controllers.client;

import com.esprit.Controllers.admin.OrderItemController;
import com.esprit.models.Commande;
import com.esprit.service.CommandeService;
import com.esprit.service.MyListenerCommande;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
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

public class ClientOrdersController implements Initializable {

    @FXML
    private VBox chosen;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    private List<Commande> commandesList = new ArrayList<>();
    MyListenerCommande myListenerCommande;

    public Commande selectedOrder;

    private void setChosenOrder(Commande c) {
        selectedOrder = c;
    }

    public void Update() {
        Commande c;
        CommandeService commande = new CommandeService();
        commandesList.clear();
        grid.getChildren().clear();
        commandesList.addAll(commande.afficherCommandeParIdUser(21));
        myListenerCommande = new MyListenerCommande() {
            @Override
            public void onClickListener(Commande c) {
                setChosenOrder(c);

            }
        };

        int cln = 0;
        int l = 0;
        try {
            for (int i = 0; i < commandesList.size(); i++) {

                if (cln > 1) {
                    cln = 0;
                    l++;
                }
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/myorders.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                MyOrderItemController orderController = fxmlLoader.getController();
                orderController.setData(commandesList.get(i), myListenerCommande);

                grid.add(anchorPane, cln++, l);
                //grid weight
                grid.setMinWidth(134);
                grid.setPrefWidth(134);
                grid.setMaxWidth(134);//
                //height
                grid.setMinHeight(112);
                grid.setPrefHeight(112);
                grid.setMaxHeight(112);//
                grid.setLayoutY(10);


                GridPane.setMargin(anchorPane, new Insets(175, 0, 0, 70));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void GoToShop(ActionEvent event) throws IOException {
        URL fxURL = getClass().getResource("/ClientShop.fxml");
        FXMLLoader loader = new FXMLLoader(fxURL);
        Parent root = (Parent) loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Davincci Admin - Products");
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void search(MouseEvent event) {
        Update();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Update();
    }

}
