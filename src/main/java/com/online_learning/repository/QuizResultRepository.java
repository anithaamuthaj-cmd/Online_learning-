package com.online_learning.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QuizResultRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuizResultRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveResult(int quizId, int userId, int score, String resultStatus) {

        String sql = "INSERT INTO QUIZ_RESULT " +
                     "(quiz_id, user_id, score, result_status, completed_at) " +
                     "VALUES (?, ?, ?, ?, CURDATE())";

        jdbcTemplate.update(
                sql,
                quizId,
                userId,
                score,
                resultStatus
        );
    }
}