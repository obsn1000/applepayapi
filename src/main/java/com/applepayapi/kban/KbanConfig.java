package com.applepayapi.kban;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bank")
public class KbanConfig {
    private String amid;
    private String bankName;
    private String accountNumber;
    private String bankCode;
    private String country;
    private String checksum;
    private String bban;
    private boolean amidIsvalid;
    private String merchantId;
    
    // Legacy fields for backward compatibility
    private String branchCode;
    private String iban;
    private String transactionHash;
    private String sessionToken;

    // Getters and Setters
    public String getAmid() { return amid; }
    public void setAmid(String amid) { this.amid = amid; }
    
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    
    public String getBankCode() { return bankCode; }
    public void setBankCode(String bankCode) { this.bankCode = bankCode; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public String getChecksum() { return checksum; }
    public void setChecksum(String checksum) { this.checksum = checksum; }
    
    public String getBban() { return bban; }
    public void setBban(String bban) { this.bban = bban; }
    
    public boolean isAmidIsvalid() { return amidIsvalid; }
    public void setAmidIsvalid(boolean amidIsvalid) { this.amidIsvalid = amidIsvalid; }
    
    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
    
    // Legacy getters/setters
    public String getBranchCode() { return branchCode != null ? branchCode : bankCode; }
    public void setBranchCode(String branchCode) { this.branchCode = branchCode; }
    
    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }
    
    public String getTransactionHash() { return transactionHash; }
    public void setTransactionHash(String transactionHash) { this.transactionHash = transactionHash; }
    
    public String getSessionToken() { return sessionToken; }
    public void setSessionToken(String sessionToken) { this.sessionToken = sessionToken; }
}
