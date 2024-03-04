package com.esprit.Controllers.client;

import com.esprit.models.Commande;
import com.esprit.service.MyListenerCommande;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MyOrderItemController implements Initializable {
    private MyListenerCommande myListener;
    private Commande c;
    @FXML
    private Label Date_commande;
    public void setData(Commande c, MyListenerCommande myListener){
        this.c = c;

        this.myListener = myListener;
        Date_commande.setText("Date de commande: "+c.getDate_commande());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
