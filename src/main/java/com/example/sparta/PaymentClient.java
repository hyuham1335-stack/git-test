package com.example.sparta;

import org.springframework.stereotype.Component;


public interface PaymentClient {
    boolean validatePayment(String paymentKey, Long amount);
}
