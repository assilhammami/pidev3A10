package com.esprit.Controllers.admin;


import com.esprit.models.Produits;
import com.esprit.service.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;

public class ProductItemController implements Initializable {
    @FXML
    private ImageView img;

    private MyListener myListener;
    private Produits p;

    public void setData(Produits p, MyListener myListener) {
        this.p = p;

        this.myListener = myListener;
        Image image = new Image(p.getImage_produit());
        img.setImage(image);

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
        myListener.onClickListener(p);
    }
}
