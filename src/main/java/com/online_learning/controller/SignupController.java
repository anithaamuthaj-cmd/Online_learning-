
package com.online_learning.controller;

import com.online_learning.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

@RestController
public class SignupController {

    private final UserRepository userRepository;

    public SignupController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String userName,
                         @RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String userRole) {

        // Only User and Instructor can sign up
        if (!userRole.equals("USER") && !userRole.equals("INSTRUCTOR")) {
            return "Invalid account type";
        }

        // Password validation
        if (!password.matches(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$")) {

            return "Password does not meet the requirements";
        }

        try {

            userRepository.createUser(
                    userName,
                    email,
                    password,
                    userRole
            );

            return "Account created successfully!";

        } catch (DuplicateKeyException e) {

            return "Email already registered";

        } catch (Exception e) {

            return "Account creation failed";
        }
    }
}

