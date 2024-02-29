package com.esprit.Controllers.client.products;

import com.esprit.models.Commande;
import com.esprit.models.Panier;
import com.esprit.models.Produits;
import com.esprit.service.MyListenerCart;
import com.esprit.service.PanierService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CartItemController implements Initializable {

    @FXML
    private Label nom_produit;

    @FXML
    private ImageView img;

    @FXML
    private Label quantity;

    @FXML
    private Label total_price;

    @FXML
    private Button minusBtn;

    @FXML
    private Button selectedButton;

    @FXML
    private Button plusBtn;

    @FXML
    private Button selectButton;
    private MyListenerCart myListener;
    private Panier p;
    private int qte;
    private boolean isSelected;

    public void setData(Panier p, MyListenerCart myListener) {
        this.p = p;
        this.myListener = myListener;
        qte = p.getQuantite_produit();
        nom_produit.setText(p.getNom_produit());
        quantity.setText(Integer.toString(qte));
        total_price.setText(Float.toString(p.getPrix_commande() * qte));
        Image image = new Image(p.getImage_produit());
        img.setImage(image);
        isSelected = p.isSelected();
        if(isSelected){
            selectedButton.setVisible(true);
            selectButton.setVisible(false);
        }else{
            selectedButton.setVisible(false);
            selectButton.setVisible(true);
        }
    }

    @FXML
    void increaseQuantity(ActionEvent event) {
        qte++;
        quantity.setText(Integer.toString(qte));
        total_price.setText(Float.toString(p.getPrix_commande() * qte));
        minusBtn.setDisable(false);
    }

    @FXML
    void decreaseQuantity(ActionEvent event) {
        if(qte<=1){
            minusBtn.setDisable(true);
        }else{
            minusBtn.setDisable(false);
            qte--;
            quantity.setText(Integer.toString(qte));
            total_price.setText(Float.toString(p.getPrix_commande() * qte));
        }

    }

    @FXML
    void selectButton(ActionEvent event) {
        selectButton.setVisible(false);
        selectedButton.setVisible(true);
        PanierService ps = new PanierService();
        ps.setSelectedByPanierId(p.getId_panier(),true);

    }

    @FXML
    void selectedButton(ActionEvent event) {
        selectedButton.setVisible(false);
        selectButton.setVisible(true);
        PanierService ps = new PanierService();
        ps.setSelectedByPanierId(p.getId_panier(),false);

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(qte<=1){
            minusBtn.setDisable(true);
        }
    }

}