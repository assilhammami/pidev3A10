package com.esprit.controllers;

import com.esprit.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CodeController {

    @FXML
    private Button Button_Login;

    @FXML
    private Button Button_cancel;

    @FXML
    private TextField CodeErr;

    @FXML
    private TextField fieldserr;

    @FXML
    private TextField usernametext;
    @FXML
    private Label label_user_id;

    @FXML
    private Label CodeLabel;


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
    void CheckCode(ActionEvent event) throws IOException {
if (CodeLabel.getText()!="") {
    fieldserr.setVisible(false);
    if (CodeLabel.getText().equals(usernametext.getText())) {
    fieldserr.setVisible(false);
CodeErr.setVisible(false);
        Stage stage=(Stage) Button_Login.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewPassword.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("New Password");}
    else {CodeErr.setVisible(true);}}
else{fieldserr.setVisible(true);CodeErr.setVisible(false);}}


    public void setCode(int code) {CodeLabel.setText(Integer.toString(code));
    }
}
