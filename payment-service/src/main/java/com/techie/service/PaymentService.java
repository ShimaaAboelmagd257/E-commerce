package com.techie.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaymentService {
    Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl
    ) throws PayPalRESTException;

    Payment excutePayment(
            String paymentId,
            String payerId
    ) throws PayPalRESTException;
}
