package com.online_learning.repository;

import com.online_learning.model.Progress;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProgressRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProgressRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Progress> getProgress(int userId) {

        String sql = "SELECT progress_id, progress_marks, complete_status, " +
                     "content_id, completed_at, user_id " +
                     "FROM PROGRESS WHERE user_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Progress progress = new Progress();

            progress.setProgressId(rs.getInt("progress_id"));
            progress.setProgressMarks(rs.getInt("progress_marks"));
            progress.setCompleteStatus(rs.getString("complete_status"));
            progress.setContentId(rs.getInt("content_id"));
            progress.setCompletedAt(rs.getString("completed_at"));
            progress.setUserId(rs.getInt("user_id"));

            return progress;

        }, userId);
    }


    // Mark course content as completed
    public void markCompleted(int userId, int contentId) {

        String checkSql =
                "SELECT COUNT(*) FROM PROGRESS " +
                "WHERE user_id = ? AND content_id = ?";

        Integer count = jdbcTemplate.queryForObject(
                checkSql,
                Integer.class,
                userId,
                contentId
        );


        if (count != null && count > 0) {

            // Update existing progress
            String updateSql =
                    "UPDATE PROGRESS " +
                    "SET progress_marks = 100, " +
                    "complete_status = 'COMPLETED', " +
                    "completed_at = CURDATE() " +
                    "WHERE user_id = ? AND content_id = ?";

            jdbcTemplate.update(
                    updateSql,
                    userId,
                    contentId
            );

        } else {

            // Create new progress record
            String insertSql =
                    "INSERT INTO PROGRESS " +
                    "(progress_marks, complete_status, content_id, completed_at, user_id) " +
                    "VALUES (100, 'COMPLETED', ?, CURDATE(), ?)";

            jdbcTemplate.update(
                    insertSql,
                    contentId,
                    userId
            );
        }
    }
}