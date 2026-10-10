package com.online_learning.repository;

import com.online_learning.model.QuizQuestion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizQuestionRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuizQuestionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<QuizQuestion> getQuestionsByQuizId(int quizId) {

        String sql = "SELECT question_id, quiz_id, question_text, " +
                     "option_a, option_b, option_c, option_d " +
                     "FROM QUIZ_QUESTION WHERE quiz_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            QuizQuestion question = new QuizQuestion();

            question.setQuestionId(rs.getInt("question_id"));
            question.setQuizId(rs.getInt("quiz_id"));
            question.setQuestionText(rs.getString("question_text"));
            question.setOptionA(rs.getString("option_a"));
            question.setOptionB(rs.getString("option_b"));
            question.setOptionC(rs.getString("option_c"));
            question.setOptionD(rs.getString("option_d"));

            return question;
        }, quizId);
    }

    public String getCorrectAnswer(int questionId) {

        String sql = "SELECT correct_answer " +
                     "FROM QUIZ_QUESTION " +
                     "WHERE question_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                String.class,
                questionId
        );
    }
}