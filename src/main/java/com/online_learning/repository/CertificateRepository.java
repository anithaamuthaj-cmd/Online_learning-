package com.online_learning.repository;

import com.online_learning.model.Certificate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    public CertificateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Certificate> getAllCertificates() {

        String sql = "SELECT certificate_id, user_id, course_id, " +
                     "certificate_no, issue_date " +
                     "FROM CERTIFICATE";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Certificate certificate = new Certificate();

            certificate.setCertificateId(rs.getInt("certificate_id"));
            certificate.setUserId(rs.getInt("user_id"));
            certificate.setCourseId(rs.getInt("course_id"));
            certificate.setCertificateNo(rs.getString("certificate_no"));
            certificate.setIssueDate(rs.getString("issue_date"));

            return certificate;
        });
    }

    public List<Certificate> getCertificatesByUserId(int userId) {

        String sql = "SELECT certificate_id, user_id, course_id, " +
                     "certificate_no, issue_date " +
                     "FROM CERTIFICATE " +
                     "WHERE user_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Certificate certificate = new Certificate();

            certificate.setCertificateId(rs.getInt("certificate_id"));
            certificate.setUserId(rs.getInt("user_id"));
            certificate.setCourseId(rs.getInt("course_id"));
            certificate.setCertificateNo(rs.getString("certificate_no"));
            certificate.setIssueDate(rs.getString("issue_date"));

            return certificate;
        }, userId);
    }

    public Certificate getCertificateById(int certificateId) {

        String sql = "SELECT certificate_id, user_id, course_id, " +
                     "certificate_no, issue_date " +
                     "FROM CERTIFICATE " +
                     "WHERE certificate_id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            Certificate certificate = new Certificate();

            certificate.setCertificateId(rs.getInt("certificate_id"));
            certificate.setUserId(rs.getInt("user_id"));
            certificate.setCourseId(rs.getInt("course_id"));
            certificate.setCertificateNo(rs.getString("certificate_no"));
            certificate.setIssueDate(rs.getString("issue_date"));

            return certificate;
        }, certificateId);
    }
}
