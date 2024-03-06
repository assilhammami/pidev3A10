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
    @FXML
    private Label nom;
    @FXML
    private Label prix;

    private MyListener myListener;
    private Produits p;

    public void setData(Produits p, MyListener myListener) {
        this.p = p;

        this.myListener = myListener;
        nom.setText(p.getNom());
        prix.setText(Float.toString(p.getPrix())+" DT");
        Image image = new Image(p.getImage_produit());
        img.setImage(image);


    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(p);
    }
}
