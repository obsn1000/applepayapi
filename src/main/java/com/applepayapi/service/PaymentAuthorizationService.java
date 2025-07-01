package com.applepayapi.service;

import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class PaymentAuthorizationService {
    
    public PaymentAuthorizationService() {
        Stripe.apiKey = "your_stripe_secret_key";
    }
    
    public String processPayment(String paymentToken) {
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(1000L)
                .setCurrency("usd")
                .setPaymentMethod(paymentToken)
                .setConfirm(true)
                .build();
            
            PaymentIntent intent = PaymentIntent.create(params);
            
            if ("succeeded".equals(intent.getStatus())) {
                return "Payment successful";
            } else {
                return "Payment failed: " + intent.getStatus();
            }
        } catch (Exception e) {
            return "Payment error: " + e.getMessage();
        }
    }
}
