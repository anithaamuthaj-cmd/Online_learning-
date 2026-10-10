
package com.online_learning.repository;

import com.online_learning.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByEmail(String email) {
        String sql = "SELECT user_id, user_name, password, email_id, user_role " +
                     "FROM `USER` WHERE email_id = ?";

        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setUser_id(rs.getInt("user_id"));
            user.setUser_name(rs.getString("user_name"));
            user.setPassword(rs.getString("password"));
            user.setEmail_id(rs.getString("email_id"));
            user.setUser_role(rs.getString("user_role"));
            return user;
        }, email);

        return users.isEmpty() ? null : users.get(0);
    }

    public boolean verifyPasswordAndUpgrade(User user, String rawPassword) {
        String storedPassword = user.getPassword();

        if (storedPassword == null || rawPassword == null) {
            return false;
        }

        // Existing BCrypt password
        if (storedPassword.startsWith("$2a$")
                || storedPassword.startsWith("$2b$")
                || storedPassword.startsWith("$2y$")) {
            return passwordEncoder.matches(rawPassword, storedPassword);
        }

        // Legacy plain-text password: verify once, then upgrade to BCrypt.
        if (storedPassword.equals(rawPassword)) {
            String hashedPassword = passwordEncoder.encode(rawPassword);

            String sql = "UPDATE `USER` SET password = ? WHERE user_id = ?";
            jdbcTemplate.update(sql, hashedPassword, user.getUser_id());

            return true;
        }

        return false;
    }

    public List<User> getAllUsers() {
        String sql = "SELECT user_id, user_name, email_id, user_role FROM `USER`";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setUser_id(rs.getInt("user_id"));
            user.setUser_name(rs.getString("user_name"));
            user.setEmail_id(rs.getString("email_id"));
            user.setUser_role(rs.getString("user_role"));
            return user;
        });
    }

    public void createUser(String userName, String email,
                           String password, String userRole) {

        String hashedPassword = passwordEncoder.encode(password);

        String sql = "INSERT INTO `USER` " +
                     "(user_role, user_name, password, email_id, created_at) " +
                     "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                userRole,
                userName,
                hashedPassword,
                email,
                java.sql.Date.valueOf(java.time.LocalDate.now())
        );
    }
}