package com.esprit.Controllers.admin;

import com.esprit.models.Commande;
import com.esprit.service.CommandeService;
import com.esprit.service.MyListenerCommande;
import com.esprit.service.ProduitsService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderItemController implements Initializable {

    @FXML
    private Label Date_commande;

    @FXML
    private Label id;

    @FXML
    private ImageView img;

    private MyListenerCommande myListener;
    private Commande c;

    public void setData(Commande c, MyListenerCommande myListener) {
        this.c = c;

        this.myListener = myListener;
        Date_commande.setText(c.getDate_commande());
        id.setText("ID user: "+c.getId());
//        nomProd.setText(m.getNom_produit());
//        typeProd.setText(m.getType_produit());
//        // Image image = new Image(getClass().getResourceAsStream(m.getImage_produit()));
//
//        //imgProd.setImage(image);
//        prixProd.setText(Integer.toString(m.getPrix_produit()));
//        //tailleProd.setText(Integer.toString(m.getTaille_produit()));
//        descriptionProd.setText(m.getDescription_produit());
//        quantiteProd.setText(Integer.toString(m.getQuantite_produit()));
//        //idProd.setText(Integer.toString(m.getId_produit()));
//        id_prod.setText(Integer.toString(m.getId_produit()));

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(c);
    }
}
