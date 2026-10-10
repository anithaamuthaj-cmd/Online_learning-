package com.online_learning.controller;

import com.online_learning.repository.EnrollmentRepository;
import com.online_learning.model.Course;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentController(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @PostMapping("/enroll")
    public String enroll(@RequestParam int userId,
                         @RequestParam int courseId) {

        try {

            if (enrollmentRepository.alreadyEnrolled(userId, courseId)) {
                return "Already enrolled in this course!";
            }

            enrollmentRepository.enrollUser(userId, courseId);

            return "Enrollment successful!";

        } catch (Exception e) {

            return "Enrollment failed!";
        }
    }

    @GetMapping("/enrollments")
    public List<Integer> getEnrollments(@RequestParam int userId) {

        return enrollmentRepository.getEnrolledCourseIds(userId);
    }

    @GetMapping("/my-courses")
    public List<Course> getMyCourses(@RequestParam int userId) {

        return enrollmentRepository.getEnrolledCourses(userId);
    }
}
