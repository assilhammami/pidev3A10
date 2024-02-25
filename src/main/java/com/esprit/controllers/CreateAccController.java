package com.esprit.controllers;

import com.esprit.models.Artiste;
import com.esprit.models.Client;
import com.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CreateAccController implements Initializable {

    @FXML
    private Hyperlink hyperLlogin;
    @FXML
    private TextField birthErr;
    @FXML
    private TextField Emailused;
    @FXML
    private TextField emailinv;

    @FXML
    private TextField fieldserr;
    @FXML
    private TextField phone_err;

    @FXML
    private TextField usernametaken;
    @FXML
    private TextField passerr;

    @FXML
    private ChoiceBox<String> Choicebox;

    @FXML
    private ImageView Photo_de_profil;

    @FXML
    private Button Submit;

    @FXML
    private Button Uploadbutton;

    @FXML
    private TextField birthdate;

    @FXML
    private TextField email;

    @FXML
    private TextField nom;

    @FXML
    private TextField num_telephone;

    @FXML
    private TextField password1;

    @FXML
    private TextField password2;
    @FXML
    private PasswordField hiddenpassword1;

    @FXML
    private PasswordField hiddenpassword2;

    @FXML
    private TextField prenom;

    @FXML
    private TextField username;

    @FXML
    private TextField urlimage;
    @FXML
    private DatePicker birthdate1;
    @FXML
    private Button show;

    @FXML
    private Button show1;
    @FXML
    private Button hide;

    @FXML
    private Button hide1;
    @FXML
    private ImageView eyeclosed;

    @FXML
    private ImageView eyeclosed1;

    @FXML
    private ImageView eyeopen;

    @FXML
    private ImageView eyeopen1;

UserService us=new UserService();

    @FXML
    void ChoisirType(MouseEvent event) {

    }



    @FXML
    void Hidepassword11(ActionEvent event) {
        hiddenpassword2.setText(password2.getText());
        hiddenpassword2.setVisible(true);
        password2.setVisible(false);
        hide1.setVisible(false);
        show1.setVisible(true);
    }

    @FXML
    void ShowPassword(ActionEvent event) {
        password1.setText(hiddenpassword1.getText());
        password1.setVisible(true);
        hiddenpassword1.setVisible(false);
        hide.setVisible(true);
        show.setVisible(false);
    }
    @FXML
    void hidePassword(ActionEvent event) {
        hiddenpassword1.setText(password1.getText());
        hiddenpassword1.setVisible(true);
        password1.setVisible(false);
        hide.setVisible(false);
        show.setVisible(true);

    }

    @FXML
    void Showpassword11(ActionEvent event) {
        password2.setText(hiddenpassword2.getText());
        password2.setVisible(true);
        hiddenpassword2.setVisible(false);
        hide1.setVisible(true);
        show1.setVisible(false);
    }

    @FXML
    void Submitdonnes(ActionEvent event) throws SQLException, IOException {
        String name = nom.getText();
        String lastname = prenom.getText();
        String num = num_telephone.getText();
        String username1 = username.getText();
        String adresse = email.getText();
        String path = urlimage.getText();

        // Convertir le chemin de l'image en Blob


        String datenaissance = ((TextField)birthdate1.getEditor()).getText();
        String type = Choicebox.getValue();
        String pass1 = us.getPassword(password1, hiddenpassword1);
        String pass2 = us.getPassword(password2, hiddenpassword2);
        List<String> fields = new ArrayList<>(Arrays.asList(name, lastname, num, username1, adresse, path, datenaissance, type, pass1, pass2));

        if (!us.areFieldsNotEmpty(fields)) {
            fieldserr.setVisible(true);
        } else {
            fieldserr.setVisible(false);
            phone_err.setVisible(!us.isValidPhoneNumber(num));
            emailinv.setVisible(!us.validateEmail(adresse));
            Emailused.setVisible(!us.isEmailAvailable(adresse));
            usernametaken.setVisible(!us.isUsernameAvailable(username1));
            passerr.setVisible(!(pass1.equals(pass2)));
            //birthErr.setVisible(!us.isValidBirthDate(datenaissance));
        }

        if (us.isValidPhoneNumber(num) && us.validateEmail(adresse) &&
                us.isEmailAvailable(adresse) && us.isUsernameAvailable(username1) &&
                (pass1.equals(pass2)) &&/* us.isValidBirthDate(datenaissance) &&*/ us.areFieldsNotEmpty(fields)) {
            if (type.equals("CLIENT")) {
                us.ajouter(new Client(path, name, lastname, adresse, pass1, username1, Integer.parseInt(num), datenaissance));
            } else {
                us.ajouter(new Artiste(path, name, lastname, adresse, pass1, username1, Integer.parseInt(num), datenaissance));
            }
            Stage stage=(Stage) Submit.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Welcome.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Welcome!");
            AffichageNouveau an=loader.getController();
            an.setImage(Photo_de_profil.getImage());
            an.setLbusername(username1);
        }

    }

    @FXML
    void UploadImage(ActionEvent event) throws IOException {
        /*String url=urlimage.getText();
        Image image = new Image(url);
        Photo_de_profil.setImage(image);
        Photo_de_profil.setVisible(true);*/
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            urlimage.setText(selectedFile.getAbsolutePath());
            Image image = new Image(selectedFile.toURI().toString());
            Photo_de_profil.setImage(image);
            Photo_de_profil.setVisible(true);
        } else {
            System.out.println("file is not valid");
        }


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Choicebox.getItems().addAll("ARTISTE","CLIENT");

    }

    @FXML
    void goToLoginPage(ActionEvent event) throws IOException {
        Stage stage=(Stage) hyperLlogin.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");

    }



}


