package com.online_learning.repository;

import com.online_learning.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByEmail(String email) {

        String sql = "SELECT user_id, user_name, password, email_id, user_role " +
             "FROM `USER` WHERE email_id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {

            User user = new User();

            user.setUser_id(rs.getInt("user_id"));
            user.setUser_name(rs.getString("user_name"));
            user.setPassword(rs.getString("password"));
            user.setEmail_id(rs.getString("email_id"));
            user.setUser_role(rs.getString("user_role"));

            return user;

        }, email);
    }

    public List<User> getAllUsers() {

        String sql = "SELECT user_id, user_name, email_id, user_role " +
             "FROM `USER`";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            User user = new User();

            user.setUser_id(rs.getInt("user_id"));
            user.setUser_name(rs.getString("user_name"));
            user.setEmail_id(rs.getString("email_id"));
            user.setUser_role(rs.getString("user_role"));

            return user;

        });
    }

    public void createUser(String userName, String email, String password, String userRole) {

        String sql = "INSERT INTO USER (user_role, user_name, password, email_id, created_at) " +
                     "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                userRole,
                userName,
                password,
                email,
                java.sql.Date.valueOf(java.time.LocalDate.now())
        );
    }
}