class ApplePayAPI {
    constructor(merchantIdentifier) {
        this.merchantIdentifier = merchantIdentifier;
        this.merchantValidationEndpoint = '/api/applepay/validate-merchant';
        this.paymentAuthorizationEndpoint = '/api/applepay/authorize-payment';
    }

    checkApplePayAvailability() {
        return window.ApplePaySession && ApplePaySession.canMakePayments();
    }

    checkApplePayCardAvailability() {
        return ApplePaySession.canMakePaymentsWithActiveCard(this.merchantIdentifier);
    }

    initializeApplePay() {
        if (!this.checkApplePayAvailability()) {
            console.log('Apple Pay is not available');
            return false;
        }

        const paymentRequest = {
            countryCode: 'US',
            currencyCode: 'USD',
            merchantCapabilities: ['supports3DS'],
            supportedNetworks: ['visa', 'masterCard', 'amex', 'discover'],
            total: {
                label: 'Your Store Name',
                amount: '10.00'
            }
        };

        const session = new ApplePaySession(3, paymentRequest);

        session.onvalidatemerchant = (event) => {
            this.validateMerchant(event.validationURL)
                .then(merchantSession => {
                    session.completeMerchantValidation(merchantSession);
                })
                .catch(error => {
                    console.error('Merchant validation failed:', error);
                    session.abort();
                });
        };

        session.onpaymentauthorized = (event) => {
            this.processPayment(event.payment.token)
                .then(response => {
                    if (response === 'Payment successful') {
                        session.completePayment(ApplePaySession.STATUS_SUCCESS);
                    } else {
                        session.completePayment(ApplePaySession.STATUS_FAILURE);
                    }
                });
        };

        session.begin();
    }

    async validateMerchant(validationURL) {
        const response = await fetch(this.merchantValidationEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(validationURL)
        });
        return await response.json();
    }

    async processPayment(paymentToken) {
        const response = await fetch(this.paymentAuthorizationEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(paymentToken)
        });
        return await response.text();
    }
}

// Usage example
document.addEventListener('DOMContentLoaded', () => {
    const applePayButton = document.getElementById('apple-pay-button');
    if (applePayButton) {
        applePayButton.addEventListener('click', () => {
            const applePay = new ApplePayAPI('your.merchant.id');
            applePay.initializeApplePay();
        });
    }
});
