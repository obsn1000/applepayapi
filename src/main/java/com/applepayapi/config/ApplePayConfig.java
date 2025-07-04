package com.applepayapi.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import java.io.File;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplePayConfig {
    private final AppleCertsProperty certs;

    @Bean
    public RestTemplate applePayRestTemplate() throws Exception {
        File merchantIdFile = new File(certs.getMerchantIdPath());
        File paymentProcessingFile = new File(certs.getPaymentProcessingPath());
        
        // Check if certificate files exist
        if (!merchantIdFile.exists() || !paymentProcessingFile.exists()) {
            log.warn("Apple Pay certificate files not found. Using default RestTemplate without SSL configuration.");
            log.warn("Expected files: {} and {}", merchantIdFile.getAbsolutePath(), paymentProcessingFile.getAbsolutePath());
            return new RestTemplate();
        }
        
        // Check if password is available
        String password = System.getenv("APPLE_PAY_CERT_PASSWORD");
        if (password == null || password.isEmpty()) {
            log.warn("APPLE_PAY_CERT_PASSWORD environment variable not set. Using default RestTemplate.");
            return new RestTemplate();
        }
        
        try {
            SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyMaterial(
                    merchantIdFile,
                    password.toCharArray(),
                    password.toCharArray())
                .loadTrustMaterial(
                    paymentProcessingFile,
                    null)
                .build();

            HttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                .build();

            log.info("Apple Pay SSL configuration loaded successfully");
            return new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));
        } catch (Exception e) {
            log.error("Failed to configure Apple Pay SSL context: {}", e.getMessage());
            log.warn("Falling back to default RestTemplate");
            return new RestTemplate();
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
