package com.esprit.tests;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;



import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import javafx.stage.Stage;


import java.io.IOException;

public class MainProgGUI extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminAffichageProduits.fxml"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
 Gestion-Evènements
        primaryStage.setTitle("les events");
        primaryStage.show();
    }
}

        primaryStage.setTitle("Login");
        primaryStage.show();
    }




    }



       FXMLLoader loader = new FXMLLoader(getClass().getResource("/ClientProducts.fxml"));
      // FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminProducts.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Products");
        primaryStage.show();
    }
}


