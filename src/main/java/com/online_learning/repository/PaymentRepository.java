
package com.online_learning.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class PaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Return only the payment records for the requested user.
    public List<Map<String, Object>> getPaymentsByUserId(int userId) {
        String sql =
                "SELECT p.payment_id AS paymentId, " +
                "p.student_id AS userId, " +
                "p.course_id AS courseId, " +
                "c.title AS courseTitle, " +
                "p.amount AS amount, " +
                "p.payment_date AS paymentDate, " +
                "p.payment_type AS paymentType, " +
                "p.payment_status AS paymentStatus, " +
                "p.transaction_id AS transactionId " +
                "FROM PAYMENT p " +
                "INNER JOIN COURSE c ON p.course_id = c.course_id " +
                "WHERE p.student_id = ? " +
                "ORDER BY p.payment_id DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> payment = new LinkedHashMap<>();
            payment.put("paymentId", rs.getInt("paymentId"));
            payment.put("userId", rs.getInt("userId"));
            payment.put("courseId", rs.getInt("courseId"));
            payment.put("courseTitle", rs.getString("courseTitle"));
            payment.put("amount", rs.getBigDecimal("amount"));
            payment.put("paymentDate", rs.getString("paymentDate"));
            payment.put("paymentType", rs.getString("paymentType"));
            payment.put("paymentStatus", rs.getString("paymentStatus"));
            payment.put("transactionId", rs.getString("transactionId"));
            return payment;
        }, userId);
    }

    // Record a simulated payment for testing only.
    // The amount comes from the database, not the browser.
    public int saveDemoPayment(
            int userId,
            int courseId,
            String transactionId) {

        String sql =
                "INSERT INTO PAYMENT " +
                "(student_id, course_id, amount, payment_date, " +
                "payment_type, payment_status, transaction_id) " +
                "SELECT ?, course_id, course_price, CURDATE(), " +
                "'DEMO', 'DEMO_SUCCESS', ? " +
                "FROM COURSE WHERE course_id = ?";

        return jdbcTemplate.update(
                sql, userId, transactionId, courseId);
    }

    // Generate a unique transaction ID for demo testing.
    public String generateDemoTransactionId() {
        return "DEMO-" +
                UUID.randomUUID().toString()
                        .substring(0, 8)
                        .toUpperCase();
    }
}