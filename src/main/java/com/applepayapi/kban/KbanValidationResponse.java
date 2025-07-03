package com.applepayapi.kban;

public class KbanValidationResponse {
    private boolean isValid;
    private Details derailes;

    public static class Details {
        private String accountNumber;
        private String branchCode;
        public String getAccountNumber() { return accountNumber; }
        public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
        public String getBranchCode() { return branchCode; }
        public void setBranchCode(String branchCode) { this.branchCode = branchCode; }
    }

    public boolean isValid() { return isValid; }
    public void setValid(boolean valid) { isValid = valid; }
    public Details getDerailes() { return derailes; }
    public void setDerailes(Details derailes) { this.derailes = derailes; }
}
