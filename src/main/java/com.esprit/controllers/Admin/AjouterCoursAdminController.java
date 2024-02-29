package com.esprit.controllers.Admin;

import com.esprit.models.cours;
import com.esprit.services.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AjouterCoursAdminController implements Initializable {
    @FXML
    private TextField tfDescription;
    @FXML
    private TextField tfNom;
    @FXML
    private DatePicker tfDatepub;
    @FXML
    private TextField tfImage;

    private final CoursService cs = new CoursService();
    private Date date_pub;
    private String imagePathStr;

    public AjouterCoursAdminController() throws IOException {
    }

    @FXML
    void addCours(ActionEvent event) throws IOException {

                String errorMessage = "";
                if (tfNom.getText().isEmpty()) {
                    errorMessage += "Le nom est vide.\n";
                }
                if (tfDescription.getText().isEmpty()) {
                    errorMessage += "La description est vide.\n";
                }
                if (tfImage.getText().isEmpty()) {
                    errorMessage += "L'image est vide.\n";
                }
                if (!errorMessage.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText(errorMessage);
                    alert.show();
                } else if (cs.isNomUnique(tfNom.getText())) {
                    cs.ajouter(new cours(tfNom.getText(), tfDescription.getText(), Date.valueOf(tfDatepub.getValue()), tfImage.getText()));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cours ajouté ");
                    alert.setContentText("Cours ajouté avec succès !");
                    alert.show();
                    Stage stage = (Stage) tfNom.getScene().getWindow();

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("Le nom du cours doit être unique.");
                    alert.show();
                }
            }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/AfficheCoursAdmin.fxml"));

    @FXML
    void afficher(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/AfficheCoursAdmin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tfDatepub.setValue(LocalDate.now());
    }

    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {
                java.nio.file.Path resourcesDir = Paths.get("src/main/resources/images");
                if (!Files.exists(resourcesDir)) {
                    Files.createDirectories(resourcesDir);
                }

                java.nio.file.Path imagePath = resourcesDir.resolve(selectedFile.getName());
                Files.copy(selectedFile.toPath(), imagePath, StandardCopyOption.REPLACE_EXISTING);

                tfImage.setText(selectedFile.getName());
                System.out.println(tfImage);
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to upload image.");
                alert.showAndWait();
            }
        }
    }




}
