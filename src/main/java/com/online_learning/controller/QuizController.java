
package com.online_learning.controller;

import com.online_learning.model.Quiz;
import com.online_learning.repository.QuizQuestionRepository;
import com.online_learning.repository.QuizRepository;
import com.online_learning.repository.QuizResultRepository;
import com.online_learning.repository.CertificateRepository;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class QuizController {

    private final QuizRepository quizRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuizResultRepository quizResultRepository;
    private final CertificateRepository certificateRepository;

    public QuizController(
            QuizRepository quizRepository,
            QuizQuestionRepository quizQuestionRepository,
            QuizResultRepository quizResultRepository,
            CertificateRepository certificateRepository) {

        this.quizRepository = quizRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.quizResultRepository = quizResultRepository;
        this.certificateRepository = certificateRepository;
    }

    @GetMapping("/quizzes")
    public List<Quiz> getAllQuizzes() {
        return quizRepository.getAllQuizzes();
    }

    @GetMapping("/quiz/{quizId}")
    public Quiz getQuizById(@PathVariable int quizId) {
        return quizRepository.getQuizById(quizId);
    }

    @PostMapping("/submit-quiz")
    public Map<String, Object> submitQuiz(
            @RequestParam int quizId,
            @RequestParam int userId,
            @RequestBody Map<String, String> answers) {

        Map<String, Object> response = new HashMap<>();

        try {
            int score = 0;
            int totalQuestions = answers.size();

            for (Map.Entry<String, String> answer : answers.entrySet()) {
                int questionId = Integer.parseInt(answer.getKey());
                String selectedAnswer = answer.getValue();

                String correctAnswer =
                        quizQuestionRepository.getCorrectAnswer(questionId);

                if (selectedAnswer != null
                        && selectedAnswer.equalsIgnoreCase(correctAnswer)) {
                    score++;
                }
            }

            double percentage = 0;

            if (totalQuestions > 0) {
                percentage = ((double) score / totalQuestions) * 100;
            }

            String resultStatus =
                    percentage >= 50 ? "PASSED" : "FAILED";

            // Save the quiz result
            quizResultRepository.saveResult(
                    quizId, userId, score, resultStatus);

            response.put("success", true);
            response.put("score", score);
            response.put("totalQuestions", totalQuestions);
            response.put("percentage", percentage);
            response.put("resultStatus", resultStatus);

            // Generate certificate automatically after passing
            if ("PASSED".equals(resultStatus)) {
                int courseId =
                        quizRepository.getCourseIdByQuizId(quizId);

                if (certificateRepository.certificateExists(
                        userId, courseId)) {

                    response.put("certificateGenerated", false);
                    response.put("certificateMessage",
                            "A certificate already exists for this course.");

                } else if (certificateRepository.hasPassedQuiz(
                        userId, courseId)) {

                    String certificateNo = "CERT-" +
                            UUID.randomUUID()
                                    .toString()
                                    .substring(0, 8)
                                    .toUpperCase();

                    certificateRepository.generateCertificate(
                            userId, courseId, certificateNo);

                    response.put("certificateGenerated", true);
                    response.put("certificateNo", certificateNo);
                    response.put("certificateMessage",
                            "Certificate generated successfully!");
                }
            }

            return response;

        } catch (Exception e) {
            e.printStackTrace();

            response.put("success", false);
            response.put("message", "Unable to submit quiz.");
            return response;
        }
    }
}