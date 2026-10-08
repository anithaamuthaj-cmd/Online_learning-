package com.online_learning.controller;

import com.online_learning.repository.QuizResultRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizResultController {

    private final QuizResultRepository quizResultRepository;

    public QuizResultController(QuizResultRepository quizResultRepository) {
        this.quizResultRepository = quizResultRepository;
    }

    @PostMapping("/quiz-result")
    public String saveResult(
            @RequestParam int quizId,
            @RequestParam int userId,
            @RequestParam int score,
            @RequestParam String resultStatus) {

        quizResultRepository.saveResult(
                quizId,
                userId,
                score,
                resultStatus
        );

        return "Quiz result saved successfully!";
    }
}