
package com.online_learning.controller;

import com.online_learning.model.User;
import com.online_learning.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public Map<String, Object> login(
            @RequestParam String email,
            @RequestParam String password) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (email == null || email.isBlank()
                    || password == null || password.isEmpty()) {
                response.put("success", false);
                response.put("message", "Email and password are required");
                return response;
            }

            User user = userRepository.findByEmail(email.trim());

            if (user == null
                    || !userRepository.verifyPasswordAndUpgrade(user, password)) {
                response.put("success", false);
                response.put("message", "Invalid email or password");
                return response;
            }

            response.put("success", true);
            response.put("userId", user.getUser_id());
            response.put("role", user.getUser_role());

            return response;

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Login error");
            return response;
        }
    }
}