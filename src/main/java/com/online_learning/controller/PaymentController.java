package com.online_learning.controller;

import com.online_learning.model.Payment;
import com.online_learning.repository.PaymentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    @GetMapping("/payments")
    public List<Payment> getAllPayments() {

        return paymentRepository.getAllPayments();
    }


    @GetMapping("/payment/{paymentId}")
    public Payment getPaymentById(
            @PathVariable int paymentId) {

        return paymentRepository.getPaymentById(paymentId);
    }

}