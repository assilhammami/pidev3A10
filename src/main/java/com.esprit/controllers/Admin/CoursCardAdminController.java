package com.esprit.controllers.Admin;

import com.esprit.models.cours;
import com.esprit.services.CoursService;
import com.esprit.services.MyListener;
import com.esprit.services.UserDataManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    @FXML
    private Button likeButton;

    @FXML
    private Button dislikeButton;

    @FXML
    private Label likeCount;

    @FXML
    private Label dislikeCount;
    public void setData(cours cours, MyListener myListener) {
        CoursService cs= new CoursService();
        this.cours = cours;
        this.myListener = myListener;
        nom.setText(cours.getNom());
        ImageView imageView = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        likeCount.setText(String.valueOf(cours.getLikes()));
        dislikeCount.setText(String.valueOf(cours.getDislikes()));

        likeButton.setOnAction(event -> {
            if (!UserDataManager.getInstance().hasLiked(cours.getId()) && !UserDataManager.getInstance().hasDisliked(cours.getId())) {
                cs.incrementLikes(cours.getId());
                cours.setLikes(cours.getLikes() + 1);
                likeCount.setText(String.valueOf(cours.getLikes()));
                UserDataManager.getInstance().addLike(cours.getId());
            } else {
                System.out.println("You have already reacted to this course.");
            }
        });

        dislikeButton.setOnAction(event -> {
            if (!UserDataManager.getInstance().hasDisliked(cours.getId()) && !UserDataManager.getInstance().hasLiked(cours.getId())) {
                cs.incrementDislikes(cours.getId());
                cours.setDislikes(cours.getDislikes() + 1);
                dislikeCount.setText(String.valueOf(cours.getDislikes()));
                UserDataManager.getInstance().addDislike(cours.getId());
            } else {
                System.out.println("You have already reacted to this course.");
            }
        });
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
