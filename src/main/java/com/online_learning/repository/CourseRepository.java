
package com.online_learning.repository;

import com.online_learning.model.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepository {

    private final JdbcTemplate jdbcTemplate;

    public CourseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Course> getAllCourses() {

        String sql = "SELECT course_id, instructor_id, course_price, " +
                     "title, description, category, status " +
                     "FROM COURSE WHERE status = 'ACTIVE'";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            Course course = new Course();

            course.setCourseId(rs.getInt("course_id"));
            course.setInstructorId(rs.getInt("instructor_id"));
            course.setCoursePrice(rs.getDouble("course_price"));
            course.setTitle(rs.getString("title"));
            course.setDescription(rs.getString("description"));
            course.setCategory(rs.getString("category"));
            course.setStatus(rs.getString("status"));

            return course;

        });
    }


    public Course getCourseById(int courseId) {

        String sql = "SELECT course_id, instructor_id, course_price, " +
                     "title, description, category, status " +
                     "FROM COURSE WHERE course_id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            Course course = new Course();

            course.setCourseId(rs.getInt("course_id"));
            course.setInstructorId(rs.getInt("instructor_id"));
            course.setCoursePrice(rs.getDouble("course_price"));
            course.setTitle(rs.getString("title"));
            course.setDescription(rs.getString("description"));
            course.setCategory(rs.getString("category"));
            course.setStatus(rs.getString("status"));

            return course;

        }, courseId);
    }


    public void addCourse(String title,
                           String description,
                           double price,
                           String category) {

        String sql = "INSERT INTO COURSE " +
                     "(instructor_id, course_price, title, description, " +
                     "category, status, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, CURDATE())";

        jdbcTemplate.update(
                sql,
                1,
                price,
                title,
                description,
                category,
                "ACTIVE"
        );
    }


    public void updateCourse(int courseId,
                             String title,
                             String description,
                             double price,
                             String category) {

        String sql = "UPDATE COURSE SET " +
                     "title = ?, " +
                     "description = ?, " +
                     "course_price = ?, " +
                     "category = ? " +
                     "WHERE course_id = ?";

        jdbcTemplate.update(
                sql,
                title,
                description,
                price,
                category,
                courseId
        );
    }


    public void deleteCourse(int courseId) {

        String sql = "UPDATE COURSE SET status = 'INACTIVE' " +
                     "WHERE course_id = ?";

        jdbcTemplate.update(sql, courseId);
    }

}
