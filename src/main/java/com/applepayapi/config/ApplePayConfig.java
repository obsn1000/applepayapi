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
}package com.applepayapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplePayConfig {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
