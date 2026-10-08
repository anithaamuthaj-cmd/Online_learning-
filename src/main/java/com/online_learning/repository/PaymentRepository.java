package com.online_learning.repository;

import com.online_learning.model.Payment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Payment> getAllPayments() {

        String sql = "SELECT payment_id, student_id, course_id, amount, " +
                     "payment_date, payment_type, payment_status, transaction_id " +
                     "FROM PAYMENT";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Payment payment = new Payment();

            payment.setPaymentId(rs.getInt("payment_id"));
            payment.setStudentId(rs.getInt("student_id"));
            payment.setCourseId(rs.getInt("course_id"));
            payment.setAmount(rs.getDouble("amount"));
            payment.setPaymentDate(rs.getString("payment_date"));
            payment.setPaymentType(rs.getString("payment_type"));
            payment.setPaymentStatus(rs.getString("payment_status"));
            payment.setTransactionId(rs.getString("transaction_id"));

            return payment;

        });
    }


    public Payment getPaymentById(int paymentId) {

        String sql = "SELECT payment_id, student_id, course_id, amount, " +
                     "payment_date, payment_type, payment_status, transaction_id " +
                     "FROM PAYMENT WHERE payment_id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            Payment payment = new Payment();

            payment.setPaymentId(rs.getInt("payment_id"));
            payment.setStudentId(rs.getInt("student_id"));
            payment.setCourseId(rs.getInt("course_id"));
            payment.setAmount(rs.getDouble("amount"));
            payment.setPaymentDate(rs.getString("payment_date"));
            payment.setPaymentType(rs.getString("payment_type"));
            payment.setPaymentStatus(rs.getString("payment_status"));
            payment.setTransactionId(rs.getString("transaction_id"));

            return payment;

        }, paymentId);
    }

}