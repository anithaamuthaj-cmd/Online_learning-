package com.online_learning.controller;

import com.online_learning.model.QuizQuestion;
import com.online_learning.repository.QuizQuestionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuizQuestionController {

    private final QuizQuestionRepository quizQuestionRepository;

    public QuizQuestionController(QuizQuestionRepository quizQuestionRepository) {
        this.quizQuestionRepository = quizQuestionRepository;
    }

    @GetMapping("/quiz-questions")
    public List<QuizQuestion> getQuestions(
            @RequestParam int quizId) {

        return quizQuestionRepository.getQuestionsByQuizId(quizId);
    }
}