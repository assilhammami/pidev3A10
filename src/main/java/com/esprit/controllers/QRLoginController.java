package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.services.UserDataManager;
import com.esprit.services.UserService;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class QRLoginController {

    @FXML
    private Button Button_cancel;

    @FXML
    private Button Button_continue;

    @FXML
    private ImageView Photo_de_profil;

    @FXML
    private TextField QR_read;

    @FXML
    private Button Uploadbutton;

    @FXML
    private TextField errorField1;

    @FXML
    private TextField fieldserr1;

    @FXML
    private TextField loggedinfield;

    @FXML
    private TextField urlimage;
    @FXML
    private TextField activateErr;


    @FXML
    void Cancel(ActionEvent event) throws IOException {
        Stage stage=(Stage) Button_cancel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
    }

    @FXML
    void Checklogin(ActionEvent event) throws IOException, SQLException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String url = urlimage.getText();

        if (url.isEmpty()) {
            fieldserr1.setVisible(true);
        } else {
            fieldserr1.setVisible(false);
            try {
                // Charger l'image depuis l'URL
                InputStream barcodeInputStream = new FileInputStream(urlimage.getText());
                BufferedImage barcBufferedImage = ImageIO.read(barcodeInputStream);

                // Décodeur QR
                LuminanceSource source = new BufferedImageLuminanceSource(barcBufferedImage);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Reader reader = new MultiFormatReader();
                Result result = reader.decode(bitmap);

                // Afficher le texte extrait
                System.out.println(result.getText());
                QR_read.setText(result.getText());


                UserService us = new UserService();

                if (!us.isUsernameAvailable(QR_read.getText())) {
                    System.out.println("Login successful");
                    User user = us.getUtilisateurUsernameouEmail(QR_read.getText());
                    System.out.println(user);
                    if (user.getActive()) {
                        System.out.println("Account is active");
                        System.out.println("logged in successfully!");
                        loggedinfield.setVisible(true);
                        errorField1.setVisible(false);
                        activateErr.setVisible(false);
                        int userId = us.getUserId(QR_read.getText());
                        if (us.getUser(userId).getType().equals("ADMIN")) {
                            UserDataManager.getInstance().setUserId(userId);

                            // Rediriger vers le tableau de bord de l'administrateur
                            Stage stage = (Stage) Button_continue.getScene().getWindow();
                            Parent root = FXMLLoader.load(getClass().getResource("/AdminDashboard.fxml"));
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setTitle("Admin Dashboard");

                            System.out.println(userId);
                        } else {
                            // Rediriger vers la page d'accueil
                            UserDataManager.getInstance().setUserId(userId);

                            Stage stage = (Stage) Button_continue.getScene().getWindow();
                            Parent root = FXMLLoader.load(getClass().getResource("/HomePage.fxml"));
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setTitle("Home");
                        }
                    } else {
                        System.out.println("Account is not active");
                        errorField1.setVisible(false);
                        activateErr.setVisible(true);
                    }
                } else {
                    System.out.println("Invalid login");
                    loggedinfield.setVisible(false);
                    errorField1.setVisible(true);
                }

            } catch (Exception e) {
                // Gérer les erreurs lors du décodage du code QR
                System.out.println("Error decoding QR code: " + e.getMessage());
                e.printStackTrace(); // Ceci est utile pour déboguer, mais vous pouvez le supprimer en production
                // Afficher une notification ou un message d'erreur à l'utilisateur
                errorField1.setVisible(true);
            }
        }
    }


    @FXML
    void UploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            urlimage.setText(imagePath);
            // Mettre à jour l'image dans l'interface graphique
            Image image = new Image(selectedFile.toURI().toString());
            Photo_de_profil.setImage(image);
            Photo_de_profil.setVisible(true);
        } else {
            System.out.println("Aucun fichier sélectionné");
        }
    }

}
