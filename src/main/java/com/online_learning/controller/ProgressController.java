package com.online_learning.controller;

import com.online_learning.model.Progress;
import com.online_learning.repository.ProgressRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProgressController {

    private final ProgressRepository progressRepository;

    public ProgressController(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    // Get progress for a user
    @GetMapping("/progress")
    public List<Progress> getProgress(@RequestParam int userId) {

        return progressRepository.getProgress(userId);
    }


    // Mark content as completed
    @PostMapping("/progress/complete")
    public String markCompleted(
            @RequestParam int userId,
            @RequestParam int contentId) {

        try {

            progressRepository.markCompleted(
                    userId,
                    contentId
            );

            return "Content completed successfully!";

        } catch (Exception e) {

            e.printStackTrace();

            return "Failed to update progress.";
        }
    }
}