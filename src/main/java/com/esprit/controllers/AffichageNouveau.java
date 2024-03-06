package com.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class AffichageNouveau {

    @FXML
    private ImageView image;

    @FXML
    private Label lbusername;
    @FXML
    private Button Login;
    public void setLbusername(String nom) {
        lbusername.setText(nom);
    }

    public void setImage(Image url) {
        image.setImage(url);
        image.setVisible(true);}

    @FXML
    void goToLoginPage(ActionEvent event) throws IOException {
        Stage stage=(Stage) Login.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);


    }

}
