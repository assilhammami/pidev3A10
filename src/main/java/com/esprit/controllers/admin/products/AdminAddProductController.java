package com.esprit.Controllers.admin.products;

import com.esprit.models.Produits;
import com.esprit.service.DataUpdateCallback;
import com.esprit.service.ProduitsService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
//openAI API
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;


public class AdminAddProductController implements Initializable {


    @FXML
    private TextArea Description;
    @FXML
    private ImageView img;

    @FXML
    private TextField Prix;

    @FXML
    private TextField Nom;

    @FXML
    private TextField Stock;
    @FXML
    private ChoiceBox<String> categorieOptions;

    private DataUpdateCallback dataUpdateCallback;
    private List<Produits> produitsList = new ArrayList<>();
    private String imgProdPath;


    public void setDataUpdateCallback(DataUpdateCallback callback) {
        this.dataUpdateCallback = callback;
    }
    @FXML
    void ajouterProduit(ActionEvent event) {

        //control de saisie
        try {
            if (Nom.getText().trim().isEmpty() || Description.getText().trim().isEmpty() || Prix.getText().trim().isEmpty() || Stock.getText().trim().isEmpty() || categorieOptions.getValue().compareTo("") == 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
                return;
            }

            float prixProduit;
            int stockProduit;
            try {
                prixProduit = Float.parseFloat(Prix.getText().trim());
                stockProduit = Integer.parseInt(Stock.getText().trim());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid number format for price or stock.");
                return;
            }

            if (prixProduit < 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Price must be positive.");
                return;
            }
            if (stockProduit <= 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Stock must be greater than 0.");
                return;
            }

            // CRUD AJOUT
            LocalDate dateObj = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateCreation = dateObj.format(formatter);
            String nomProduit = Nom.getText().trim();
            String descriptionProduit = Description.getText().trim();
            String categorieProduit = categorieOptions.getValue();

            String upperCaseNom;


            ProduitsService produit = new ProduitsService();
           // String aiDescription = generateProductDescription(descriptionProduit);

            Produits p = new Produits(nomProduit,descriptionProduit, prixProduit, stockProduit, dateCreation, categorieProduit, convertPathToUrl(imgProdPath));
            produitsList = produit.afficherProduit();
            for (Produits prod : produitsList) {
                upperCaseNom = prod.getNom().toUpperCase();
                if (upperCaseNom.equals(nomProduit.toUpperCase())) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Product Already exists!");
                    return;
                }
            }
            produit.ajouterProduits(p);

            if (dataUpdateCallback != null) {
                dataUpdateCallback.onUpdate();
            }

            showAlert(Alert.AlertType.INFORMATION, "Success", "Product added successfully.");


        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add product.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void initialize(URL url, ResourceBundle rb) {
        categorieOptions.getItems().addAll("Table", "Tools");
    }

    @FXML
    void handleUploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            imgProdPath = file.getAbsolutePath();
            Image image = new Image(convertPathToUrl(imgProdPath));
            img.setImage(image);
            // Here you could update your UI or do something with the selected image path
            System.out.println("Selected image: " + imgProdPath);
        }
    }

    private String convertPathToUrl(String filePath) {
        File file = new File(filePath);
        return file.toURI().toString();
    }

    //openAI API

//    private String generateProductDescription(String userMessage) {
//        String generatedText = "";
//        String openAiUrl = "https://api.openai.com/v1/chat/completions";
//        String apiKey = "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"; // Make sure to use your actual API key
//
//        HttpClient client = HttpClient.newHttpClient();
//
//        // Prepare the data to send in JSON format
//        Map<String, Object> data = new HashMap<>();
//        data.put("model", "gpt-3.5-turbo"); // Ensure you're using the correct model
//
//        // Use messages array for chat completions
//        List<Map<String, String>> messages = new ArrayList<>();
//        Map<String, String> message = new HashMap<>();
//        message.put("role", "user");
//        message.put("content", userMessage); // Use the text from your TextArea as the user's message
//        messages.add(message);
//
//        data.put("messages", messages);
//        data.put("max_tokens", 150); // Adjust max_tokens as needed
//        data.put("temperature", 0.7);
//
//        Gson gson = new Gson();
//        String jsonData = gson.toJson(data);
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(openAiUrl))
//                .header("Content-Type", "application/json")
//                .header("Authorization", "Bearer " + apiKey)
//                .POST(HttpRequest.BodyPublishers.ofString(jsonData))
//                .build();
//
//        try {
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            // Assuming the response format, parse to extract the actual message response
//            // The parsing would depend on the JSON structure of the response
//            String responseBody = response.body();
//
//            // Parse the JSON response to extract the generated text
//            JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);
//            JsonArray jsonMessages = jsonResponse.getAsJsonArray("choices");
//            if (!jsonMessages.isEmpty()) {
//                JsonObject lastMessage = jsonMessages.get(0).getAsJsonObject();
//                generatedText = lastMessage.getAsJsonObject().get("message").getAsString();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            generatedText="Failed to generate description";
//        }
//        return generatedText;
//    }


    private String generateProductDescription(String prompt) {
        HttpClient client = HttpClient.newHttpClient();
        //String apiKey = "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";
        String apiKey = "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";
        String openAiUrl = "https://api.openai.com/v1/chat/completions";

        // Prepare the data to send in JSON format
        Map<String, Object> data = new HashMap<>();
        data.put("model", "gpt-3.5-turbo");
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt); // Use the text from your TextArea as the user's message
        data.put("messages", messages);

        //data.put("content", prompt);
        data.put("max_tokens", 1);
        data.put("temperature", 0.7);

        Gson gson = new Gson();
        String jsonData = gson.toJson(data);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(openAiUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String description = response.body();
            int maxLength = 255;
            if (description.length() > maxLength) {
                description = description.substring(0, maxLength);
            }
            System.out.println(response.body());
            return description;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to generate description";
        }
    }

}


