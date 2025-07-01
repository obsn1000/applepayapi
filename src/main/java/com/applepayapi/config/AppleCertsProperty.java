package com.applepayapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "apple.certs")
@Getter @Setter
public class AppleCertsProperty {
    private String dir = "config/certificates/applepay";
    private String merchantId = "merchant_id.pem";
    private String paymentProcessing = "apple_pay.pem";
    
    // Full paths for easy access
    public String getMerchantIdPath() {
        return dir + "/" + merchantId;
    }
    
    public String getPaymentProcessingPath() {
        return dir + "/" + paymentProcessing;
    }
}
