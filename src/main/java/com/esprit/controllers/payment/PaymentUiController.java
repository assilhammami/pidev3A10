package com.esprit.Controllers.payment;

import com.esprit.Controllers.client.products.MyCartController;
import com.esprit.service.PanierService;
import com.esprit.utils.StripeUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class PaymentUiController {

    @FXML
    private TextField MM;

    @FXML
    private TextField YY;

    @FXML
    private Button submitBTN;

    @FXML
    private TextField CCV;

    @FXML
    private TextField cardNumber;

    BigDecimal price;
    private MyCartController myCartController;

    public void setMyCartController(MyCartController myCartController) {
        this.myCartController = myCartController;
    }

    @FXML
    void SubmitPayment(ActionEvent event) {
        if (!validateCardDetails()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please check your card details.");
            return;
        }
        int amountInCents = price.multiply(BigDecimal.valueOf(100)).intValueExact();
        String currency = "usd";
        try {
            String clientSecret = StripeUtils.createPaymentIntent(amountInCents, currency);

            if (clientSecret != null) {
                System.out.println("PaymentIntent created successfully. Client secret: " + clientSecret);

                PanierService panierService = new PanierService();
                int userId = 9;
                int didBuy = panierService.buySelected(userId);
                PanierService ps = new PanierService();
                if(didBuy == 0){
                ps.supprimerDuPanier(9);
                    //n3aytou lel UPDATE FEL MYCART w nsakrou fenetre el paymentUI
                    if (myCartController != null) {
                        myCartController.Update(); // Call the update method on MyCartController
                    }
                    // Close the payment window
                    Stage stage = (Stage) submitBTN.getScene().getWindow(); // Assuming submitBTN is part of the scene
                    stage.close();


                showAlert(Alert.AlertType.INFORMATION, "Success", "Order passed successfully.");
                    System.out.println("Order processed successfully.");
            }else{
                showAlert(Alert.AlertType.ERROR, "ERROR", "Order not passed due to high quantity.");
                    System.out.println("Order processing failed.");
            }
            } else {
                System.out.println("Failed to create PaymentIntent.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initData(BigDecimal totalAmount) {
        price = totalAmount;
    }


    private boolean validateCardDetails() {
        String cardNum = cardNumber.getText();
        String mm = MM.getText();
        String yy = YY.getText();
        String ccv = CCV.getText();

        // Basic validation criteria, consider using more sophisticated validation in production
        if (cardNum == null || cardNum.length() < 16 || cardNum.length() > 16) {
            return false;
        }
        if (mm == null || mm.length() != 2 || Integer.parseInt(mm) < 1 || Integer.parseInt(mm) > 12) {
            return false;
        }
        if (yy == null || yy.length() != 2 || Integer.parseInt(yy) < 24) {
            return false;
        }
        if (ccv == null || ccv.length() < 3 || ccv.length() > 4) {
            return false;
        }
        return true;
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
