package com.applepayapi.config;

import lombok.RequiredArgsConstructor;
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
public class ApplePayConfig {
    private final AppleCertsProperty certs;

    @Bean
    public RestTemplate applePayRestTemplate() throws Exception {
        SSLContext sslContext = SSLContextBuilder.create()
            .loadKeyMaterial(
                new File(certs.getMerchantIdPath()),
                getPassword(),
                getPassword())
            .loadTrustMaterial(
                new File(certs.getPaymentProcessingPath()),
                null)
            .build();

        HttpClient client = HttpClients.custom()
            .setSSLContext(sslContext)
            .build();

        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(client));
    }

    private char[] getPassword() {
        return System.getenv("APPLE_PAY_CERT_PASSWORD").toCharArray();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
