package com.applepayapi.kban;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kban")
public class KbanController {
    @Autowired
    private KbanConfig kbanConfig;

    // Get LL5 config
    @GetMapping("/ll5")
    public ResponseEntity<KbanConfig> getLl5Config() {
        return ResponseEntity.ok(kbanConfig);
    }

    // Validate K/BAN
    @PostMapping("/validate")
    public ResponseEntity<KbanValidationResponse> validateKban(@RequestBody(required = false) String dummy) {
        KbanValidationResponse response = new KbanValidationResponse();
        response.setValid(true);
        KbanValidationResponse.Details details = new KbanValidationResponse.Details();
        details.setAccountNumber(kbanConfig.getAccountNumber());
        details.setBranchCode(kbanConfig.getBranchCode());
        response.setDerailes(details);
        return ResponseEntity.ok(response);
    }

    // Export K/BAN config
    @GetMapping("/export")
    public ResponseEntity<KbanConfig> exportKbanConfig() {
        return ResponseEntity.ok(kbanConfig);
    }
}
