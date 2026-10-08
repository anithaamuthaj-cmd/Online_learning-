package com.online_learning.controller;

import com.online_learning.model.CourseContent;
import com.online_learning.repository.CourseContentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseContentController {

    private final CourseContentRepository courseContentRepository;

    public CourseContentController(CourseContentRepository courseContentRepository) {
        this.courseContentRepository = courseContentRepository;
    }


    @GetMapping("/course-content")
    public List<CourseContent> getCourseContent(
            @RequestParam int courseId) {

        return courseContentRepository.getCourseContent(courseId);
    }


    @GetMapping("/course-content/{contentId}")
    public CourseContent getContentById(
            @PathVariable int contentId) {

        return courseContentRepository.getContentById(contentId);
    }


    @PostMapping("/add-course-content")
    public String addCourseContent(
            @RequestParam int courseId,
            @RequestParam String title,
            @RequestParam String contentUrl,
            @RequestParam String contentType,
            @RequestParam int duration) {

        courseContentRepository.addCourseContent(
                courseId,
                title,
                contentUrl,
                contentType,
                duration
        );

        return "Course content added successfully";
    }


    @PutMapping("/update-course-content/{contentId}")
    public String updateCourseContent(
            @PathVariable int contentId,
            @RequestParam String title,
            @RequestParam String contentUrl,
            @RequestParam String contentType,
            @RequestParam int duration) {

        courseContentRepository.updateCourseContent(
                contentId,
                title,
                contentUrl,
                contentType,
                duration
        );

        return "Course content updated successfully";
    }


    @DeleteMapping("/delete-course-content/{contentId}")
    public String deleteCourseContent(
            @PathVariable int contentId) {

        courseContentRepository.deleteCourseContent(contentId);

        return "Course content deleted successfully";
    }

}