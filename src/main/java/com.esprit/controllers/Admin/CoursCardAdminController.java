package com.esprit.controllers.Admin;

import com.esprit.models.cours;
import com.esprit.services.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CoursCardAdminController implements Initializable {

    @FXML
    private ImageView img;
    @FXML
    private Label nom;
    private cours cours;
    private MyListener myListener;

    public void setData(cours cours, MyListener myListener) {
        this.cours = cours;
        this.myListener = myListener;
        nom.setText(cours.getNom());
        ImageView imageView = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        try {

            File file = new File("src/main/resources/images/" + cours.getImage());
            String imageUrl = file.toURI().toURL().toExternalForm();
            Image image = new Image(imageUrl);
            img.setImage(image);
            System.out.println(cours.getImage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(cours);
    }



}
