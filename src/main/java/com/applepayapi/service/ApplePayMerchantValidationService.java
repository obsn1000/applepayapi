package com.applepayapi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class ApplePayMerchantValidationService {
    
    private static final String APPLE_VALIDATION_URL = "https://apple-pay-gateway.apple.com/paymentservices/paymentSession";
    
    public String validateMerchant(String validationUrl) {
        RestTemplate restTemplate = new RestTemplate();
        
        Map<String, String> request = Map.of(
            "merchantIdentifier", "your.merchant.id",
            "domainName", "yourdomain.com",
            "displayName", "Your Store Name"
        );
        
        Map<String, String> response = restTemplate.postForObject(
            APPLE_VALIDATION_URL, 
            request, 
            Map.class
        );
        
        return response.get("merchantSession");
    }
}
