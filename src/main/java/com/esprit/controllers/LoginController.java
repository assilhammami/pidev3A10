package com.esprit.controllers;

import com.esprit.services.Encryptor;
import com.esprit.services.UserDataManager;
import com.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginController {

    @FXML
    private TextField activateErr;
    @FXML
    private Button Button_Login;

    @FXML
    private PasswordField Hiddenpassword;

    @FXML
    private TextField Visiblepassword;

    @FXML
    private CheckBox checkbox;

    @FXML
    private TextField usernametext;
    @FXML
    private TextField errorField;
    @FXML
    private TextField loggedinfield;
    @FXML
    private Hyperlink CreateAccHL;
    @FXML
    private Button hide;

    @FXML
    private Button show;
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
    private Hyperlink hlpassword;

    UserService us= new UserService();

    @FXML
    void ShowPassword(ActionEvent event) {
        Visiblepassword.setText(Hiddenpassword.getText());
        Visiblepassword.setVisible(true);
        Hiddenpassword.setVisible(false);
        hide.setVisible(true);
        show.setVisible(false);
    }
    @FXML
    void hidePassword(ActionEvent event) {
        Hiddenpassword.setText(Visiblepassword.getText());
        Hiddenpassword.setVisible(true);
        Visiblepassword.setVisible(false);
        hide.setVisible(false);
        show.setVisible(true);

    }
    @FXML
    void Checklogin(ActionEvent event) throws SQLException, IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String username = usernametext.getText();
        String password = us.getPassword(Visiblepassword,Hiddenpassword);

        List<String> fields = new ArrayList<>(Arrays.asList(username,password));
        if (!us.areFieldsNotEmpty(fields)) {
            fieldserr.setVisible(true);}
        else {fieldserr.setVisible(false);
            int userid=us.getUserId(username);

            if (us.verifierLogin(username,password)){
                System.out.println("Login successful");
                if(us.getUser(userid).getActive())
                { System.out.println("Account is active");
                    System.out.println("logged in successfully!");
                    loggedinfield.setVisible(true);
                    errorField.setVisible(false);
                    activateErr.setVisible(false);

                    int userId = us.getUserId(username);

                    if(us.getUser(userId).getType().equals("ADMIN"))
                    {UserDataManager.getInstance().setUserId(userId);

                        Stage stage = (Stage) Button_Login.getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getResource("/forumadmin.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);stage.setTitle("Admin Dashboard");

                        System.out.println(userId);}else {UserDataManager.getInstance().setUserId(userId);

                        Stage stage = (Stage) Button_Login.getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getResource("/test.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setTitle("Home");}}
                else {System.out.println("Account is not active");
                    activateErr.setVisible(true);};



            }else {
                System.out.println("Invalid login");
                System.out.println("invalid !");
                errorField.setVisible(true);
                loggedinfield.setVisible(false);
            }
        }


    }

    @FXML
    void CreateAccount(ActionEvent event) throws IOException {
        Stage stage=(Stage) CreateAccHL.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreateAccount.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Create Account");

    }




    @FXML
    void mdpoublier(ActionEvent event) throws IOException {
        Stage stage=(Stage) hlpassword.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MdpOublie.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Forgot Password");
    }

}