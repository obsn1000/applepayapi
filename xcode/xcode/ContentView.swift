
import SwiftUI
import PassKit

struct ContentView: View {
    var body: some View {
        VStack {
            Button("Pay with Apple Pay") {
                startApplePay()
            }
        }
    }

    func startApplePay() {
        let paymentRequest = PKPaymentRequest()
        paymentRequest.merchantIdentifier = "merchant.applepayapi"
        paymentRequest.supportedNetworks = [.visa, .masterCard, .amex]
        paymentRequest.merchantCapabilities = .threeDSecure
        paymentRequest.countryCode = "US"
        paymentRequest.currencyCode = "USD"
        paymentRequest.paymentSummaryItems = [
            PKPaymentSummaryItem(label: "Test Item", amount: NSDecimalNumber(string: "1.00"))
        ]

        if let controller = PKPaymentAuthorizationViewController(paymentRequest: paymentRequest) {
            if let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
               let rootVC = windowScene.windows.first?.rootViewController {
                let delegate = ApplePayDelegate()
                controller.delegate = delegate
                objc_setAssociatedObject(controller, "applePayDelegate", delegate, .OBJC_ASSOCIATION_RETAIN_NONATOMIC)
                rootVC.present(controller, animated: true, completion: nil)
            }
        }
    }
}

class ApplePayDelegate: NSObject, PKPaymentAuthorizationViewControllerDelegate {
    func paymentAuthorizationViewController(_ controller: PKPaymentAuthorizationViewController,
                                           didAuthorizePayment payment: PKPayment,
                                           handler completion: @escaping (PKPaymentAuthorizationResult) -> Void) {
        let tokenData = payment.token.paymentData
        let tokenString = tokenData.base64EncodedString()

        let url = URL(string: "https://applepayapi.vercel.app/api/applepay/authorize-payment")!
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        let json: [String: String] = ["paymentToken": tokenString]
        request.httpBody = try? JSONSerialization.data(withJSONObject: json)

        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            DispatchQueue.main.async {
                if let error = error {
                    print("Error: \(error)")
                    completion(PKPaymentAuthorizationResult(status: .failure, errors: nil))
                    return
                }
                completion(PKPaymentAuthorizationResult(status: .success, errors: nil))
            }
        }
        task.resume()
    }

    func paymentAuthorizationViewControllerDidFinish(_ controller: PKPaymentAuthorizationViewController) {
        controller.dismiss(animated: true, completion: nil)
    }
}
