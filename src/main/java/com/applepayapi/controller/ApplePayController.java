package com.applepayapi.controller;

import com.applepayapi.service.ApplePayMerchantValidationService;
import com.applepayapi.service.PaymentAuthorizationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/applepay")
public class ApplePayController {
    private final ApplePayMerchantValidationService validationService;
    private final PaymentAuthorizationService paymentService;

    public ApplePayController(ApplePayMerchantValidationService validationService,
                             PaymentAuthorizationService paymentService) {
        this.validationService = validationService;
        this.paymentService = paymentService;
    }

    @PostMapping("/validate-merchant")
    public ResponseEntity<String> validateMerchant(@RequestBody String validationUrl) {
        String merchantSession = validationService.validateMerchant(validationUrl);
        return ResponseEntity.ok(merchantSession);
    }

    @PostMapping("/authorize-payment")
    public ResponseEntity<String> authorizePayment(@RequestBody String paymentToken) {
        String paymentStatus = paymentService.processPayment(paymentToken);
        return ResponseEntity.ok(paymentStatus);
    }

    // Endpoint to serve the .pkpass file
    @GetMapping("/pkpass/download")
    public ResponseEntity<byte[]> downloadPkpass() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("public/REAPNET.pkpass");
        if (is == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] pkpassBytes = is.readAllBytes();
        is.close();
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=REAPNET.pkpass")
                .header("Content-Type", "application/vnd.apple.pkpass")
                .body(pkpassBytes);
    }
}
