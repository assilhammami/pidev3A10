package com.esprit.controllers;

import com.esprit.models.User;
import com.esprit.services.UserDataManager;
import com.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class MdpOublieController {

    @FXML
    private Button Button_cancel;

    @FXML
    private Button Button_continue;

    @FXML
    private TextField activateErr;

    @FXML
    private TextField errorField;

    @FXML
    private TextField fieldserr;

    @FXML
    private TextField usernametext;
    UserService us = new UserService();

    private void send_SMS(int recipnum, int code){
        String ACCOUNT_SID = "ACa3de239404d7de45d78f0e92c7a7f685";
        String AUTH_TOKEN = "9e4d76879c4783afb3e5f94639be1235";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String recipientNumber = "+216"+recipnum;
        String message = "Greetings Artist ,\n"
                + "Your password reset code is : "+code;


        Message twilioMessage = Message.creator(
                new PhoneNumber(recipientNumber),
                new PhoneNumber("+19286155773"),message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }
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
    void Checklogin(ActionEvent event) throws InvalidAlgorithmParameterException, SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException {
        if(usernametext.getText()=="")
        {
            fieldserr.setVisible(true);} else {
            fieldserr.setVisible(false);

    }

            if(!us.isUsernameAvailable(usernametext.getText().toString()))
            {   errorField.setVisible(false);
                User U=new User(),u=new User();
                U=us.getUser(us.getUserId(usernametext.getText().toString()));
                System.out.println(U);
                int code=0;
                System.out.println(U.getNum_telephone());
                code=us.generer();
                System.out.println(code);
                send_SMS(U.getNum_telephone(), code);
                Stage stage=(Stage) Button_continue.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Code.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Code");
                CodeController codecontroller=loader.getController();
                UserDataManager.getInstance().setUserId(U.getId());
                codecontroller.setCode(code);
            }
            else
            {
               errorField.setVisible(true);
            }


    }

}

