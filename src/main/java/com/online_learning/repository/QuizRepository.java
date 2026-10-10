
package com.online_learning.repository;

import com.online_learning.model.Quiz;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuizRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Quiz> getAllQuizzes() {

        String sql = "SELECT quiz_id, course_id, title, total_marks, " +
                     "created_at, user_id, result_id " +
                     "FROM QUIZ";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Quiz quiz = new Quiz();

            quiz.setQuizId(rs.getInt("quiz_id"));
            quiz.setCourseId(rs.getInt("course_id"));
            quiz.setTitle(rs.getString("title"));
            quiz.setTotalMarks(rs.getInt("total_marks"));
            quiz.setCreatedAt(rs.getString("created_at"));
            quiz.setUserId(rs.getInt("user_id"));
            quiz.setResultId(rs.getInt("result_id"));

            return quiz;
        });
    }

    public Quiz getQuizById(int quizId) {

        String sql = "SELECT quiz_id, course_id, title, total_marks, " +
                     "created_at, user_id, result_id " +
                     "FROM QUIZ WHERE quiz_id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            Quiz quiz = new Quiz();

            quiz.setQuizId(rs.getInt("quiz_id"));
            quiz.setCourseId(rs.getInt("course_id"));
            quiz.setTitle(rs.getString("title"));
            quiz.setTotalMarks(rs.getInt("total_marks"));
            quiz.setCreatedAt(rs.getString("created_at"));
            quiz.setUserId(rs.getInt("user_id"));
            quiz.setResultId(rs.getInt("result_id"));

            return quiz;
        }, quizId);
    }

    // Find the course associated with a quiz
    public int getCourseIdByQuizId(int quizId) {

        String sql = "SELECT course_id FROM QUIZ WHERE quiz_id = ?";

        Integer courseId = jdbcTemplate.queryForObject(
                sql, Integer.class, quizId);

        if (courseId == null) {
            throw new IllegalArgumentException(
                    "No course found for quiz ID: " + quizId);
        }

        return courseId;
    }
}