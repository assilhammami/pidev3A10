package com.esprit.controllers;

import com.esprit.models.Event;
import com.esprit.services.EventService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.Parent;

import java.io.IOException;

public class AjoutEventcontroller {

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfcapa;

    @FXML
    private TextField tfdate;

    @FXML
    private TextField tfdes;

    @FXML
    private TextField tfpla;

    @FXML
    private TextField tfimage;
    @FXML
    void addEvent(ActionEvent event) throws IOException {
        EventService ev = new  EventService();
        ev.ajouter(new Event(tfNom.getText(), tfdate.getText(), tfdes.getText(), Integer.parseInt(tfcapa.getText()), tfpla.getText(),tfimage.getText()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Event ajoutée");
        alert.setContentText("Event ajoutée !");
        alert.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageEvent.fxml"));
        Parent root = loader.load();
        tfNom.getScene().setRoot(root);
        AffichageEventcontroller apc = loader.getController();
        apc.setLbNom(tfNom.getText());
        apc.setLbdate(tfdate.getText());
        apc.setLbdescription(tfdes.getText());
        apc.setlbcapacity(Integer.parseInt(tfcapa.getText()));
        apc.setLbplace(tfpla.getText());
        apc.setLbimage(tfimage.getText());
    }

}