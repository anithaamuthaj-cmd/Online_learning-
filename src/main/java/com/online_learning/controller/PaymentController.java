
package com.online_learning.controller;

import com.online_learning.repository.PaymentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // Payment history for one requested user.
    @GetMapping("/payments")
    public List<Map<String, Object>> getUserPayments(
            @RequestParam int userId) {

        if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID.");
        }

        return paymentRepository.getPaymentsByUserId(userId);
    }

    // Simulated payment for project testing only.
    @PostMapping("/demo-payment")
    public Map<String, Object> makeDemoPayment(
            @RequestParam int userId,
            @RequestParam int courseId) {

        Map<String, Object> response = new HashMap<>();

        if (userId <= 0 || courseId <= 0) {
            response.put("success", false);
            response.put("message", "Invalid User ID or course ID.");
            return response;
        }

        try {
            String transactionId =
                    paymentRepository.generateDemoTransactionId();

            int rows = paymentRepository.saveDemoPayment(
                    userId, courseId, transactionId);

            if (rows == 0) {
                response.put("success", false);
                response.put("message", "Course not found.");
                return response;
            }

            response.put("success", true);
            response.put("paymentStatus", "DEMO_SUCCESS");
            response.put("transactionId", transactionId);
            response.put(
                    "message",
                    "Simulated payment recorded. No real money was collected."
            );

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Unable to record simulated payment.");
        }

        return response;
    }
}