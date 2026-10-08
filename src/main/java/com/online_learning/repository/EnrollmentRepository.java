package com.online_learning.repository;

import com.online_learning.model.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnrollmentRepository {

    private final JdbcTemplate jdbcTemplate;

    public EnrollmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean alreadyEnrolled(int userId, int courseId) {

        String sql = "SELECT COUNT(*) FROM ENROLLMENT " +
                     "WHERE user_id = ? AND course_id = ?";

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                userId,
                courseId
        );

        return count != null && count > 0;
    }

    public void enrollUser(int userId, int courseId) {

        String sql = "INSERT INTO ENROLLMENT " +
                     "(user_id, course_id, enroll_date, status) " +
                     "VALUES (?, ?, CURDATE(), 'ENROLLED')";

        jdbcTemplate.update(sql, userId, courseId);
    }

    public List<Integer> getEnrolledCourseIds(int userId) {

        String sql = "SELECT course_id FROM ENROLLMENT " +
                     "WHERE user_id = ?";

        return jdbcTemplate.queryForList(
                sql,
                Integer.class,
                userId
        );
    }

    public List<Course> getEnrolledCourses(int userId) {

        String sql = "SELECT DISTINCT c.course_id, c.title, c.description, c.course_price " +
                     "FROM COURSE c " +
                     "INNER JOIN ENROLLMENT e ON c.course_id = e.course_id " +
                     "WHERE e.user_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Course course = new Course();

            course.setCourseId(rs.getInt("course_id"));
            course.setTitle(rs.getString("title"));
            course.setDescription(rs.getString("description"));
            course.setCoursePrice(rs.getDouble("course_price"));

            return course;

        }, userId);
    }
}
