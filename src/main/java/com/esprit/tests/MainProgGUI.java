package com.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainProgGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
<<<<<<< HEAD
       //FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/AfficheTravail.fxml")); // Créer un objet et charger la page fxml
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/Client/AfficheTravailClient.fxml")); // Créer un objet et charger la page fxml

        Parent root = loader.load(); // Charger le contenu du fichier FXML
        Scene scene = new Scene(root); // Créer une nouvelle scène à partir de la racine de l'interface
        primaryStage.setScene(scene); // Définir la scène créer comme scène principale
        primaryStage.setTitle("Mes travaux"); // Définir le titre de la fenêtre de l'application
        primaryStage.show(); // Afficher la fenêtre de l'application.
=======
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root;
        root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajout Publication");
        primaryStage.show();
>>>>>>> 2a5c9c04de20e210453822606aa0fa2e3fa3f9d9

    }
}
