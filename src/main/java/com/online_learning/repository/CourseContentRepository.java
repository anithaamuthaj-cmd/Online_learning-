package com.online_learning.repository;

import com.online_learning.model.CourseContent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseContentRepository {

    private final JdbcTemplate jdbcTemplate;

    public CourseContentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<CourseContent> getCourseContent(int courseId) {

        String sql = "SELECT content_id, course_id, title, content_url, " +
                     "content_type, duration " +
                     "FROM COURSE_CONTENT " +
                     "WHERE course_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            CourseContent content = new CourseContent();

            content.setContentId(rs.getInt("content_id"));
            content.setCourseId(rs.getInt("course_id"));
            content.setTitle(rs.getString("title"));
            content.setContentUrl(rs.getString("content_url"));
            content.setContentType(rs.getString("content_type"));
            content.setDuration(rs.getInt("duration"));

            return content;

        }, courseId);
    }


    public CourseContent getContentById(int contentId) {

        String sql = "SELECT content_id, course_id, title, content_url, " +
                     "content_type, duration " +
                     "FROM COURSE_CONTENT " +
                     "WHERE content_id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            CourseContent content = new CourseContent();

            content.setContentId(rs.getInt("content_id"));
            content.setCourseId(rs.getInt("course_id"));
            content.setTitle(rs.getString("title"));
            content.setContentUrl(rs.getString("content_url"));
            content.setContentType(rs.getString("content_type"));
            content.setDuration(rs.getInt("duration"));

            return content;

        }, contentId);
    }


    public void addCourseContent(
            int courseId,
            String title,
            String contentUrl,
            String contentType,
            int duration) {

        String sql = "INSERT INTO COURSE_CONTENT " +
                     "(course_id, title, content_url, content_type, duration) " +
                     "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                courseId,
                title,
                contentUrl,
                contentType,
                duration
        );
    }


    public void updateCourseContent(
            int contentId,
            String title,
            String contentUrl,
            String contentType,
            int duration) {

        String sql = "UPDATE COURSE_CONTENT SET " +
                     "title = ?, " +
                     "content_url = ?, " +
                     "content_type = ?, " +
                     "duration = ? " +
                     "WHERE content_id = ?";

        jdbcTemplate.update(
                sql,
                title,
                contentUrl,
                contentType,
                duration,
                contentId
        );
    }


    public void deleteCourseContent(int contentId) {

        String sql = "DELETE FROM COURSE_CONTENT " +
                     "WHERE content_id = ?";

        jdbcTemplate.update(sql, contentId);
    }

}