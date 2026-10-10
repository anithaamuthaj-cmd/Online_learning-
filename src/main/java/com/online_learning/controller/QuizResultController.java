
package com.online_learning.controller;

import com.online_learning.repository.QuizResultRepository;
import com.online_learning.repository.CertificateRepository;
import com.online_learning.repository.QuizRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class QuizResultController {

    private final QuizResultRepository quizResultRepository;
    private final CertificateRepository certificateRepository;
    private final QuizRepository quizRepository;

    public QuizResultController(
            QuizResultRepository quizResultRepository,
            CertificateRepository certificateRepository,
            QuizRepository quizRepository) {

        this.quizResultRepository = quizResultRepository;
        this.certificateRepository = certificateRepository;
        this.quizRepository = quizRepository;
    }

    @PostMapping("/quiz-result")
    public Map<String, Object> saveResult(
            @RequestParam int quizId,
            @RequestParam int userId,
            @RequestParam int score,
            @RequestParam String resultStatus) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Save the quiz result first
            quizResultRepository.saveResult(
                    quizId,
                    userId,
                    score,
                    resultStatus
            );

            response.put("success", true);
            response.put("message", "Quiz result saved successfully!");

            // Generate a certificate only for a passed quiz
            if ("PASSED".equalsIgnoreCase(resultStatus)) {

                // Find the course linked to this quiz
                int courseId = quizRepository.getCourseIdByQuizId(quizId);

                if (!certificateRepository.certificateExists(
                        userId, courseId)) {

                    if (certificateRepository.hasPassedQuiz(
                            userId, courseId)) {

                        String certificateNo = "CERT-" +
                                UUID.randomUUID()
                                        .toString()
                                        .substring(0, 8)
                                        .toUpperCase();

                        certificateRepository.generateCertificate(
                                userId,
                                courseId,
                                certificateNo
                        );

                        response.put(
                                "certificateGenerated", true);
                        response.put(
                                "certificateNo", certificateNo);
                    }
                } else {
                    response.put(
                            "certificateGenerated", false);
                    response.put(
                            "certificateMessage",
                            "A certificate already exists for this course.");
                }
            }

            return response;

        } catch (Exception e) {
            e.printStackTrace();

            response.put("success", false);
            response.put(
                    "message",
                    "Unable to save quiz result or generate certificate."
            );

            return response;
        }
    }
}