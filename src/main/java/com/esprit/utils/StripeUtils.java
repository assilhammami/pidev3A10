package com.esprit.utils;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public class StripeUtils {

    static {
        // Initialize Stripe with your secret key
        Stripe.apiKey = "sk_test_51OqkdvGCcBolQTR1ueX7ZOpBvSLMD8ativTXySv5uc0jhxOBF7TpEHRtsdRrOfJIuIdSe67WKeLaZDapeUP7Ox1W00VyNpxTCb";
    }

    public static String createPaymentIntent(int amount, String currency) {
        try {
            // Create a payment intent
            PaymentIntent paymentIntent = PaymentIntent.create(
                    java.util.Map.of(
                            "amount", amount,
                            "currency", currency,
                            "payment_method_types", java.util.List.of("card")
                    )
            );
            return paymentIntent.getClientSecret();
        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
