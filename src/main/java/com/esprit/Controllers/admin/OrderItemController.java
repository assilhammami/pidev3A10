package com.esprit.Controllers.admin;

import com.esprit.models.Commande;
import com.esprit.service.MyListenerCommande;
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

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(c);
    }
}