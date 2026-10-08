package com.online_learning.controller;

import com.online_learning.model.Quiz;
import com.online_learning.repository.QuizRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuizController {

    private final QuizRepository quizRepository;

    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }


    @GetMapping("/quizzes")
    public List<Quiz> getAllQuizzes() {

        return quizRepository.getAllQuizzes();
    }


    @GetMapping("/quiz/{quizId}")
    public Quiz getQuizById(
            @PathVariable int quizId) {

        return quizRepository.getQuizById(quizId);
    }

}