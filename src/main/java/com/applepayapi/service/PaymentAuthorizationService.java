package com.applepayapi.service;

import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class PaymentAuthorizationService {

    public PaymentResponse processPayment(String paymentToken) {
        try {
            Stripe.apiKey = "your_stripe_secret_key";
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(1000L)
                .setCurrency("usd")
                .setPaymentMethod(paymentToken)
                .setConfirm(true)
                .build();
            PaymentIntent intent = PaymentIntent.create(params);
            if ("succeeded".equals(intent.getStatus())) {
                return new PaymentResponse("success", "Payment successful");
            } else {
                return new PaymentResponse("failure", "Payment failed: " + intent.getStatus());
            }
        } catch (Exception e) {
            return new PaymentResponse("error", "Payment error: " + e.getMessage());
        }
    }
}
