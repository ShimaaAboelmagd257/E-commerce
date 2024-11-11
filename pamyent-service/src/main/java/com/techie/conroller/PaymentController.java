package com.techie.conroller;


import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.techie.domain.PaymentRequest;
import com.techie.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment/create")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest paymentRequest){
        try {
            String cancelUrl="http://localhost:8181/payment/cancel";
            String successUrl="http://localhost:8181/payment/success";
            Payment payment = paymentService.createPayment(
                   paymentRequest.getTotal(),
                    paymentRequest.getCurrency(),
                    paymentRequest.getMethod(),
                    paymentRequest.getIntent(),
                    paymentRequest.getDescription(),
                    cancelUrl,
                    successUrl
            );
            for (Links links: payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return ResponseEntity.ok(links.getHref());
                }
            }


        } catch (PayPalRESTException e) {
            log.error("Error during PayPal payment creation: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred during payment creation.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment creation failed.");
    }

    @GetMapping("payment/success")
    public ResponseEntity<String> paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("payerId") String payerId) {
        try {
            Payment payment = paymentService.excutePayment(paymentId, payerId);
            if ("approved".equalsIgnoreCase(payment.getState())) {
                return ResponseEntity.ok("Payment successful!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment was not approved.");
            }

        } catch (PayPalRESTException e) {
            log.error("Error during PayPal payment execution: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred during payment execution.");
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> paymentCancel() {
        return ResponseEntity.ok("Payment cancelled.");
    }

    @GetMapping("/error")
    public ResponseEntity<String> paymentError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during payment processing.");
    }
}
