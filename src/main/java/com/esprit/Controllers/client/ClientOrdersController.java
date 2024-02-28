package com.esprit.Controllers.client.orders;

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
import java.util.List;
import java.util.ResourceBundle;

public class ClientOrdersController implements Initializable {

    @FXML
    private Label Date_commande;

    @FXML
    private ImageView Image_produit;

    @FXML
    private Label Montant_total;

    @FXML
    private Label Nom_produit;

    @FXML
    private ChoiceBox<?> filtreOptions;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchInput;
    int id_cmd;

    MyListenerCommande myListenerCommande;

    public Commande selectedOrder;
    private List<Commande> commandesList = new ArrayList<>();
    private void setChosenOrder(Commande c) {
        CommandeService cs = new CommandeService();

        String NomProduit = cs.getProductNameById(c.getId_produit());
        String imageProduit = cs.getProductImageById(c.getId_produit());

        selectedOrder = c;
        id_cmd=c.getId_commande();
        Image image = new Image(getClass().getResourceAsStream(imageProduit));
        Image_produit.setImage(image);
        Date_commande.setStyle("-fx-text-fill: #000000;");
        Date_commande.setText("Order Date: "+c.getDate_commande());
        Nom_produit.setText(NomProduit);
        Montant_total.setText("Total: "+Float.toString(c.getMontant_totale())+" DT");
    }

    public void Update() {
        Commande c;
        CommandeService commande = new CommandeService();
        commandesList.clear();
        grid.getChildren().clear();
        commandesList.addAll(commande.afficherCommandeByUserId(8));
        myListenerCommande = new MyListenerCommande() {
            @Override
            public void onClickListener(Commande c) {
                setChosenOrder(c);

            }
        };

        int column = 0;
        int row = 1;
        try {
            for (Commande order : commandesList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/orders.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                OrderItemController orderController = fxmlLoader.getController();
                orderController.setData(order, myListenerCommande);

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
    void search(ActionEvent event) {

    }
    @FXML
    void GoToProducts(ActionEvent event) throws IOException {
        URL fxURL = getClass().getResource("/ClientProducts.fxml");
        FXMLLoader loader = new FXMLLoader(fxURL);
        Parent root = (Parent) loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Davincci - Shop");
        stage.setScene(new Scene(root));
        stage.show();

    }
}
