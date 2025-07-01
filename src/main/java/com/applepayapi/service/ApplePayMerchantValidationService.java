package com.applepayapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class ApplePayMerchantValidationService {
    private static final String APPLE_VALIDATION_URL = "https://apple-pay-gateway.apple.com/paymentservices/paymentSession";

    @Value("${apple.merchant.id}")
    private String merchantIdentifier;
    @Value("${apple.merchant.domain}")
    private String domainName;
    @Value("${apple.merchant.display-name}")
    private String displayName;

    public String validateMerchant(String validationUrl) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> request = Map.of(
            "merchantIdentifier", merchantIdentifier,
            "domainName", domainName,
            "displayName", displayName
        );
        Map<String, String> response = restTemplate.postForObject(
            APPLE_VALIDATION_URL,
            request,
            Map.class
        );
        return response.get("merchantSession");
    }
}
