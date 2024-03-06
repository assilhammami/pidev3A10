package com.esprit.controllers.admin;

import com.esprit.models.Event;
import com.esprit.services.EventService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class AffichageEventcontroller {

    @FXML
    private Label lbcapacity;

    @FXML
    private Label lbdate;

    @FXML
    private Label lbdescription;

    @FXML
    private Label lbimage;

    @FXML
    private Label lbnom;

    @FXML
    private Label lbplace;

    public void setLbNom(String nom) {
        lbnom.setText(nom);
    }
    public void setLbdate(String date) {
        lbdate.setText(date);
    }
    public void setlbcapacity(int capacity) {
        lbcapacity.setText(String.valueOf(capacity));
    }

    public void setLbdescription(String description) {
        lbdescription.setText(description);
    }
    public void setLbplace(String place) {
        lbplace.setText(place);
    }
    public void setLbimage(String image) {
        lbdate.setText(image);
    }


}
