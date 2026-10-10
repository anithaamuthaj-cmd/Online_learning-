package com.online_learning.repository;

import com.online_learning.model.Certificate;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    public CertificateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get all certificates
    public List<Certificate> getAllCertificates() {
        String sql = "SELECT certificate_id, user_id, course_id, " +
                "certificate_no, issue_date FROM CERTIFICATE";

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

    // Get certificates for a particular User
    public List<Certificate> getCertificatesByUserId(int userId) {
        String sql = "SELECT c.certificate_id, c.user_id, c.course_id, " +
                "c.certificate_no, c.issue_date, " +
                "co.title AS course_title " +
                "FROM CERTIFICATE c " +
                "INNER JOIN COURSE co ON c.course_id = co.course_id " +
                "WHERE c.user_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Certificate certificate = new Certificate();
            certificate.setCertificateId(rs.getInt("certificate_id"));
            certificate.setUserId(rs.getInt("user_id"));
            certificate.setCourseId(rs.getInt("course_id"));
            certificate.setCertificateNo(rs.getString("certificate_no"));
            certificate.setIssueDate(rs.getString("issue_date"));
            certificate.setCourseTitle(rs.getString("course_title"));
            return certificate;
        }, userId);
    }

    // Get certificate details, including User name
    public Certificate getCertificateById(int certificateId) {
        String sql = "SELECT c.certificate_id, c.user_id, c.course_id, " +
                "c.certificate_no, c.issue_date, " +
                "co.title AS course_title, u.user_name AS user_name " +
                "FROM CERTIFICATE c " +
                "INNER JOIN COURSE co ON c.course_id = co.course_id " +
                "INNER JOIN USER u ON c.user_id = u.user_id " +
                "WHERE c.certificate_id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Certificate certificate = new Certificate();
            certificate.setCertificateId(rs.getInt("certificate_id"));
            certificate.setUserId(rs.getInt("user_id"));
            certificate.setCourseId(rs.getInt("course_id"));
            certificate.setCertificateNo(rs.getString("certificate_no"));
            certificate.setIssueDate(rs.getString("issue_date"));
            certificate.setCourseTitle(rs.getString("course_title"));
            certificate.setUserName(rs.getString("user_name"));
            return certificate;
        }, certificateId);
    }

    // Check whether a certificate already exists
    public boolean certificateExists(int userId, int courseId) {
        String sql = "SELECT COUNT(*) FROM CERTIFICATE " +
                "WHERE user_id = ? AND course_id = ?";

        Integer count = jdbcTemplate.queryForObject(
                sql, Integer.class, userId, courseId);

        return count != null && count > 0;
    }

    // Generate a certificate
    public void generateCertificate(
            int userId, int courseId, String certificateNo) {

        String sql = "INSERT INTO CERTIFICATE " +
                "(user_id, course_id, certificate_no, issue_date) " +
                "VALUES (?, ?, ?, CURDATE())";

        jdbcTemplate.update(sql, userId, courseId, certificateNo);
    }

    // Check whether the User passed the course quiz
    public boolean hasPassedQuiz(int userId, int courseId) {
        String sql = "SELECT COUNT(*) FROM QUIZ_RESULT qr " +
                "INNER JOIN QUIZ q ON qr.quiz_id = q.quiz_id " +
                "WHERE qr.user_id = ? AND q.course_id = ? " +
                "AND qr.result_status = 'PASSED'";

        Integer count = jdbcTemplate.queryForObject(
                sql, Integer.class, userId, courseId);

        return count != null && count > 0;
    }
}
