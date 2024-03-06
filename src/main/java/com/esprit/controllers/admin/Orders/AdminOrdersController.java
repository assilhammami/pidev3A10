package com.esprit.Controllers.admin.Orders;

import com.esprit.Controllers.admin.OrderItemController;
import com.esprit.Controllers.admin.ProductItemController;
import com.esprit.Controllers.admin.products.AdminAddProductController;
import com.esprit.models.Commande;
import com.esprit.models.Produits;
import com.esprit.service.CommandeService;
import com.esprit.service.DataUpdateCallback;
import com.esprit.service.MyListenerCommande;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminOrdersController implements Initializable, DataUpdateCallback {
    @FXML
    private Label Date_commande;

    @FXML
    private ImageView Image_produit;

    @FXML
    private Label Montant_total;

    @FXML
    private TextField Montant_total_edit;

    @FXML
    private Label Nom_produit;

    @FXML
    private TextField Nom_produit_edit;

    @FXML
    private Label Quantité;

    @FXML
    private TextField Quantité_edit;

    @FXML
    private ChoiceBox<?> filtreOptions;

    @FXML
    private GridPane grid;

    @FXML
    private Button modifierBtn;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchInput;

    @FXML
    private Button supprimerBtn;

    @FXML
    private Label user_email;

    @FXML
    private TextField user_email_edit;

    @FXML
    private Label user_name;

 int id_cmd;
    private boolean isEditMode = false;

    MyListenerCommande myListenerCommande;

    public Commande selectedOrder;
    String userEmail="";
    private List<Commande> commandesList = new ArrayList<>();
    private void setChosenOrder(Commande c) {
        supprimerBtn.setDisable(false);
        supprimerBtn.setVisible(true);
        modifierBtn.setDisable(false);
        modifierBtn.setVisible(true);
        CommandeService cs = new CommandeService();

        String username = cs.getUsernameById(c.getId());
        userEmail = cs.getUserEmailById(c.getId());
        String NomProduit = cs.getProductNameById(c.getId_produit());
        String imageProduit = cs.getProductImageById(c.getId_produit());

        selectedOrder = c;
        id_cmd=c.getId_commande();
        Image image = new Image(getClass().getResourceAsStream(imageProduit));
        Image_produit.setImage(image);
        Date_commande.setStyle("-fx-text-fill: #000000;");
        Date_commande.setText("Order Date: "+c.getDate_commande());
        user_name.setText("Name: "+username);
        user_email.setText("Email: "+userEmail);
        Nom_produit.setText(NomProduit);
        Montant_total.setText("Total: "+Float.toString(c.getMontant_totale())+" DT");
        Quantité.setText("Quantity: "+Integer.toString(c.getQuantite()));


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
        initScreen();
        Update();
    }

    @FXML
    void ajoutCommande(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Admin_add_order.fxml"));
            Parent parent = fxmlLoader.load();
            AdminAddOrderController addOrderController = fxmlLoader.getController();
            addOrderController.setDataUpdateCallback(this);
            Stage stage = new Stage();
            stage.setTitle("Add Order");
            stage.setScene(new Scene(parent));

            //block until closed
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void supprimerCommande(ActionEvent event) {
        CommandeService commande = new CommandeService();
        commande.supprimerCommande(id_cmd);
        initScreen();
        Update();

    }

    void initScreen(){
        Nom_produit.setText("");
        Quantité.setText("");
        Montant_total.setText("");
        user_name.setText("");
        Date_commande.setStyle("-fx-text-fill: #6C261F;");
        Date_commande.setText("Choose an Order!");
        user_email.setText("");
        Image_produit.setImage(null);

        supprimerBtn.setDisable(true);
        supprimerBtn.setVisible(false);
        modifierBtn.setDisable(true);
        modifierBtn.setVisible(false);

    }

    @Override
    public void onUpdate() {
        Update();
    }

    @FXML
    void GoToProducts(ActionEvent event) throws IOException {
        URL fxURL = getClass().getResource("/AdminProducts.fxml");
        FXMLLoader loader = new FXMLLoader(fxURL);
        Parent root = (Parent) loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Davincci Admin - Products");
        stage.setScene(new Scene(root));
        stage.show();

    }


    @FXML
    void modifierCommande(ActionEvent event) throws SQLException {
        isEditMode = !isEditMode;
        if (isEditMode) {
            // Switch to edit mode: Show TextFields, hide Labels
            //nom
            Nom_produit.setVisible(false);
            Nom_produit_edit.setVisible(true);
            Nom_produit_edit.setText(Nom_produit.getText());
            //Montant_total
            Montant_total.setVisible(false);
            Montant_total_edit.setVisible(true);
            Montant_total_edit.setText(Float.toString(selectedOrder.getMontant_totale()));
            // Stock
            Quantité.setVisible(false);
            Quantité_edit.setVisible(true);
            Quantité_edit.setText(Integer.toString(selectedOrder.getQuantite()));
            // user email
            user_email.setVisible(false);
            user_email_edit.setVisible(true);
            user_email_edit.setText(userEmail);

        } else {
            // Switch back to view mode: Show Labels, hide TextFields
            //nom
            Nom_produit.setText(Nom_produit_edit.getText());
            Nom_produit.setVisible(true);
            Nom_produit_edit.setVisible(false);
            //prix
            Montant_total.setText(Montant_total_edit.getText() + " DT");
            Montant_total.setVisible(true);
            Montant_total_edit.setVisible(false);
            //stock
            Quantité.setText("Stock: " + Quantité_edit.getText());
            Quantité.setVisible(true);
            Quantité_edit.setVisible(false);
            //Categorie
            user_email.setText("Email: " + user_email_edit.getText());
            user_email.setVisible(true);
            user_email_edit.setVisible(false);
            saveChanges();
        }
    }

    private void saveChanges() throws SQLException {
        String editedName = Nom_produit_edit.getText();
        float editedMontant = Float.parseFloat(Montant_total_edit.getText());
        int editedQuantite = Integer.parseInt(Quantité_edit.getText());
        String editedEmail = user_email_edit.getText();

        CommandeService cs = new CommandeService();
        int id_prod = cs.getProductIdByName(editedName);
        int id_user = cs.getUserIdByEmail(editedEmail);

        System.out.println(id_prod+"---"+id_user);
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateCommande = dateObj.format(formatter);
        Commande c = new Commande(selectedOrder.getId_commande(),dateCommande, editedMontant, editedQuantite, id_prod, id_user);
        cs.modifierCommande(c);
        System.out.println(id_prod+"---"+id_user);
        System.out.println(cs.getUserEmailById(id_user));
        Update();
    }
}

