package com.online_learning.controller;

import com.online_learning.model.User;
import com.online_learning.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/admin/users")
    public List<User> getAllUsers() {

        return userRepository.getAllUsers();

    }

}