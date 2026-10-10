
package com.online_learning.controller;

import com.online_learning.model.Certificate;
import com.online_learning.repository.CertificateRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class CertificateController {

    private final CertificateRepository certificateRepository;

    public CertificateController(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @GetMapping("/certificates")
    public List<Certificate> getCertificates(
            @RequestParam int userId) {

        return certificateRepository.getCertificatesByUserId(userId);
    }

    @GetMapping("/certificate/{certificateId}")
    public Certificate getCertificateById(
            @PathVariable int certificateId) {

        return certificateRepository.getCertificateById(certificateId);
    }

    @PostMapping("/generate-certificate")
    public Map<String, Object> generateCertificate(
            @RequestParam int userId,
            @RequestParam int courseId) {

        Map<String, Object> response = new HashMap<>();

        try {

            // Check whether the User has passed the quiz
            if (!certificateRepository.hasPassedQuiz(userId, courseId)) {

                response.put("success", false);
                response.put(
                        "message",
                        "Certificate can be generated only after passing the quiz."
                );

                return response;
            }

            // Check whether certificate already exists
            if (certificateRepository.certificateExists(userId, courseId)) {

                response.put("success", false);
                response.put(
                        "message",
                        "Certificate already exists for this course."
                );

                return response;
            }

            // Generate unique certificate number
            String certificateNo =
                    "CERT-" + UUID.randomUUID()
                            .toString()
                            .substring(0, 8)
                            .toUpperCase();

            // Save certificate
            certificateRepository.generateCertificate(
                    userId,
                    courseId,
                    certificateNo
            );

            response.put("success", true);
            response.put("certificateNo", certificateNo);
            response.put(
                    "message",
                    "Certificate generated successfully!"
            );

            return response;

        } catch (Exception e) {

            e.printStackTrace();

            response.put("success", false);
            response.put(
                    "message",
                    "Unable to generate certificate."
            );

            return response;
        }
    }
}

