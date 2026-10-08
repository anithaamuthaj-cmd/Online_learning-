package com.online_learning.controller;

import com.online_learning.model.Certificate;
import com.online_learning.repository.CertificateRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
