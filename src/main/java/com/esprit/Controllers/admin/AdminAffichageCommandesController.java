package com.esprit.Controllers.admin;

import com.esprit.models.Commande;
import com.esprit.service.CommandeService;
import com.esprit.service.MyListenerCommande;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;

public class AdminAffichageCommandesController implements Initializable {
    @FXML
    private Label Date_commande;

    @FXML
    private TextField Id;

    @FXML
    private TextField Id_produit;

    @FXML
    private VBox chosen;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView img;

    @FXML
    private TextField montant_total;

    @FXML
    private TextField quantite;

    @FXML
    private ScrollPane scroll;

    MyListenerCommande myListenerCommande;

    public Commande selectedOrder;
    private List<Commande> commandesList = new ArrayList<>();
    private void setChosenOrder(Commande c) {
        selectedOrder = c;
        Date_commande.setText(c.getDate_commande());
        Id.setText(Integer.toString(c.getId()));
        Id_produit.setText(Integer.toString(c.getId_produit()));
        montant_total.setText(Float.toString(c.getMontant_totale()));
        quantite.setText(Integer.toString(c.getQuantite()));
        // image = new Image(getClass().getResourceAsStream(m.getImage_produit()));
        // imgProd_ch.setImage(image);
    }

    public void Update() {
        Commande c;
        CommandeService commande = new CommandeService();
        commandesList.clear();
        grid.getChildren().clear();
        commandesList.addAll(commande.afficherCommande());
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
                fxmlLoader.setLocation(getClass().getResource("/orders.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                OrderItemController orderController = fxmlLoader.getController();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Update();
    }
    @FXML
    void GoToProducts(ActionEvent event) throws IOException {
        URL fxURL = getClass().getResource("/AdminAffichageProduits.fxml");
        FXMLLoader loader = new FXMLLoader(fxURL);
        Parent root = (Parent) loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Davincci Admin - Products");
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void ajoutCommande(ActionEvent event) throws SQLException {
        CommandeService commande = new CommandeService();
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateCommande = dateObj.format(formatter);
        int idUser = Integer.parseInt(Id.getText());
        int idProd = Integer.parseInt(Id_produit.getText());
        float montantTotal = Float.parseFloat(montant_total.getText());
        int qte = Integer.parseInt(quantite.getText());

        Commande c = new Commande(dateCommande, montantTotal,qte,idProd,idUser);
        commande.ajouterCommande(c);
        Update();
    }

    @FXML
    void search(MouseEvent event) {

    }

    @FXML
    void supprimerCommande(ActionEvent event) {
        CommandeService commande = new CommandeService();
        commande.supprimerCommande(selectedOrder.getId_commande());
        commandesList.clear();
        grid.getChildren().clear();
        Update();
    }

}
