package com.esprit.controllers;

import com.esprit.models.Artiste;
import com.esprit.models.Client;
import com.esprit.models.User;
import com.esprit.services.UserDataManager;
import com.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewPasswordController {

    @FXML
    private Button Button_Login;

    @FXML
    private Button Button_cancel;

    @FXML
    private TextField CodeErr;

    @FXML
    private Label CodeLabel;

    @FXML
    private ImageView eyeclosed;

    @FXML
    private ImageView eyeclosed1;

    @FXML
    private ImageView eyeopen;

    @FXML
    private ImageView eyeopen1;

    @FXML
    private TextField fieldserr;

    @FXML
    private PasswordField hiddenpassword1;

    @FXML
    private PasswordField hiddenpassword2;

    @FXML
    private Button hide;

    @FXML
    private Button hide1;

    @FXML
    private Label label_user_id;

    @FXML
    private TextField passerr;

    @FXML
    private TextField password1;

    @FXML
    private TextField password2;

    @FXML
    private Button show;

    @FXML
    private Button show1;

    @FXML
    private TextField weakPassword;
    UserService us= new UserService();
    private UserDataManager userDataManager = UserDataManager.getInstance();

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
    void CheckCode(ActionEvent event) throws SQLException, IOException {
        User currentuser= us.getUser(userDataManager.getUserId());
        String pass1 = us.getPassword(password1, hiddenpassword1);
        String pass2 = us.getPassword(password2, hiddenpassword2);
        List<String> fields = new ArrayList<>(Arrays.asList(pass1, pass2));

        if (!us.areFieldsNotEmpty(fields)) {
            fieldserr.setVisible(true);
        } else {
            fieldserr.setVisible(false);

            passerr.setVisible(!(pass1.equals(pass2)));
            //birthErr.setVisible(!us.isValidBirthDate(datenaissance));
            weakPassword.setVisible(!us.isMdp(pass1));
        }

        if (us.areFieldsNotEmpty(fields)&&us.isMdp(pass1)&&pass1.equals(pass2))  {
            currentuser.setMot_de_passe(pass1);
            if (currentuser.getType().equals("CLIENT")) {
                us.modifier(new Client(currentuser.getId(), currentuser.getPhoto_de_profile(), currentuser.getNom(),currentuser.getPrenom(),currentuser.getEmail(),currentuser.getMot_de_passe(),currentuser.getUsername(),currentuser.getNum_telephone(),currentuser.getDate_de_naissance()));
            }if (currentuser.getType().equals("ADMIN")) {
                us.modifier(new User(currentuser.getId(), currentuser.getPhoto_de_profile(), currentuser.getNom(),currentuser.getPrenom(),currentuser.getEmail(),currentuser.getMot_de_passe(),currentuser.getUsername(),currentuser.getNum_telephone(),currentuser.getDate_de_naissance()));
            }
            else {
                us.modifier(new Artiste(currentuser.getId(), currentuser.getPhoto_de_profile(), currentuser.getNom(),currentuser.getPrenom(),currentuser.getEmail(),currentuser.getMot_de_passe(),currentuser.getUsername(),currentuser.getNum_telephone(),currentuser.getDate_de_naissance()));
            }
            Stage stage=(Stage) Button_Login.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login");

        }
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

}
