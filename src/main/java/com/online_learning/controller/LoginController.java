
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
    public Map<String, Object> login(@RequestParam String email,
                                     @RequestParam String password) {

        Map<String, Object> response = new HashMap<>();

        try {

            User user = userRepository.findByEmail(email);

            if (user == null) {

                response.put("success", false);
                response.put("message", "Invalid email or password");

                return response;
            }

            if (!user.getPassword().equals(password)) {

                response.put("success", false);
                response.put("message", "Invalid email or password");

                return response;
            }

            String role = user.getUser_role();

            response.put("success", true);
            response.put("userId", user.getUser_id());
            response.put("role", role);

            return response;

        } catch (Exception e) {

            e.printStackTrace();

            response.put("success", false);
            response.put("message", "Login error");

            return response;
        }
    }
}
