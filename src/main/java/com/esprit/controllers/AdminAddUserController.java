package com.esprit.controllers;

import com.esprit.models.Admin;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AdminAddUserController implements Initializable {

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
    private CheckBox ck1;

    @FXML
    private CheckBox ck2;
    @FXML
    private TextField urlimage;

    UserService us=new UserService();

    @FXML
    void ChoisirType(MouseEvent event) {

    }

    @FXML
    void ShowPassword1(ActionEvent event) {
        if (ck1.isSelected()) {
            password1.setText(hiddenpassword1.getText());
            password1.setVisible(true);
            hiddenpassword1.setVisible(false);
            return;
        }
        hiddenpassword1.setText(password1.getText());
        hiddenpassword1.setVisible(true);
        password1.setVisible(false);

    }

    @FXML
    void ShowPassword2(ActionEvent event) {
        if (ck2.isSelected()) {
            password2.setText(hiddenpassword2.getText());
            password2.setVisible(true);
            hiddenpassword2.setVisible(false);
            return;
        }
        hiddenpassword2.setText(password2.getText());
        hiddenpassword2.setVisible(true);
        password2.setVisible(false);

    }

    @FXML
    void Submitdonnes(ActionEvent event) throws SQLException, IOException {
        String name = nom.getText();
        String lastname=prenom.getText();
        String num=num_telephone.getText();
        String username1=username.getText();
        String adresse=email.getText();
        String path=urlimage.getText();
        String datenaissance=birthdate.getText();

        String type=Choicebox.getValue();
        String pass1=us.getPassword(password1,hiddenpassword1);
        String pass2=us.getPassword(password2,hiddenpassword2);
        List<String> fields=new ArrayList<>(Arrays.asList(name, lastname, num, username1, adresse, path, datenaissance, type, pass1, pass2));

        if(!us.areFieldsNotEmpty(fields)){
            fieldserr.setVisible(true);
        }
        else {fieldserr.setVisible(false);
            phone_err.setVisible(!us.isValidPhoneNumber(num));
            emailinv.setVisible(!us.validateEmail(adresse));
            Emailused.setVisible(!us.isEmailAvailable(adresse));
            usernametaken.setVisible((!us.isUsernameAvailable(username1)));
            passerr.setVisible(!(pass1.equals(pass2)));
            birthErr.setVisible(!us.isValidBirthDate(datenaissance));}
        if(us.isValidPhoneNumber(num)&&us.validateEmail(adresse)&&us.isEmailAvailable(adresse)&&us.isUsernameAvailable(username1)&&(pass1.equals(pass2))&&us.isValidBirthDate(datenaissance)&&us.areFieldsNotEmpty(fields))
        {
            if(type.equals("CLIENT")){us.ajouter(new Client(path,name,lastname,adresse,pass1,username1,Integer.parseInt(num),datenaissance));}
            else if (type.equals("ADMIN")) {us.ajouter(new Admin(path,name,lastname,adresse,pass1,username1,Integer.parseInt(num),datenaissance));
            }
            } else {us.ajouter(new Artiste(path,name,lastname,adresse,pass1,username1,Integer.parseInt(num),datenaissance));}
            Stage stage=(Stage) Submit.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/test.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);

        }



    @FXML
    void UploadImage(ActionEvent event) {
        String url=urlimage.getText();
        Image image = new Image(url);
        Photo_de_profil.setImage(image);
        Photo_de_profil.setVisible(true);


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Choicebox.getItems().addAll("ARTISTE","CLIENT","ADMIN");

    }





}
